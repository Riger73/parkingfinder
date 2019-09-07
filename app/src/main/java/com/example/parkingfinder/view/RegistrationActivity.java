package com.example.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingfinder.R;
import com.example.parkingfinder.model.Constants;
import com.example.parkingfinder.model.data.DatabaseHelper;
import com.example.parkingfinder.model.data.IPF_Endpoint;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    EditText mTextFirstname;
    EditText mTextLastname;
    Button mButtonRegister;
    TextView mTextViewLogin;
    private IPF_Endpoint mPF_Endpoint;

    // Initialises retrofit HTTP transfer - gets data from endpoint
    private void initAnonService() {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(Constants.URL_PF_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mPF_Endpoint = retrofit.create(IPF_Endpoint.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mTextCnfPassword = (EditText)findViewById(R.id.edittext_cnf_password);
        mTextFirstname = (EditText) findViewById(R.id.edittext_firstname);
        mTextLastname = (EditText) findViewById(R.id.edittext_lastname);
        mButtonRegister = (Button)findViewById(R.id.button_register);
        mTextViewLogin = (TextView)findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent =
                        new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();
                String firstname = mTextFirstname.getText().toString().trim();
                String lastname = mTextLastname.getText().toString().trim();

                if(pwd.equals(cnf_pwd)){
                    long val = db.addUser(user,pwd,firstname,lastname);
                    if(val > 0){
                        Toast.makeText(
                                RegistrationActivity.this,
                                "You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin =
                                new Intent(
                                        RegistrationActivity.this,LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(
                                RegistrationActivity.this,
                                "Registeration Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(
                            RegistrationActivity.this,
                            "Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

