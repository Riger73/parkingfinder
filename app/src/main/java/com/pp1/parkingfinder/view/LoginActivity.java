package com.pp1.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.pp1.parkingfinder.R;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginActivity extends AppCompatActivity {

    private static boolean chkUser = true;

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    //Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.login_activity);

        inputEmail = (EditText) findViewById(R.id.editTextEmail);
        inputPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        // if user logged in, go to sign-in screen
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public void onLoginClicked(View view) {
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        if (password.length() < 6) {
            inputPassword.setError(getString(R.string.minimum_password));
        }
        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                            Log.e("MyTag", task.getException().toString());
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            // Gets the UID of the logged in user
                            String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DocumentReference docRef = db.collection("User").
                                    document("currentuser");
                            docRef.get().addOnSuccessListener
                                    (new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(@NonNull DocumentSnapshot document) {
                                    if (document.exists()) {
                                        Log.i(TAG, "DocumentSnapshot data: " + document.getData());
                                        chkUser = (boolean)document.get("IsLeaser");
                                        Log.d(TAG, "This a boolean: " + chkUser);
                                    } else {
                                        Log.e(TAG, "No such document");
                                    }
                                }
                            });
                            if(chkUser == true){
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, AddListingActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
    }


    public void onRegisterClicked(View view) {
        startActivity(new Intent(this, RegistrationActivity .class));
    }
}