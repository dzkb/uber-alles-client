package com.example.tomek.uberallescustomer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tomek.uberallescustomer.api.ApiClient;
import com.example.tomek.uberallescustomer.api.UserService;
import com.example.tomek.uberallescustomer.api.pojo.CreateAccount;
import com.example.tomek.uberallescustomer.api.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void requestCreate() {
        UserService loginService =
                ApiClient.createService(UserService.class, "700800300", "dupa.8");
        CreateAccount acc = new CreateAccount(884056858, "pashhs","password","password");
        Call<User> call = loginService.createAccount(acc);
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // user object available
                    System.out.println("udalo sie");
                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
}
