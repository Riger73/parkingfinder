package com.pp1.parkingfinder.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pp1.parkingfinder.R;
import com.pp1.parkingfinder.model.Listing;

import java.util.ArrayList;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder>{

    private ArrayList<Listing> mListings = new ArrayList<>();


    public LocationRecyclerAdapter(ArrayList<Listing> users) {
        this.mListings = users;
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
        ((ViewHolder)holder).address.setText(mListings.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return mListings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView username, address;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.location_list_recycler_view);
            address = itemView.findViewById(R.id.location_list_recycler_view);
        }


    }

}