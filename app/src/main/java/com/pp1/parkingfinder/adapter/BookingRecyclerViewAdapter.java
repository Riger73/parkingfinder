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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.pp1.parkingfinder.model.Constants.ADDRESS;
import static com.pp1.parkingfinder.model.Constants.AVAILABILITY;
import static com.pp1.parkingfinder.model.Constants.EMAIL;


public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Booking> bookings;

    public BookingRecyclerViewAdapter(Context context, ArrayList<Booking> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.booking_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Collects each entity in our listing list
        Booking booking = bookings.get(position);
        viewHolder.tvEmail.setText(booking.getEmail());
        viewHolder.tvAddress.setText(booking.getAddress());
        viewHolder.tvAvailability.setText(booking.getAvailability());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    // Inserts Booking data into database
    public void deleteBooking(String email, String address,
                              String availability) {

        String user_id;
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put(EMAIL, email);
        bookingData.put(ADDRESS, address);
        bookingData.put(AVAILABILITY, availability);

        //DocumentReference newBooking =
        db.collection("Booking").document(user_id).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Booking instance deletion failed" + e.toString());
                    }
                });
    }

    // Holds the view for the RecyclerVView list element in Search Listing Activity
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvAddress, tvAvailability, tvEmail;

        public ViewHolder(@Nullable View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvEmail = itemView.findViewById(R.id.email);
            tvAddress = itemView.findViewById(R.id.address);
            tvAvailability = itemView.findViewById(R.id.availability);
        }

        @Override
        public void onClick(View v) {
            tvEmail = itemView.findViewById(R.id.email);
            tvAddress = itemView.findViewById(R.id.address);
            tvAvailability = itemView.findViewById(R.id.availability);
            String sEmail = tvEmail.getText().toString();
            String sAddress = tvAddress.getText().toString();
            String sAvailability = tvAvailability.getText().toString();
            int position = getAdapterPosition();
            deleteBooking(sEmail, sAddress, sAvailability);
            Log.d("Clicked", "onClick"  + position);
        }
    }
}
