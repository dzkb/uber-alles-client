package com.example.tomek.uberallescustomer.api.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("phoneNumber")
    public Integer phoneNumber;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;

}