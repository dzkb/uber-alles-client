package com.example.tomek.uberallescustomer.utils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.tomek.uberallescustomer.fragments.DriverInformationFragment;
import com.example.tomek.uberallescustomer.fragments.SummaryMapFragment;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    Bundle extras;
    String id;

    public CustomPagerAdapter(FragmentManager fm, int tabCount, Bundle extras) {
        super(fm);
        this.tabCount = tabCount;
        this.extras = extras;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SummaryMapFragment summaryMapFragment = new SummaryMapFragment();
                return summaryMapFragment;
            case 1:

                DriverInformationFragment driverInformationFragment = new DriverInformationFragment();
                driverInformationFragment.setArguments(extras);

                return driverInformationFragment;
            default:
                SummaryMapFragment summaryMapFragment1 = new SummaryMapFragment();
                return summaryMapFragment1;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }


}
