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

import com.example.tomek.uberallescustomer.R;
import com.example.tomek.uberallescustomer.api.ApiClient;
import com.example.tomek.uberallescustomer.api.UserService;
import com.example.tomek.uberallescustomer.api.pojo.Fare;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomek.uberallescustomer.LogedUserData.ACTIVE_FARE_ID;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PASSWORD;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PHONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverInformationFragment extends Fragment {

    private View rootView;
    Button cancelRide;


    public DriverInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_driver_information, container, false);

        cancelRide = (Button) rootView.findViewById(R.id.cancel_ride);
        cancelRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFare(ACTIVE_FARE_ID);
                OrderFragment orderFragment = new OrderFragment();
                openFragment(orderFragment);

            }
        });

        return rootView;
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    public void deleteFare(String fareId) {
        final String phoneNumber = USER_PHONE.toString();
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<Fare> call = fareService.deleteFare(fareId);

        call.enqueue(new Callback<Fare>() {
            @Override
            public void onResponse(Call<Fare> call, Response<Fare> response) {
                if (response.isSuccessful()) {
                    Log.d("OK", "Anulowano");
                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<Fare> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
}
