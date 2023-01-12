package com.example.serenityhealth.models;

import com.example.serenityhealth.helpers.TimeSlot;

import java.io.Serializable;
import java.util.Date;

public class ConsultationModel implements Serializable {
    String id;
    Date date;
    TimeSlot time;
    UserModel user;

    public ConsultationModel(String id, Date date, TimeSlot time, UserModel user ) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.user = user;
    }

    public String getId() {
        return id;
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
