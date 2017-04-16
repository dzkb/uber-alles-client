package com.example.tomek.uberallescustomer.api.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Krystian on 2017-04-16.
 */

public class CreateAccount {

    @SerializedName("phoneNumber")
    public Integer phoneNumber;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("password")
    public String password;

    public CreateAccount(int phoneNumber, String firstName, String lastName, String password) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}