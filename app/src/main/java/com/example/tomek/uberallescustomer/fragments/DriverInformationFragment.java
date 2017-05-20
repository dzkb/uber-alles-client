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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomek.uberallescustomer.LogedUserData.ACTIVE_FARE_ID;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PASSWORD;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PHONE;
import static com.example.tomek.uberallescustomer.LogedUserData.deleteFareByKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverInformationFragment extends Fragment {

    private View rootView;
    Button cancelRide;
    TextView driverNameTextView,driverCarTextView, carPlateTextView, driverPhoneTextView;

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

        bindViews();

        Bundle bundle = this.getArguments();

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

        return rootView;
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public static void deleteFare(final String fareId) {
        final String phoneNumber = USER_PHONE.toString();
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<Fare> call = fareService.deleteFare(fareId);

        call.enqueue(new Callback<Fare>() {
            @Override
            public void onResponse(Call<Fare> call, Response<Fare> response) {
                if (response.isSuccessful()) {
                    Log.d("OK", "Anulowano");
                    deleteFareByKey(fareId);

                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<Fare> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void bindViews(){

        driverNameTextView = (TextView) rootView.findViewById(R.id.driver_name);
        driverCarTextView = (TextView) rootView.findViewById(R.id.car_model);
        carPlateTextView = (TextView) rootView.findViewById(R.id.plates_number);
        driverPhoneTextView = (TextView) rootView.findViewById(R.id.driver_phone_number);

    }
}
