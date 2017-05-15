package com.example.tomek.uberallescustomer.api.pojo;


public class HistorialFare {

    private String fareId;
    private String startingPoint;
    private String endingPoint;
    private String clientName;
    private String startingDate;
    private String status;

    public HistorialFare(String fareId, String startingPoint, String endingPoint, String clientName, String startingDate, String status) {
        this.fareId = fareId;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.clientName = clientName;
        this.startingDate = startingDate;
        this.status = status;
    }
}
