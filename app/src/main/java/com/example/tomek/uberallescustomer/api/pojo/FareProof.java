package com.example.tomek.uberallescustomer.api.pojo;


import com.google.gson.annotations.SerializedName;

public class FareProof {

    @SerializedName("id")
    private String id;
    @SerializedName("requestDate")
    private String requestDate;

    public String getId() {
        return id;
    }
}
