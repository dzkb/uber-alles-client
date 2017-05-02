package com.example.tomek.uberallescustomer.api;

import com.example.tomek.uberallescustomer.api.pojo.CreateAccount;
import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.FareProof;
import com.example.tomek.uberallescustomer.api.pojo.FareTimes;
import com.example.tomek.uberallescustomer.api.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {

    @GET("/users")
    Call<User> basicLogin();
    @POST("/users")
    Call<User> createAccount(@Body CreateAccount account);
    @POST("/fares")
    Call<FareProof> createFare(@Body Fare fare);
    @DELETE("/fares/{fareId}")
    Call<Fare> deleteFare(@Path("fareId") String fareId);
    @GET("/arrivalTimes?")
    Call<FareTimes> arrivalTime(@Query("lat") String lat, @Query("lon") String lon);
}