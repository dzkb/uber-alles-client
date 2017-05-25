package com.example.tomek.uberallescustomer.database;

public class FeedReaderContract {

    private FeedReaderContract() {}

    public static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE IF NOT EXISTS fares(" +
                    "userPhone integer, " +
                    "fareId TEXT Primary Key, " +
                    "clientName TEXT, " +
                    "startingDate TEXT, " +
                    "startingPoint TEXT, " +
                    "endingPoint TEXT," +
                    "status TEXT);\n";

    public static final String SQL_CREATE_ENTRIES2 =
            "Create Table IF NOT EXISTS historyClient(" +
                    "fareId TEXT Primary Key, "+
                    "driverName TEXT, " +
                    "driverPhone integer, " +
                    "carModel TEXT, " +
                    "carPlates TEXT)";



    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS  fares;"+
            "DROP TABLE IF EXISTS historyClient";
}
