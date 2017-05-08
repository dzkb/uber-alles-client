package com.example.tomek.uberallescustomer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.api.ApiClient;
import com.example.tomek.uberallescustomer.api.UserService;
import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.FareProof;
import com.example.tomek.uberallescustomer.api.pojo.FareTimes;
import com.example.tomek.uberallescustomer.api.pojo.Point;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomek.uberallescustomer.LogedUserData.ACTIVE_FARE_ID;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PASSWORD;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PHONE;
import static com.example.tomek.uberallescustomer.LogedUserData.addFare;
import static com.example.tomek.uberallescustomer.LogedUserData.times;
import static com.example.tomek.uberallescustomer.fragments.OrderFragment.TimePickerFragment.setTextView;
import static java.lang.String.format;

public class ConfirmFragment extends Fragment {

    private TextView arriveTime;

    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        arriveTime = (TextView) view.findViewById(R.id.confirm_time);
        initOnClick(view);

        return view;
    }

    private void initOnClick(View view) {
        Button confirmButton = (Button) view.findViewById(R.id.confirm_button);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        setArriveTime();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SummaryFragment summaryFragment = new SummaryFragment();
                Fare fare = getFareDetails(getArguments());
                createFare(fare);
                openFragment(summaryFragment);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFragment orderFragment = new OrderFragment();
                openFragment(orderFragment);
            }
        });
    }

    public void createFare(final Fare fare) {
        final String phoneNumber = USER_PHONE.toString();
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<FareProof> call = fareService.createFare(fare);

        call.enqueue(new Callback<FareProof>() {
            @Override
            public void onResponse(Call<FareProof> call, Response<FareProof> response) {
                if (response.isSuccessful()) {
                    ACTIVE_FARE_ID = response.body().getId();
                    addFare(ACTIVE_FARE_ID, fare);
                    Log.d("OK", "Wszystko spoko - " + ACTIVE_FARE_ID);
                } else {
                    Log.d("Error", "Coś poszło nie tak . . .");
                }
            }

            @Override
            public void onFailure(Call<FareProof> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    private static Fare getFareDetails(Bundle bundle) {
        Point point = null;
        Fare fare = new Fare();
        fare.setClientName(bundle.getString("name"));
        fare.setClientPhone(bundle.getInt("phone"));
        fare.setStartingDate(bundle.getString("time"));
        point = new Point(bundle.getDouble("StartLat"), bundle.getDouble("StartLong"));
        fare.setStartingPoint(point);
        point = new Point(bundle.getDouble("EndLat"), bundle.getDouble("EndLong"));
        fare.setEndingPoint(point);
        return fare;
    }

    private void setArriveTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, times.getMin());
        setTextView(arriveTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }
}
