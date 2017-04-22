package com.example.tomek.uberallescustomer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tomek.uberallescustomer.fragments.HistoryFragment;
import com.example.tomek.uberallescustomer.fragments.OrderFragment;
import com.example.tomek.uberallescustomer.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    OrderFragment orderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        ButterKnife.bind(this);

        initNavigationView();

        orderFragment = new OrderFragment();
        openFragment(orderFragment);

    }

    public void initNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_history:
                                HistoryFragment historyFragment = new HistoryFragment();
                                openFragment(historyFragment);
                                break;
                            case R.id.action_order:
                                orderFragment = new OrderFragment();
                                openFragment(orderFragment);
                                break;
                            case R.id.action_settings:
                                SettingsFragment settingsFragment = new SettingsFragment();
                                openFragment(settingsFragment);
                                break;
                            default:
                                orderFragment = new OrderFragment();
                                openFragment(orderFragment);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    private void checkItem(MenuItem item) {
        item.setIcon(getResources().getDrawable(R.drawable.ic_local_taxi_yellow_24dp));
        BottomNavigationItemView m = (BottomNavigationItemView) findViewById(R.id.action_settings);
        m.setIcon(getResources().getDrawable(R.drawable.ic_settings_black_24dp));
        m = (BottomNavigationItemView) findViewById(R.id.action_history);
        m.setIcon(getResources().getDrawable(R.drawable.ic_history_black_24dp));
        m = (BottomNavigationItemView) findViewById(R.id.action_order);
        m.setIcon(getResources().getDrawable(R.drawable.ic_local_taxi_black_1_24dp));
        switch (item.getItemId()) {
            case R.id.action_history:
                m = (BottomNavigationItemView) findViewById(R.id.action_history);
                m.setIcon(getResources().getDrawable(R.drawable.ic_history_yellow_24dp));
                break;
            case R.id.action_order:
                m = (BottomNavigationItemView) findViewById(R.id.action_order);
                m.setIcon(getResources().getDrawable(R.drawable.ic_local_taxi_yellow_24dp));
                break;
            case R.id.action_settings:
                m = (BottomNavigationItemView) findViewById(R.id.action_settings);
                m.setIcon(getResources().getDrawable(R.drawable.ic_settings_yellow_24dp));
                break;
        }
    }
}
