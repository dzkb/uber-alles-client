package com.example.tomek.uberallescustomer.api.pojo;

/**
 * Created by Tomek on 20.05.2017.
 */

public class Driver {

    private String driverName;
    private int driverPhone;
    private String carName;
    private String carPlateNumber;

    public Driver(String driverName, int driverPhone, String carName, String carPlateNumber) {
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.carName = carName;
        this.carPlateNumber = carPlateNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(int driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }
}
