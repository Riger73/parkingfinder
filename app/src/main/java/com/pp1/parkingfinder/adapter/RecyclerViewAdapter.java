/*
  Adapter to construct RecyclerView list items for listings view
 */
package com.pp1.parkingfinder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Booking;
import com.pp1.parkingfinder.model.Listing;
import com.pp1.parkingfinder.view.SearchListingActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Listing> listings;

    public RecyclerViewAdapter(Context context, List<Listing> listings) {
        this.context = context;
        this.listings = listings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listing_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Collects each entity in our listing list
        Listing listing = listings.get(position);
        viewHolder.tvAddress.setText(listing.getAddress());
        viewHolder.tvAvailability.setText(listing.getAvailability());
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    // Inserts Booking data into database
    public void createBooking (String address,
                               String availability) {

        String email, user_id;
        Booking booking = new Booking();
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        booking.setCustomer(user_id);
        booking.setEmail(email);
        booking.setAddress(address);
        booking.setAvailability(availability);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();

        db.collection(data)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            String listData = "";
                            for(QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                // Todo - Make "address" a string deal with Geopoints later
                                Listing lists = new Listing();

                                lists.setAddress(document.getString("address"));
                                lists.setAvailability(document.getString("availability"));

                                listings.add(lists);

                            }

                            //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listings);
                            recyclerViewAdapter = new RecyclerViewAdapter(SearchListingActivity.this, listings);

                            //listViewParkingListings.setAdapter(arrayAdapter);
                            recyclerView.setAdapter(recyclerViewAdapter);

                            recyclerViewAdapter.notifyDataSetChanged();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        Map<String, Object> data = new HashMap<>();

        DocumentReference newBooking = db.collection("booking").document();

// Later...
        newBooking.set(data);

    }

    // Holds the view for the RecyclerVView list element in Search Listing Activity
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvAddress, tvAvailability;

        public ViewHolder(@Nullable View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvAddress = itemView.findViewById(R.id.address);
            tvAvailability = itemView.findViewById(R.id.availability);
        }

        @Override
        public void onClick(View v) {
            tvAddress = itemView.findViewById(R.id.address);
            tvAvailability = itemView.findViewById(R.id.availability);
            String sAddress = tvAddress.getText().toString();
            String sAvailability = tvAvailability.getText().toString();
            int position = getAdapterPosition();
            createBooking(sAddress, sAvailability);
            Log.d("Clicked", "onClick"  + position);
        }
    }
}
