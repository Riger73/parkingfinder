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

import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Listing;

import java.util.List;

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

    // Holds the view for the RecyuclerVView list element in Search Listing Activity
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

            int position = getAdapterPosition();
            Log.d("Clicked", "onClick"  + position);
        }
    }
}
