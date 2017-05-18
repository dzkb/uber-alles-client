package com.example.tomek.uberallescustomer;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.FareTimes;
import com.example.tomek.uberallescustomer.api.pojo.HistorialFare;
import com.example.tomek.uberallescustomer.api.pojo.Point;
import com.example.tomek.uberallescustomer.database.FeedReaderDbHelper;

import java.util.HashMap;

import static com.example.tomek.uberallescustomer.CustomerActivity.giveMeContext;
import static com.example.tomek.uberallescustomer.LoginActivity.giveMeLoginContext;

public class LogedUserData {

    public static String USER_NAME;
    public static String USER_SURNAME;
    public static String USER_PHONE;
    public static String USER_PASSWORD;

    public static String ACTIVE_FARE_ID;
    public static FareTimes times;

    public static HashMap<String, Fare> FARES_LIST = null;

    public static void addFare(String key, Fare fare) {

        FeedReaderDbHelper helper = new FeedReaderDbHelper(giveMeLoginContext());
        if (FARES_LIST == null) {
            FARES_LIST = new HashMap<>();
            //oncreate nalezy wykonać przy instalacji - tylko nie wiem jak
            //helper.onCreate(helper.getWritableDatabase());
        }
        helper.insert(helper.getWritableDatabase(), fare, "new", key);
        HashMap<String, HistorialFare> fares = helper.selectById("500");
        int e = 0;        //FARES_LIST.put(key, fare);
    }

    public static String newFareStartingPoint;
    public static String newFareEndingPoint;

    public static HashMap<String, Point> currentDriverLocation = null; // key -> driver Phone Number, value -> current loc

    public static void upadateLocation(String key, String lat, String lon) {
        Point newDriverPoint = new Point(lat, lon);
        if (currentDriverLocation == null) currentDriverLocation = new HashMap<>();
        currentDriverLocation.put(key, newDriverPoint); //put new or replace exist location
        Log.i("INFO", "Dodano nowa lokalizację " + key + " {" + lat + ", " + lon + "}");
    }

    public static void deleteFareByKey(String key) {
        FARES_LIST.remove(key);
    }

    public static void saveCredentials(String login, String password, String firstName, String lastName, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Authentication_Id", login);
        editor.putString("Authentication_Password", password);
        editor.putString("Authentication_Name", firstName);
        editor.putString("Authentication_Surname", lastName);
        editor.apply();
    }
}
