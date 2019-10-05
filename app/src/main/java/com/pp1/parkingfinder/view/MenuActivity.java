package com.pp1.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.pp1.parkingfinder.R;

public class MenuActivity extends AppCompatActivity implements
        View.OnClickListener {
    // widgets
    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        findViewById(R.id.selectMap).setOnClickListener(this);
        findViewById(R.id.listLeases).setOnClickListener(this);
        findViewById(R.id.makeRating).setOnClickListener(this);
        findViewById(R.id.listBooking).setOnClickListener(this);
        findViewById(R.id.button_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectMap: {
                Intent intent = new Intent(MenuActivity.this,
                        MapActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.listLeases: {
                Intent intent = new Intent(MenuActivity.this,
                        SearchListingFragment.class);
                startActivity(intent);
                break;
            }
            case R.id.makeRating: {
                Intent intent = new Intent(MenuActivity.this,
                        BookingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.listBooking: {
                Intent intent = new Intent(MenuActivity.this,
                        ManageBookingsFragment.class);
                startActivity(intent);
                break;
            }
            case R.id.button_logout: {

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MenuActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                break;
            }


        }
    }
}