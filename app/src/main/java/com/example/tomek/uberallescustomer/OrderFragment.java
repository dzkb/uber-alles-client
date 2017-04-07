package com.example.tomek.uberallescustomer;


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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    MapView mapView;

    private View rootView;

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
        
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(rootView.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(rootView.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
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

                if(startLocation!=null){
                    Geocoder geocoder = new Geocoder(rootView.getContext());
                    try {
                        addressList = geocoder.getFromLocationName(startLocation,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = addressList.get(0);
                    LatLng latLng =  new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(startLocation));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                }

                if(destinationLocation!=null || !destinationLocation.equals("")){
                    Geocoder geocoder = new Geocoder(rootView.getContext());
                    try {
                        addressList1 = geocoder.getFromLocationName(destinationLocation,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = addressList1.get(0);
                    LatLng latLng =  new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(startLocation));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });



    }


}
