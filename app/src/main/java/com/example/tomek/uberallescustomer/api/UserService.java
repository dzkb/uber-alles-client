package com.example.tomek.uberallescustomer.api;

import com.example.tomek.uberallescustomer.api.pojo.CreateAccount;
import com.example.tomek.uberallescustomer.api.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Krystian on 2017-04-16.
 */

public interface UserService {
    @GET("/users")
    Call<User> basicLogin();
    @POST("/users")
    Call<User> createAccount(@Body CreateAccount account);
}