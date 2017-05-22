package com.example.tomek.uberallescustomer.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.database.FeedReaderDbHelper;
import com.example.tomek.uberallescustomer.utils.CustomPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {




    View rootView;
    CustomPagerAdapter adapter;
    ViewPager viewPager;
    FragmentManager fragmentManager;
    Bundle bundleFromHistorial;
    Boolean isFromHistory = false;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Mapa"));
        tabLayout.addTab(tabLayout.newTab().setText("Podsumowanie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FeedReaderDbHelper helper = new FeedReaderDbHelper(getContext());

        fragmentManager = getActivity().getSupportFragmentManager();


        Bundle bundle = this.getArguments();
        String id = (String) bundle.get("id");



        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        adapter = new CustomPagerAdapter(fragmentManager, tabLayout.getTabCount(), bundle);


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




    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewPager = null;
        fragmentManager = null;

        //viewPager.setAdapter(null);


    }
}
