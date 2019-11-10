package com.pp1.parkingfinder.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.adapter.BookingRecyclerViewAdapter;
import com.pp1.parkingfinder.model.Booking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
Creates activity to display customer bookings on search.
Customers can manage a booking. Currently only includes the ability to cancel
 */
public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    // Collection for RecyclerView items
    ArrayList<Booking> bookings = new ArrayList<>();
    private Context context;
    ArrayAdapter arrayAdapter;

    private static final String TAG = "BookingActivity";
    private static final String KEY_DESCRIPTION = "description";
    FirebaseAuth mAuth;

    private TextView textViewLocation;

    // private ListView listViewParkingListings;
    RecyclerView recyclerView;
    BookingRecyclerViewAdapter bookingRecyclerViewAdapter;

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

        // listViewParkingListings = findViewById(R.id.listViewParkingListings);
        textViewLocation = findViewById(R.id.textViewLocation);

        // Action on button click - Calls button lister on object
        findViewById(R.id.btMenu).setOnClickListener(this);
        findViewById(R.id.btLogout).setOnClickListener(this);

        loadBookingListings();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingRecyclerViewAdapter = new BookingRecyclerViewAdapter(this, bookings);

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
        switch (view.getId()){
            case R.id.btMenu: {

                Intent intent = new Intent(BookingActivity.this,
                        MenuActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btLogout: {

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(BookingActivity.this,
                        LoginActivity.class);
                startActivity(intent);

                break;
            }
        }
    }


    private void loadDatePickerSearch() {
        // todo
    }


    private void loadAddressSearch() {
        // todo
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