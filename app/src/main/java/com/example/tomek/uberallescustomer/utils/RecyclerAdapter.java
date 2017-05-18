package com.example.tomek.uberallescustomer.utils;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tomek.uberallescustomer.DetailsActivity;
import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.HistorialFare;
import com.example.tomek.uberallescustomer.api.pojo.Point;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.example.tomek.uberallescustomer.LogedUserData.ACTIVE_FARE_ID;
import static com.example.tomek.uberallescustomer.LogedUserData.FARES_LIST;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

//    ArrayList<Fare> faresList;
    ArrayList<HistorialFare> historialFares;
    Activity activity;

    public RecyclerAdapter(ArrayList<HistorialFare> historialFares, Activity activity) {
        this.historialFares = historialFares;
        this.activity = activity;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        final HistorialFare historialFare = historialFares.get(position);
        String startPointFromList = historialFare.getStartingPoint();
        String destinationPointFromList = historialFare.getEndingPoint();
        holder.startPoint.setText(startPointFromList);
        holder.destinationPoint.setText(destinationPointFromList);
        holder.date.setText(historialFare.getStartingDate());
//        Geocoder geocoder;
//        List<Address> addresses = null;
//        geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
//        double startPointLatitude = startPoint.getLatitude();
//        double startPointLongitude = startPoint.getLongitude();
//        try {
//            addresses = geocoder.getFromLocation(startPointLatitude, startPointLongitude, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//
//        holder.startPoint.setText(address);
//        List<Address> addressesDestination = null;
//        double destinationPointLatitude = destinationPoint.getLatitude();
//        double destinationPointLongitude = destinationPoint.getLongitude();
//        try {
//            addressesDestination = geocoder.getFromLocation(destinationPointLatitude, destinationPointLongitude, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String addressDestination = addressesDestination.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//
//        holder.destinationPoint.setText(addressDestination);
//        holder.date.setText(historialFare.getStartingDate());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ACTIVE_FARE_ID = getKeyByValue(FARES_LIST, his);
//                Bundle bundle = new Bundle();
//                bundle.putString("key", ACTIVE_FARE_ID);
//                Intent intent = new Intent(activity, DetailsActivity.class);
//                intent.putExtra("bundle", bundle);
//                activity.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return historialFares.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView startPoint;
        private TextView destinationPoint;
        private TextView date;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            startPoint = (TextView) itemView.findViewById(R.id.start_point_name);
            destinationPoint = (TextView) itemView.findViewById(R.id.destination_point_name);
            date = (TextView) itemView.findViewById(R.id.fare_date);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public static String getKeyByValue(Map<String, Fare> map, Fare value) {
        for (Map.Entry<String, Fare> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
