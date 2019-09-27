package com.example.parkingfinder.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.parkingfinder.R;
import com.example.parkingfinder.controller.MenuActivity;
import com.example.parkingfinder.model.Customer;
import com.example.parkingfinder.model.data.DatabaseHelper;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
//import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText mTextEmail;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DatabaseHelper db;
//    ViewGroup progressView;
//    protected boolean isProgressShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        //View v = this.getLayoutInflater().inflate(R.layout.progressbar,null);
        //dialog.setContentView(v);
        //dialog.show();

        db = new DatabaseHelper(this);
        mTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonLogin = (Button) findViewById(R.id.buttonLogin);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent =
                        new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mTextEmail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(email, pwd);
                if (res == true) {
                    Intent HomePage = new Intent(
                            LoginActivity.this, MenuActivity.class);
                    startActivity(HomePage);
                } else {
                    Toast.makeText(
                            LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

  /*  public void showProgressingView() {

        if (!isProgressShowing) {
            View view=findViewById(R.id.progressBar1);
            view.bringToFront();
        }
    }

    public void hideProgressingView() {
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }
   */
}