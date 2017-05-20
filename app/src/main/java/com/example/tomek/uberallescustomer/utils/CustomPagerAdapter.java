package com.example.tomek.uberallescustomer.utils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tomek.uberallescustomer.fragments.DriverInformationFragment;
import com.example.tomek.uberallescustomer.fragments.SummaryMapFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    int tabCount;
    Bundle extras;

    public CustomPagerAdapter(FragmentManager fm, int tabCount, Bundle extras) {
        super(fm);
        this.tabCount = tabCount;
        this.extras = extras;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SummaryMapFragment summaryMapFragment = new SummaryMapFragment();
                return summaryMapFragment;
            case 1:
                DriverInformationFragment driverInformationFragment = new DriverInformationFragment();
                if(extras != null){
                    driverInformationFragment.setArguments(extras);
                }
                return driverInformationFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }


}
