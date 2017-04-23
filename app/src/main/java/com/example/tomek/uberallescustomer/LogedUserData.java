package com.example.tomek.uberallescustomer;


import com.example.tomek.uberallescustomer.api.pojo.Fare;

import java.util.HashMap;

public class LogedUserData {

    public static String USER_NAME;
    public static String USER_SURNAME;
    public static Integer USER_PHONE;
    public static String USER_PASSWORD;

    public static String ACTIVE_FARE_ID;

    public static HashMap<String, Fare> FARES_LIST = null;

    public static void addFare(String key, Fare fare) {
        if (FARES_LIST == null) FARES_LIST = new HashMap<>();
        FARES_LIST.put(key, fare);
    }

    public static void deleteFareByKey(String key) {
        FARES_LIST.remove(key);
    }
}
