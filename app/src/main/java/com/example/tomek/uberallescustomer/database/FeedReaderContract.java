package com.example.tomek.uberallescustomer.database;

public class FeedReaderContract {

    private FeedReaderContract() {}

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE fares(" +
                    "userPhone integer, " +
                    "fareId TEXT Primary Key, " +
                    "clientName TEXT, " +
                    "startingDate TEXT, " +
                    "startingPoint TEXT, " +
                    "endingPoint TEXT," +
                    "status TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS  fares";
}
