package com.example.serenityhealth.helpers;

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
            if(TimeSlot.values()[i].value == value){
               theSlot= TimeSlot.values()[i];
                break;
            }
        }
        return theSlot;
    }
}
