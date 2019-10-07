package com.pp1.parkingfinder.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Leaser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchListingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SearchLisitngActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    FirebaseAuth mAuth;

    private EditText editTextLocation;
    private TextView listViewParkingListings;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference listingRef = db.collection("Leasers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_listing_activity);
        mAuth = FirebaseAuth.getInstance();

        editTextLocation = findViewById(R.id.editTextLocation);
        listViewParkingListings = findViewById(R.id.listViewParkingListings);

        findViewById(R.id.btSearch).setOnClickListener(this);

    }

    private String getAddress(LatLng coordinates) {
        String parkingSpots = "";
        Geocoder geocoder = new Geocoder(SearchListingActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation
                    (coordinates.latitude, coordinates.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            parkingSpots = addresses.get(0).getPremises();

        } catch(IOException e) {
            e.printStackTrace();
        }
        return parkingSpots;
    }


    @Override
    protected void onStart() {
        super.onStart();

        // If the user isn't logged in it will redirect to the login screen
        //if (mAuth.getCurrentUser() == null) {
        //    finish();
        //    startActivity(new Intent(this, LoginActivity.class));
        //}
    }

    private void loadParkingLisitngs() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Leasers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String listData = "";
                            for(QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Leaser leaser = document.toObject(Leaser.class);

                                String email = leaser.getEmail();
                                String firstname = leaser.getFirstname();

                                GeoPoint carpark = leaser.getCarpark();
                                LatLng coordinates = new LatLng(carpark.getLatitude(), carpark.getLongitude());
                                String address = getAddress(coordinates);
                                listData += "\nLeaser: " + firstname + "\nCar spase for lease: "
                                        + address + "\nContact details: " + email + "\n";
                            }
                            listViewParkingListings.setText(listData);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSearch:{
                loadParkingLisitngs();
                break;
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}