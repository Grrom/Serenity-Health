package com.example.serenityhealth.models;

import java.sql.Time;
import java.util.Date;

public class ConsultationModel {
    int image;
    int patientId;
    Date date;
    Time time;

    public ConsultationModel(int image, int patientId, Date date, Time time) {
        this.image = image;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public int getPatientId() {
        return patientId;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
