package com.pp1.parkingfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Leaser;

import java.util.ArrayList;

public class ParkingListingAdapter extends RecyclerView.Adapter<ParkingListingAdapter.ViewHolder>{

    private ArrayList<Leaser> mListings = new ArrayList<>();

    public ParkingListingAdapter(ArrayList<Leaser> listings) {
        this.mListings = listings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.location_list_item_fragment, parent,
                false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ((ViewHolder)holder).username.setText(mListings.get(position).getUsername());
        //((ViewHolder)holder).carpark.settext(mListings.get(position).getCarpark().toString(getLatitude()));

        FirebaseDatabase.getInstance().getReference().child("Leaser").child(String.valueOf(position)).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String carPark = dataSnapshot.child("carpark").getValue().toString();
                        String userEmail = dataSnapshot.child("email").getValue().toString();
                        String leaserID = dataSnapshot.child("leaser_id").getValue().toString();


                        //SET TEXT TO VIEW


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                }
        );
    }

    @Override
    public int getItemCount() {
        return mListings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView username, carpark;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.location_list_recycler_view);
            carpark = itemView.findViewById(R.id.location_list_recycler_view);
        }


    }

}