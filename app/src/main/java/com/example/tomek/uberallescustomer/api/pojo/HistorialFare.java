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

    public String getFareId() {
        return fareId;
    }

    public void setFareId(String fareId) {
        this.fareId = fareId;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
