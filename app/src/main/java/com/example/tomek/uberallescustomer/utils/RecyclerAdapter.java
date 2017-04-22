package com.example.tomek.uberallescustomer.utils;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.Point;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Fare> faresList;

    public RecyclerAdapter(ArrayList<Fare> faresList) {
        this.faresList = faresList;
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

        Fare fare = faresList.get(position);
        Point startPoint = fare.getStartingPoint();
        Point destinationPoint = fare.getEndingPoint();
        holder.startPoint.setText( String.valueOf((fare.getStartingPoint().getLatitude() +", "+ fare.getStartingPoint().getLongitude())));
        holder.destinationPoint.setText(String.valueOf((fare.getEndingPoint().getLatitude() +", "+ fare.getEndingPoint().getLongitude())));
        holder.date.setText(fare.getStartingDate());
    }

    @Override
    public int getItemCount() {
        return faresList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView startPoint;
        private TextView destinationPoint;
        private TextView date;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            startPoint = (TextView) itemView.findViewById(R.id.start_point_name);
            destinationPoint = (TextView) itemView.findViewById(R.id.destination_point_name);
            date = (TextView) itemView.findViewById(R.id.fare_date
            );
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
