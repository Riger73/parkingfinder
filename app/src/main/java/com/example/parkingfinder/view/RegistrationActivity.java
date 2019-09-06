package com.example.parkingfinder.view;

import android.os.Bundle;

import com.example.parkingfinder.R;
import com.example.parkingfinder.model.Constants;
import com.example.parkingfinder.model.data.IPF_Endpoint;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.parkingfinder.R.id.bRegistration;

public class RegistrationActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private EditText firstName;
    private EditText lastName;
    private IPF_Endpoint mPF_Endpoint;

    // Initialises retrofit HTTP transfer - gets data from endpoint
    private void initAnonService() {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(Constants.URL_PF_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mPF_Endpoint = retrofit.create(IPF_Endpoint.class);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);

        Button bRegistration = findViewById(R.id.bRegistration);

        bRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {
        if (isEmpty(firstName)) {
            Toast t = Toast.makeText(this, "This field can't be blank, please enter " +
                    "your First Name", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(lastName)) {
            lastName.setError("This field can't be blank, please enter your Last Name");
        }
    }
}