package com.example.tomek.uberallescustomer.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tomek.uberallescustomer.api.pojo.Fare;
import com.example.tomek.uberallescustomer.api.pojo.HistorialFare;

import java.util.HashMap;

import static com.example.tomek.uberallescustomer.LogedUserData.newFareEndingPoint;
import static com.example.tomek.uberallescustomer.LogedUserData.newFareStartingPoint;
import static com.example.tomek.uberallescustomer.database.FeedReaderContract.SQL_CREATE_ENTRIES;
import static com.example.tomek.uberallescustomer.database.FeedReaderContract.SQL_DELETE_ENTRIES;


public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insert(SQLiteDatabase db, Fare fare, String status, String key) {
        db.execSQL("insert into fares values(" +
                fare.getClientPhone() + ", '" +
                key + "', '" +
                fare.getClientName() + "', '" +
                fare.getStartingDate() + "', '" +
                newFareStartingPoint + "', '" +
                newFareEndingPoint + "', '" +
                status + "')");
    }

    public void insertExample(SQLiteDatabase db) {
        db.execSQL("insert into fares values(500, 'rwgv3465773tf', 'Szymon', '2047.51.18', 'Wro', 'Waw', 'new')");
    }

    public HashMap<String, HistorialFare> selectById(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from fares where userPhone = " + user + ";", null);
        HashMap<String, HistorialFare> map = new HashMap<>();
        if (c.moveToFirst()) {
            do {
                String fareId = c.getString(1);
                String clientName = c.getString(2);
                String startingDate = c.getString(3);
                String startingPoint = c.getString(4);
                String endingPoint = c.getString(5);
                String status = c.getString(6);
                HistorialFare fare = new HistorialFare(fareId, startingPoint, endingPoint, clientName, startingDate, status);
                map.put(fareId, fare);
            } while (c.moveToNext());
            c.close();
        }
        return map;
    }

    public HashMap<String, HistorialFare> selectByIdAndKey(String key, String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from fares where fareId = '" + key + "' and userPhone = " + user + ";", null);
        HashMap<String, HistorialFare> map = new HashMap<>();
        if (c.moveToFirst()) {
            do {
                String fareId = c.getString(1);
                String clientName = c.getString(2);
                String startingDate = c.getString(3);
                String startingPoint = c.getString(4);
                String endingPoint = c.getString(5);
                String status = c.getString(6);
                HistorialFare fare = new HistorialFare(fareId, startingPoint, endingPoint, clientName, startingDate, status);
                map.put(fareId, fare);
            } while (c.moveToNext());
            c.close();
        }
        return map;
    }

    public HashMap<String, HistorialFare> selectByStatus(String status, String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from fares where status = '" + status + "' and userPhone = " + user + ";", null);
        HashMap<String, HistorialFare> map = new HashMap<>();
        if (c.moveToFirst()) {
            do {
                String fareId = c.getString(1);
                String clientName = c.getString(2);
                String startingDate = c.getString(3);
                String startingPoint = c.getString(4);
                String endingPoint = c.getString(5);
                HistorialFare fare = new HistorialFare(fareId, startingPoint, endingPoint, clientName, startingDate, status);
                map.put(fareId, fare);
            } while (c.moveToNext());
            c.close();
        }
        return map;
    }

    public void deleteById(String user, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Delete from fares where fareId = '" + id + "' and userPhone = " + user + ";", null);
        c.moveToFirst();
        c.close();
    }

    public void updateById(String user, String id, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("UPDATE fares SET status = " + newStatus + " WHERE fareId = '" + id + "' and userPhone = " + user + ";", null);
        c.moveToFirst();
        c.close();
    }
}