package com.sickle.healthcareapp.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public static String getCurrentDate(){

        // Get current date
        Date currentDate = new Date();

        // Define date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        // Format date to string
        String formattedDate = dateFormat.format(currentDate);


        return formattedDate;
    }


    public static String getCurrentTime(){
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Format time to string
        String formattedTime = formatTime(hourOfDay, minute);

        return formattedTime;
    }

    private static String formatTime(int hourOfDay, int minute) {
        String timeFormatPattern = "h:mm a";
        SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatPattern, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return timeFormat.format(calendar.getTime());
    }
}
