package com.pp1.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.User;

import java.util.HashMap;
import java.util.Map;

import static android.text.TextUtils.isEmpty;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.pp1.parkingfinder.model.Constants.ADDRESS;
import static com.pp1.parkingfinder.model.Constants.AVAILABILITY;
import static com.pp1.parkingfinder.model.Constants.EMAIL;
import static com.pp1.parkingfinder.util.Helper.doStringsMatch;


public class AddListingActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "AddListingActivity";
    //widgets
    private EditText mAddress, mAvailability;

    private ProgressBar mProgressBar;

    //variabless
    private FirebaseFirestore mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_listing_activity);
        mAddress = (EditText) findViewById(R.id.edittext_address);
        mAvailability = (EditText) findViewById(R.id.edittext_availability);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        // Action on button click - Calls button lister on object
        findViewById(R.id.btLogout).setOnClickListener(this);
        findViewById(R.id.btMenu).setOnClickListener(this);

        hideSoftKeyboard();

    }

    public void addListing(final String address, String availability) {
        final String eFirstname = address;
        final String eLastname = availability;

        showDialog();
        String email, user_id;
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> listingData = new HashMap<>();
        listingData.put(ADDRESS, address);
        listingData.put(AVAILABILITY, availability);

        //DocumentReference newBooking =
        db.collection("Listing").document(user_id).set(listingData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddListingActivity.this, "Listing Added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Listing database update failed" + e.toString());
                    }
                });
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen() {

        Intent intent = new Intent(AddListingActivity.this, LoginActivity.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_addlisting: {

                //check for null valued EditText fields
                if (!isEmpty(mAddress.getText().toString())
                        && !isEmpty(mAvailability.getText().toString())) {

                        //Initiate registration task
                        addListing(mAddress.getText().toString(),
                                mAvailability.getText().toString());
                    }  else {
                    Toast.makeText(AddListingActivity.this,
                            "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                    }
                break;
            }
            case R.id.btLogout: {
                Intent intent = new Intent(AddListingActivity.this,
                        LoginActivity.class);
                startActivity(intent);

            }
            case R.id.btMenu: {

                Intent intent = new Intent(AddListingActivity.this,
                        MenuActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}