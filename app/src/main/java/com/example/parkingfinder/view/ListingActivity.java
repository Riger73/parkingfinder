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
import com.example.parkingfinder.model.Constants;
import com.example.parkingfinder.model.Listing;
import com.example.parkingfinder.model.data.DatabaseHelper;
import com.example.parkingfinder.model.data.IEndpoint_Listing;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListingActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextOwner;
    EditText mTextContact;
    EditText mTextParkingspotID;
    EditText mTextDescription;
    EditText mTextAddress;
    EditText mTextLongitude;
    Button mButtonListing;
    TextView mTextViewList;
    private IEndpoint_Listing listingApi;

    // Initialises retrofit HTTP transfer - gets data from endpoint
    private void listingDatagram
    (Listing listing) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl(Constants.URL_PF_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient);
        Retrofit retrofit = retrofitBuilder.build();
        listingApi = retrofit.create(IEndpoint_Listing.class);

        //Listing listing =
        //        new Listing(owner, contact, parkingspotID, description, latitude, longitude);
        Call<ResponseBody> callableResponse = listingApi.createListing(listing);
        //dumpCallableResponse(callableResponse);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing_activity);

        db = new DatabaseHelper(this);
        mTextOwner = (EditText)findViewById(R.id.edittext_owner);
        mTextContact = (EditText)findViewById(R.id.edittext_contact);
        mTextParkingspotID = (EditText)findViewById(R.id.edittext_parkingspotID);
        mTextDescription = (EditText) findViewById(R.id.edittext_description);
        mTextAddress = (EditText) findViewById(R.id.edittext_address);
        mButtonListing = (Button)findViewById(R.id.button_listing);
        //mTextViewList= (TextView)findViewById(R.id.textview_list);
        mTextViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent =
                        new Intent(ListingActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String owner = mTextOwner.getText().toString().trim();
                String contact = mTextContact.getText().toString().trim();
                String parkingSpotID = mTextParkingspotID.getText().toString().trim();
                String description = mTextDescription.getText().toString().trim();
                String latitude = mTextAddress.getText().toString().trim();
                 Listing listing = new Listing();
                listing.setOwner(owner);
                listing.setContact(contact);
                listing.setParkingSpotID(parkingSpotID);
                listing.setDescription(description);
                listing.setLatitude(latitude);

                    // Checks for empty values
                    if(listing != null){
                        // Sends to endpoint
                        listingDatagram(listing);
                        Toast.makeText(
                                ListingActivity.this,
                                "You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin =
                                new Intent(
                                        ListingActivity.this,LoginActivity.class);
                        startActivity(moveToLogin);
                    }
            }
        });
    }
}