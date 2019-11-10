package com.pp1.parkingfinder.view;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.adapter.ListingRecyclerViewAdapter;
import com.pp1.parkingfinder.model.Listing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
Creates activity to display parking listings on search.
Also displays an interactive map to show the listings on a map. Customers can make a booking by
selecting a location time combo which then directs them to manage their bookings
 */
public class SearchListingActivity extends AppCompatActivity
        implements View.OnClickListener, OnMapReadyCallback {

    // Collection for ListView items
    ArrayList<Listing> listings = new ArrayList<>();
    private Context context;
    ArrayAdapter arrayAdapter;

    // Initialize constants
    private static final String TAG = "SearchLisitngActivity";

    // Initialise FB authenticaiton
    FirebaseAuth mAuth;

    // initialize field variables
    private EditText editTextLocation;
    private MapFragment mMapView;

    // private ListView listViewParkingListings;
    RecyclerView recyclerView;
    ListingRecyclerViewAdapter listingRecyclerViewAdapter;

    // With current logged in user, calls collection called "Leasers" from remote data store.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference listingRef = db.collection("Leasers");

    // Calls google maps
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_listing_activity);

        recyclerView = findViewById(R.id.rvListings);
        mAuth = FirebaseAuth.getInstance();
        mMapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        // listViewParkingListings = findViewById(R.id.listViewParkingListings);
        editTextLocation = findViewById(R.id.textViewLocation);

        // Action on button click - Calls button lister on object
        findViewById(R.id.btMenu).setOnClickListener(this);
        findViewById(R.id.btLogout).setOnClickListener(this);

        loadParkingListings();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listingRecyclerViewAdapter = new ListingRecyclerViewAdapter(this, listings);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Converts String text address to LatLng coordinates
    public LatLng getLocationFromAddress(SearchListingActivity ls, String inputtedAddress) {

        Geocoder coder = new Geocoder(ls);
        List<Address> address;
        LatLng resLatLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(ls, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return resLatLng;
    }

    // Sets map coordinates and markers
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Listing")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot document : task.getResult()) {

                                String inputAddress = document.getString("address");

                                double lat = -37.8676;
                                double lng = 144.98099;

                                LatLng latLng = new LatLng(lat, lng);
                                latLng = getLocationFromAddress
                                        (SearchListingActivity.this, inputAddress);

                                mMap.addMarker(new MarkerOptions().position(latLng)
                                        .title(inputAddress));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(lat, lng)).zoom(15).build();

                                mMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));

                            }
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    // Loads parking listings from firestore
    private void loadParkingListings() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(getString(R.string.collection_parking_listings))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String listData = "";
                            for(QueryDocumentSnapshot document : task.getResult()) {

                                Listing lists = new Listing();
                                lists.setAddress(document.getString("address"));
                                lists.setAvailability(document.getString("availability"));

                                listings.add(lists);
                            }
                            listingRecyclerViewAdapter = new ListingRecyclerViewAdapter
                                    (SearchListingActivity.this, listings);

                            recyclerView.setAdapter(listingRecyclerViewAdapter);

                            listingRecyclerViewAdapter.notifyDataSetChanged();

                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    // Sets onClick behavior for buttons and interactive elements
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btMenu: {

                Intent intent = new Intent(SearchListingActivity.this,
                        MenuActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btLogout: {

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(SearchListingActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
    }


    private void loadDatePickerSearch() {
        //todo
    }


    private void loadAddressSearch() {
        //todo
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        //todo
    }

    //Helper method for converting timestamps to userfriendly String
    public String timestampToString(Long date) {
        String leaserDate;

        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        leaserDate = sfd.format(new Date(date*1000));

        return leaserDate;

    }
}