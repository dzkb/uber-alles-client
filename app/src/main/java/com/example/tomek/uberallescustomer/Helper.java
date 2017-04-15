package com.example.tomek.uberallescustomer;


import java.util.Calendar;

public class Helper {

    public static boolean compareTime(Calendar cal1, Calendar cal2) {
        int hoursFromCal1 = cal1.get(Calendar.HOUR_OF_DAY);
        int minutesFromCal1 = cal1.get(Calendar.MINUTE);

        int hoursFromCal2 = cal2.get(Calendar.HOUR_OF_DAY);
        int minutesFromCal2 = cal2.get(Calendar.MINUTE);

        if (hoursFromCal1 < hoursFromCal2) return false;
        else
            return hoursFromCal1 > hoursFromCal2 || minutesFromCal1 >= minutesFromCal2;
    }
}