package com.pp1.parkingfinder.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

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
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.adapter.BookingRecyclerViewAdapter;
import com.pp1.parkingfinder.model.Booking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BookingActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    // Collection for ListView items
    ArrayList<Booking> bookings = new ArrayList<>();
    //HashMap<String, LatLng> location = new HashMap<String, LatLng>();

    ArrayAdapter arrayAdapter;

    private static final String TAG = "BookingActivity";
    private static final String KEY_DESCRIPTION = "description";
    FirebaseAuth mAuth;

    private EditText editTextLocation;
    private MapFragment mMapView;
    // private ListView listViewParkingListings;
    RecyclerView recyclerView;
    BookingRecyclerViewAdapter bookingRecyclerViewAdapter;

    private DatePicker datePicker;

    // With current logged in user, calls collection called "Leasers" from remote data store.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference listingRef = db.collection("Leasers");

    // Calls google maps
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity);

        recyclerView = findViewById(R.id.rvListings);
        mAuth = FirebaseAuth.getInstance();
        mMapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        // listViewParkingListings = findViewById(R.id.listViewParkingListings);
        editTextLocation = findViewById(R.id.editTextLocation);

        loadBookingListings();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingRecyclerViewAdapter = new BookingRecyclerViewAdapter(this, bookings);

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

    }

    private void loadBookingListings() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(getString(R.string.collection_parking_bookings))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String listData = "";
                            for(QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                // Todo - Make "address" a string deal with Geopoints later
                                Booking bookingLists = new Booking();

                                bookingLists.setEmail(document.getString("email"));
                                bookingLists.setAddress(document.getString("address"));
                                bookingLists.setAvailability(document.getString("availability"));

                                bookings.add(bookingLists);

                            }

                            //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bookings);
                            bookingRecyclerViewAdapter = new BookingRecyclerViewAdapter(BookingActivity.this, bookings);

                            //listViewParkingListings.setAdapter(arrayAdapter);
                            recyclerView.setAdapter(bookingRecyclerViewAdapter);

                            bookingRecyclerViewAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void onClick(View view) {

    }


    private void loadDatePickerSearch() {
        //TO BE COMPLETED
    }


    private void loadAddressSearch() {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public String timestampToString(Long date) {
        String leaserDate;

        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        leaserDate = sfd.format(new Date(date*1000));

        return leaserDate;

    }
}