package com.pp1.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Booking;
import com.pp1.parkingfinder.model.Constants;
import com.pp1.parkingfinder.model.data.DatabaseHelper;
import com.pp1.parkingfinder.model.data.IEndpoint_Booking;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RatingActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextCustomer;
    EditText mTextEmail;
    EditText mTextBookingDate;
    EditText mTextAddress;
    EditText mTextRegistration;
    Button mButtonBooking;
    TextView mTextViewList;
    private IEndpoint_Booking bookingApi;

    Booking booking = new Booking();
    private void BookingDatagram
            (Booking booking) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl(Constants.URL_PF_ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient);
        Retrofit retrofit = retrofitBuilder.build();
        bookingApi = retrofit.create(IEndpoint_Booking.class);

        //Leaser listing =
        //        new Leaser(owner, contact, parkingspotID, description, latitude, longitude);
        Call<ResponseBody> callableResponse = bookingApi.createBooking(booking);
        //dumpCallableResponse(callableResponse);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity);

        db = new DatabaseHelper(this);
        mTextBookingDate = (EditText)findViewById(R.id.edittext_bookingdate);
        mTextCustomer = (EditText)findViewById(R.id.edittext_customer);
        mTextAddress = (EditText)findViewById(R.id.edittext_address);
        mTextEmail = (EditText) findViewById(R.id.edittext_email);
        mTextRegistration = (EditText) findViewById(R.id.edittext_registration);
        mButtonBooking = (Button)findViewById(R.id.button_booking);
        mTextViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent =
                        new Intent(RatingActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customer = mTextCustomer.getText().toString().trim();
                String address = mTextAddress.getText().toString().trim();
                String email = mTextEmail.getText().toString();
                String bookingdate = mTextBookingDate.getText().toString().trim();
                String registration = mTextRegistration.getText().toString().trim();

                Booking booking = new Booking();
                booking.setCustomer(customer);
                booking.setAddress(address);
                //booking.setEmail(email);
                booking.setAvailability(bookingdate);
                // Checks for empty values
                if(booking != null){
                    // Sends to endpoint
                   BookingDatagram(booking);
                    Toast.makeText(
                            RatingActivity.this,
                            "You have registered",Toast.LENGTH_SHORT).show();
                    Intent moveToLogin =
                            new Intent(
                                    RatingActivity.this,LoginActivity.class);
                    startActivity(moveToLogin);
                }
            }
        });

}}