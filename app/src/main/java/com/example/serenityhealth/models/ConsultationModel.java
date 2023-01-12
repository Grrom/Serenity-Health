package com.example.serenityhealth.models;

import com.example.serenityhealth.helpers.TimeSlot;

import java.io.Serializable;
import java.util.Date;

public class ConsultationModel implements Serializable {
    Date date;
    TimeSlot time;
    UserModel user;

    public ConsultationModel( Date date, TimeSlot time, UserModel user ) {
        this.date = date;
        this.time = time;
        this.user = user;
    }


    public Date getDate() {
        return date;
    }

    public TimeSlot getTime() {
        return time;
    }

    public UserModel getUser() {
        return user;
    }
}
