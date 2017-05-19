package com.example.tomek.uberallescustomer.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.utils.CustomPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {



    TextView driverNameTextView,driverCarTextView, carPlateTextView, driverPhoneTextView;
    View rootView;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        View rootView2 = inflater.inflate(R.layout.fragment_driver_information,container,false);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Mapa"));
        tabLayout.addTab(tabLayout.newTab().setText("Podsumowanie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        driverNameTextView = (TextView) rootView2.findViewById(R.id.driver_name);
        driverCarTextView = (TextView) rootView2.findViewById(R.id.car_model);
        carPlateTextView = (TextView) rootView2.findViewById(R.id.plates_number);
        driverPhoneTextView = (TextView) rootView2.findViewById(R.id.driver_phone_number);

        Bundle bundle =this.getArguments();

        String driverPhone = (String) bundle.get("driverPhone");
        String driverName = (String) bundle.get("driverName");
        String carModel = (String) bundle.get("carName");
        String carPlatesNumber = (String) bundle.get("carPlateNumber");

        if(driverName == null){
            driverNameTextView.setText("Brak imienia kierowcy");
        } else {
            driverNameTextView.setText(driverName);
        }

        if(driverPhone == null){
            driverNameTextView.setText("Brak telefonu kierowcy");
        } else {
            driverPhoneTextView.setText(driverPhone);
        }

        if(carModel == null){
            driverNameTextView.setText("Brak modelu samochodu kierowcy");
        } else {
            driverCarTextView.setText(carModel);
        }

        if(carPlatesNumber == null){
            driverNameTextView.setText("Brak rejestracji samochodu kierowcy");
        } else {
            carPlateTextView.setText(carPlatesNumber);
        }


        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        PagerAdapter adapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return rootView;
    }


}
