package com.example.parkingfinder.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.parkingfinder.R;
import com.example.parkingfinder.model.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPassword;
    private EditText cnfPassword;
    private EditText mFirstname;
    private EditText mLastname;
    private EditText mRego;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mEmail = findViewById(R.id.edittext_email);
        mPassword = findViewById(R.id.edittext_password);
        cnfPassword = findViewById(R.id.edittext_cnf_password);
        mFirstname = findViewById(R.id.edittext_firstname);
        mLastname = findViewById(R.id.edittext_lastname);
        mRego = findViewById(R.id.edittext_rego);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.button_register).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }

    private void registerUser() {
        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        final String checkPassword = cnfPassword.getText().toString().trim();
        final String firstname = mFirstname.getText().toString().trim();
        final String lastname = mLastname.getText().toString().trim();
        final String rego = mRego.getText().toString().trim();

        if (email.isEmpty()) {
            mEmail.setError(getString(R.string.input_error_email));
            mEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError(getString(R.string.input_error_email));
            mEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mPassword.setError(getString(R.string.input_error_password));
            mPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mPassword.setError(getString(R.string.input_error_password_length));
            mPassword.requestFocus();
            return;
        }

        if (firstname.isEmpty()) {
            mFirstname.setError(getString(R.string.input_error_name));
            mFirstname.requestFocus();
            return;
        }

        if (lastname.isEmpty()) {
            mLastname.setError(getString(R.string.input_error_name));
            mLastname.requestFocus();
            return;
        }

        if (rego.isEmpty()) {
            mRego.setError(getString(R.string.input_error_rego));
            mRego.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Customer customer = new Customer(
                                    email,
                                    firstname,
                                    lastname,
                                    rego
                            );

                            FirebaseDatabase.getInstance().getReference("Customers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                registerUser();
                break;
        }
    }
}