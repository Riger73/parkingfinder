package com.pp1.parkingfinder.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Leaser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SearchListingActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    // Collection for ListView items
    ArrayList<String> listings = new ArrayList<>();
    HashMap<String, LatLng> location = new HashMap<String, LatLng>();

    ArrayAdapter arrayAdapter;

    private static final String TAG = "SearchLisitngActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    FirebaseAuth mAuth;

    private EditText editTextLocation;
    private MapFragment mMapView;
    private ListView listViewParkingListings;
    private DatePicker datePicker;

    // With current logged in user, calls collection called "Leasers" from remote data store.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference listingRef = db.collection("Leasers");

    // Calls google maps
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_listing_activity);
        mAuth = FirebaseAuth.getInstance();
        mMapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));

        editTextLocation = findViewById(R.id.editTextLocation);
        listViewParkingListings = findViewById(R.id.listViewParkingListings);

        // Waits for a button click action for booking button
        findViewById(R.id.btBook).setOnClickListener(this);

        loadParkingLisitngs();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Leasers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot document : task.getResult()) {

                                // Todo - retrieving geopoints
                                GeoPoint gc = document.getGeoPoint("carpark");
                                double lat = gc.getLatitude();
                                double lng = gc.getLongitude ();
                                LatLng latLng = new LatLng(lat, lng);
                                String firstname = document.getString("firstname");

                                mMap.addMarker(new MarkerOptions().position(latLng).title(firstname));
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng
                                        (lat, lng)).zoom(15).build();

                                mMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(cameraPosition));
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // If the user isn't logged in it will redirect to the login screen
        // if (mAuth.getCurrentUser() == null) {
        //    finish();
        //    startActivity(new Intent(this, LoginActivity.class));
        //}
    }

    private void loadParkingLisitngs() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listings);

        listViewParkingListings.setAdapter(arrayAdapter);

        db.collection("Leasers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            /* Todo -
                                Task 1) Change "carpark" item into String type and not LatLng
                            *   or Geopoint
                            *   Task 2) Implement "availability" as a datetime field in the listing
                            */
                            String listData = "";
                            for(QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                // Todo - Make "address" a string deal with Geopoints later

                                String email = document.getString("email");
                                String firstname = document.getString("firstname");
                                String addressName = document.getString("address");

                                // Todo - implement "availability" as a datetime field from database

                                Timestamp ts = document.getTimestamp("availability");
                                long seconds = ts.getSeconds();
                                int nanoSeconds = ts.getNanoseconds();

                                Timestamp time = new Timestamp(seconds, nanoSeconds);
                                String availability = timestampToString(time.getSeconds());

                                // Todo - retrieving geopoints
                                GeoPoint gc = document.getGeoPoint("carpark");
                                double lat = gc.getLatitude();
                                double lng = gc.getLongitude ();
                                LatLng latLng = new LatLng(lat, lng);

                                location.put(firstname, latLng);

                                // Lists and formats all data fields required
                                // Todo - add String "carpark" address and Datestime for datbase

                                listData += "\nLeaser: " + firstname + "\nContact details: " + email +
                                        "\n" + "\nAddress: " + addressName + "\n" +
                                        "\nAvailability: " + availability + "\n" +
                                        "\nGeoPoint: " + latLng + "\n";

                                //Log.d(TAG, "Leaser String: " +  listData);
                            }
                            listings.add(listData);
                            arrayAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


        public void onClick(View view) {
        switch (view.getId()){
            case R.id.btBook:{

                break;
            }
        }
    }

    private void loadDatePickerSearch() {
        //TO BE COMPLETED
    }

    private void loadAddressSearch() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public String timestampToString(Long date)
    {
        String leaserDate;

        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        leaserDate = sfd.format(new Date(date*1000));

        return leaserDate;

    }

}