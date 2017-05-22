package com.example.tomek.uberallescustomer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.tomek.uberallescustomer.database.FeedReaderDbHelper;
import com.example.tomek.uberallescustomer.fragments.SummaryFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.tomek.uberallescustomer.LogedUserData.ACTIVE_FARE_ID;
import static com.example.tomek.uberallescustomer.LogedUserData.FARES_LIST;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //    ArrayList<Fare> faresList;
    ArrayList<HistorialFare> historialFares;
    Activity activity;
    Context context;
    private FeedReaderDbHelper helper;
    SummaryFragment summaryFragment;

    public RecyclerAdapter(ArrayList<HistorialFare> historialFares, Activity activity, Context context) {
        this.historialFares = historialFares;
        this.activity = activity;
        this.context = context;
        summaryFragment = new SummaryFragment();
        notifyDataSetChanged();
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, final int position) {

        getDataFromDatabase();
        final HistorialFare historialFare = historialFares.get(position);
        String startPointFromList = historialFare.getStartingPoint();
        String destinationPointFromList = historialFare.getEndingPoint();
        holder.startPoint.setText(startPointFromList);
        holder.destinationPoint.setText(destinationPointFromList);
        holder.date.setText(CommonDate.getFormattedTime(historialFare.getStartingDate()));



        if (historialFare.getStatus().equals("confirmed")) {
            holder.cardView.setCardBackgroundColor(Color.RED);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle1 = new Bundle();
                    Boolean isFromHisoty = true;
                    bundle1.putString("id",historialFare.getFareId());

                    summaryFragment = new SummaryFragment();
                    summaryFragment.setArguments(bundle1);
                    openFragment(summaryFragment);


                }
            });
        } else {
            holder.cardView.setBackgroundColor(Color.LTGRAY);
        }



        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(activity)
                        .setTitle("Usun przejazd")
                        .setMessage("Czy napewno chcesz usunąć przejazd z histori?")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing (will close dialog)

                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do something
                                helper = new FeedReaderDbHelper(context);
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                String login = prefs.getString("Authentication_Id", " ");
                                helper.deleteById(login, historialFare.getFareId());
                                historialFares.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .create();
                alertDialog.show();

                return true;
            }
        });

    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void getDataFromDatabase (){

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
