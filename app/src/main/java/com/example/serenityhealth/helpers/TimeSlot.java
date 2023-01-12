package com.example.serenityhealth.helpers;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public enum TimeSlot {
    am9_10("9am - 10am"),
    am10_11("10am - 11am"),
    am11_12("11am - 12pm"),
    pm1_2("1pm - 2pm"),
    pm2_3("2pm - 3pm"),
    pm3_4("3pm - 4pm"),
    pm4_5("4pm - 5pm");

    public final String value;

    TimeSlot(String value) {
        this.value = value;
    }

    public static TimeSlot toTimeSlot(String value){
        TimeSlot theSlot = TimeSlot.am9_10;

        for (int i = 0; i < TimeSlot.values().length; i++) {
            if(TimeSlot.values()[i].value.equalsIgnoreCase(value)){
               theSlot= TimeSlot.values()[i];
                break;
            }
        }
        return theSlot;
    }

    public static TimeSlot getTimeSlotNow(){
        Calendar timeNow =  Calendar.getInstance();
        Calendar timeNextHour =  Calendar.getInstance();
        timeNextHour.add(Calendar.HOUR_OF_DAY, 1);
        String theTimeSlotString =new SimpleDateFormat("ha").format(timeNow.getTime())+" - "+new SimpleDateFormat("ha").format(timeNextHour.getTime());
        Log.e("SA TIME", theTimeSlotString );
        return TimeSlot.toTimeSlot(theTimeSlotString);
    }
}
