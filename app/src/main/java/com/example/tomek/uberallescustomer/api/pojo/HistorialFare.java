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

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public String getClientName() {
        return clientName;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "HistorialFare{" +
                "fareId='" + fareId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", startingDate='" + startingDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
