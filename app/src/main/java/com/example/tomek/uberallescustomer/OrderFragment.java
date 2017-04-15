package com.example.tomek.uberallescustomer;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static com.example.tomek.uberallescustomer.Helper.compareTime;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    MapView mapView;

    private View rootView;
    public static final String OLD_DATE = "Podaj date z przyszłosci";
    private GoogleMap googleMap;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_order, container, false);


        mapView = initMap();


        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately


        initOnClick();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private MapView initMap() {
        mapView = (MapView) rootView.findViewById(R.id.map_view);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

            }
        });

        return mapView;

    }

    private void initOnClick() {


        ImageView myLocation = (ImageView) rootView.findViewById(R.id.location_image);
        FloatingActionButton fabAddLocation = (FloatingActionButton) rootView.findViewById(R.id.fab_add_location);
        final EditText startPosition = (EditText) rootView.findViewById(R.id.start_point_edit_text);
        final EditText destinantionPosition = (EditText) rootView.findViewById(R.id.descination_point_edit_text);
        final TextView time = (TextView) rootView.findViewById(R.id.time);

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(rootView.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(rootView.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.setMyLocationEnabled(true);

            }
        });

        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startLocation = startPosition.getText().toString();
                String destinationLocation = destinantionPosition.getText().toString();
                List<Address> addressList = null;
                List<Address> addressList1 = null;


                if (startLocation != null) {
                    Geocoder geocoder = new Geocoder(rootView.getContext());
                    try {
                        addressList = geocoder.getFromLocationName(startLocation, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(startLocation));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                }

                if (destinationLocation != null || !destinationLocation.equals("")) {
                    Geocoder geocoder = new Geocoder(rootView.getContext());
                    try {
                        addressList1 = geocoder.getFromLocationName(destinationLocation, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = addressList1.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(startLocation));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment date = new DatePickerFragment();
                date.journeyTime = time;
                date.show(getActivity().getSupportFragmentManager(), "DatePicker");
            }
        });


    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        static TextView journeyTime;
        static Calendar currentCalendar;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            currentCalendar.set(Calendar.MINUTE, minute);
            currentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            Calendar c = Calendar.getInstance();
            if (compareTime(currentCalendar, c)) {
                final StringBuilder sb = new StringBuilder(journeyTime.getText().length());
                sb.append(journeyTime.getText());
                journeyTime.setText(sb.toString() + StringUtils.leftPad(Integer.toString(hourOfDay), 2, '0') + ":" + StringUtils.leftPad(Integer.toString(minute), 2, '0'));
            } else journeyTime.setText(OLD_DATE);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        TextView journeyTime;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int givenYear, int givenMonth, int givenDay) {

            Calendar current = Calendar.getInstance();
            Calendar validDate = Calendar.getInstance();
            validDate.set(givenYear, givenMonth, givenDay);
            if (current.after(validDate)) {
                journeyTime.setText(OLD_DATE);
            } else {
                TimePickerFragment.journeyTime = journeyTime;
                TimePickerFragment.currentCalendar = validDate;
                journeyTime.setText(StringUtils.leftPad(Integer.toString(givenDay), 2, '0') + "/" + StringUtils.leftPad(Integer.toString(givenMonth), 2, '0') + " ");
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        }
    }
}