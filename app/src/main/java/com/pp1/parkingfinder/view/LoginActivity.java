package com.pp1.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Customer;

/* Test */

public class LoginActivity extends AppCompatActivity  implements
        View.OnClickListener {

    private static final String TAG = "LoginActivity";

    // Declare button and view widgets
    EditText email, password;
    Button btnSignIn;
    TextView tvRegister;

    // Declares Firebase listener variable
    private FirebaseAuth.AuthStateListener mAuthListener;
    //FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnSignIn = findViewById(R.id.buttonLogin);
        tvRegister = findViewById(R.id.textViewRegister);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginActivity.this.email.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    LoginActivity.this.email.setError("Please enter email id");
                    LoginActivity.this.email.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intToHome = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intSignUp);
            }
        });

    }
    private void setupFireBaseAuth() {
            Log.d(TAG, "setupFireBaseAuth(): initiated.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
        // @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
            FirebaseUser customer;
            FirebaseUser leaser;
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                Log.d(TAG, "onAuthStateChanged():signed_in: " + user.getUid());
                Toast.makeText
                        (LoginActivity.this, "Successful login as: "
                                + user.getEmail(), Toast.LENGTH_SHORT).show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings =
                        new FirebaseFirestoreSettings.Builder()
                                .build();
                db.setFirestoreSettings(settings);

                DocumentReference userRef =
                        db.collection(getString(R.string.collection_customers))
                                .document(user.getUid());

                userRef.get().addOnCompleteListener
                        (new OnCompleteListner<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "onComplete(): successfully set user.");
                            Customer customer = task.getResult().toObject(Customer.class);
                            ((UserClient)(getApplicationContext())).setUser(customer);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
