package com.example.tomek.uberallescustomer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tomek.uberallescustomer.api.pojo.Driver;
import com.example.tomek.uberallescustomer.database.FeedReaderDbHelper;
import com.example.tomek.uberallescustomer.fragments.HistoryFragment;
import com.example.tomek.uberallescustomer.fragments.OrderFragment;
import com.example.tomek.uberallescustomer.fragments.SettingsFragment;
import com.example.tomek.uberallescustomer.fragments.SummaryFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    private static Context context;
    OrderFragment orderFragment;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        ButterKnife.bind(this);

        databaseExample();

        initNavigationView();
        context = getApplicationContext();
        orderFragment = new OrderFragment();
        openFragment(orderFragment);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("CMFareConfirmation"));
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

    public void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commitAllowingStateLoss();

    }

    public static Context giveMeContext() {
        return context;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            Bundle bundle = intent.getExtras();
            //FeedReaderDbHelper helper = new FeedReaderDbHelper();
            SummaryFragment summaryFragment = new SummaryFragment();
            summaryFragment.setArguments(bundle);
            openFragment(summaryFragment);
        }
    };

    private void databaseExample () {
        FeedReaderDbHelper helper = new FeedReaderDbHelper(getApplicationContext());
        Driver driver = new Driver("Janusz", 605631019, "Fiat 125p", "ODF 8000");
        helper.insertToHistory(helper.getWritableDatabase(),"id123",driver);

        HashMap<String,Driver> map = new HashMap<>();
        map=helper.selectDriversByFareId("id123");
    }

//
//    @Override
//    protected void onDestroy() {
//        // Unregister since the activity is about to be closed.
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
//        super.onDestroy();
//    }



}
