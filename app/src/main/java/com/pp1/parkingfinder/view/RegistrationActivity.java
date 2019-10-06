package com.pp1.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.User;

import static android.text.TextUtils.isEmpty;
import static com.pp1.parkingfinder.util.Helper.doStringsMatch;


public class RegistrationActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "RegistrationActivity";

    //widgets
    private EditText mEmail, mRego, mFirstname, mLastname, mPassword, mConfirmPassword, mAddress;
    private ProgressBar mProgressBar;

    //vars
    private FirebaseFirestore mDb;

    //Variable
    private boolean isLeaser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        mEmail = (EditText) findViewById(R.id.edittext_email);
        mPassword = (EditText) findViewById(R.id.edittext_password);
        mConfirmPassword = (EditText) findViewById(R.id.edittext_cnf_password);
        mFirstname = (EditText) findViewById(R.id.edittext_firstname);
        mLastname = (EditText) findViewById(R.id.edittext_lastname);
        mRego = (EditText) findViewById(R.id.edittext_rego);
        mAddress = (EditText) findViewById(R.id.edittext_address);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAddress.setVisibility(View.INVISIBLE);

        // Action on button click - Calls button lister on object
        findViewById(R.id.button_toggle).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
        findViewById(R.id.textview_login).setOnClickListener(this);

        mDb = FirebaseFirestore.getInstance();

        hideSoftKeyboard();

    }
     /*
     * Register a new email and password to Firebase Authentication
     *
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     * @param rego
     *
     */
    public void registerNewEmail(final String email, String password, String firstname,
                                 String lastname, String rego) {
        final String eFirstname = firstname;
        final String eLastname = lastname;
        final String eRego = rego;

        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: AuthState: "
                                    + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            String firstname = eFirstname;
                            //insert some default data
                            User user = new User();
                            user.setEmail(email);
                            user.setUsername(email.substring(0, email.indexOf("@")));
                            user.setUser_id(FirebaseAuth.getInstance().getUid());
                            user.setFirstname(firstname);
                            user.setLastname(eLastname);
                            user.setRego(eRego);

                            FirebaseFirestoreSettings settings =
                                    new FirebaseFirestoreSettings.Builder()
                                    .setTimestampsInSnapshotsEnabled(true)
                                    .build();
                            mDb.setFirestoreSettings(settings);

                            DocumentReference newUserRef = mDb
                                    .collection(getString(R.string.collection_users))
                                    .document(FirebaseAuth.getInstance().getUid());

                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    hideDialog();

                                    if (task.isSuccessful()) {
                                        redirectLoginScreen();
                                    } else {
                                        View parentLayout = findViewById(android.R.id.content);
                                        Snackbar.make(parentLayout, "Something went wrong.",
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "Something went wrong.",
                                    Snackbar.LENGTH_SHORT).show();
                            hideDialog();
                        }

                        // ...
                    }
                });
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen() {
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void showDialog() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void changeRegisterState(View view)
    {
        boolean checked = ((ToggleButton)view).isChecked();
        if(checked)
        {
            mAddress.setVisibility(View.VISIBLE);
        }
        else
        {
            mAddress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_register: {
                Log.d(TAG, "onClick: attempting to register.");

                showDialog();

                //check for null valued EditText fields
                if (!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())) {

                    //check if passwords match
                    if (doStringsMatch(mPassword.getText().toString(),
                            mConfirmPassword.getText().toString())) {

                        //Initiate registration task
                        registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString(),
                                mFirstname.getText().toString(), mLastname.getText().toString(),
                                mRego.getText().toString());
                    } else {
                        Toast.makeText(RegistrationActivity.this,
                                "Passwords do not Match", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegistrationActivity.this,
                            "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.textview_login: {
                Log.d(TAG, "onClick: returning to login.");

                showDialog();
                Intent intent = new Intent(RegistrationActivity.this,
                        LoginActivity.class);
                startActivity(intent);

            }
        }
    }
}