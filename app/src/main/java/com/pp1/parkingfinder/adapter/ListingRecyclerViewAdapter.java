/*
  Adapter to construct RecyclerView list items for listings view
 */
package com.pp1.parkingfinder.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Listing;
import com.pp1.parkingfinder.view.BookingActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.pp1.parkingfinder.model.Constants.ADDRESS;
import static com.pp1.parkingfinder.model.Constants.AVAILABILITY;
import static com.pp1.parkingfinder.model.Constants.EMAIL;

/*
Adapter class that displays each listing in a card. This is displayed in a fragment in the
"SearchListingActivity" class. Incorporates a booking method for customers to make bookings
*/
public class ListingRecyclerViewAdapter extends RecyclerView.Adapter<ListingRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Listing> listings;

    // initialize popup alert
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    Button btConfirm;

    // Adapter constructor
    public ListingRecyclerViewAdapter(Context context, List<Listing> listings) {
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
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put(EMAIL, email);
        bookingData.put(ADDRESS, address);
        bookingData.put(AVAILABILITY, availability);

        //DocumentReference newBooking =
        db.collection("Booking").document(user_id).set(bookingData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Booking Successful",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: Booking database update failed" + e.toString());
                    }
                });
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

        // Method to create a popup alert on booking
        private void createConfirmationPopup(){

            //todo - makes a popup to confirm if the customer wants to make the booking
        }

        @Override
        public void onClick(View v) {
            tvAddress = itemView.findViewById(R.id.address);
            tvAvailability = itemView.findViewById(R.id.availability);
            String sAddress = tvAddress.getText().toString();
            String sAvailability = tvAvailability.getText().toString();
            int position = getAdapterPosition();
            Listing listing = listings.get(position);

            createBooking(sAddress, sAvailability);

            Intent intent = new Intent(context, BookingActivity.class);

            context.startActivity(intent);
        }
    }
}