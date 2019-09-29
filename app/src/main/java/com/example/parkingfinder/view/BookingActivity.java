package com.example.parkingfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingfinder.R;
import com.example.parkingfinder.model.Booking;
import com.example.parkingfinder.model.Constants;
import com.example.parkingfinder.model.data.DatabaseHelper;
import com.example.parkingfinder.model.data.IEndpoint_Booking;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BookingActivity extends AppCompatActivity {

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
            (Booking Booking) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl(Constants.URL_PF_ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient);
        Retrofit retrofit = retrofitBuilder.build();
        bookingApi = retrofit.create(IEndpoint_Booking.class);

        //Listing listing =
        //        new Listing(owner, contact, parkingspotID, description, latitude, longitude);
        Call<ResponseBody> callableResponse = bookingApi.createListing(booking);
        //dumpCallableResponse(callableResponse);
    }

}