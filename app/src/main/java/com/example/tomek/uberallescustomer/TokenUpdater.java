package com.example.tomek.uberallescustomer;

import android.util.Log;

import com.example.tomek.uberallescustomer.api.ApiClient;
import com.example.tomek.uberallescustomer.api.UserService;
import com.example.tomek.uberallescustomer.api.pojo.RegistrationToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tomek.uberallescustomer.LogedUserData.USER_PASSWORD;
import static com.example.tomek.uberallescustomer.LogedUserData.USER_PHONE;


public class TokenUpdater {
    public static void updateRegistrationToken(final RegistrationToken registrationToken) {
        final String phoneNumber = USER_PHONE;
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<String> call = fareService.putRegistrationToken(registrationToken);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TOKEN", "Zaktualizowano registration token");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
