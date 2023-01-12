package com.example.serenityhealth.models;

import com.example.serenityhealth.helpers.TimeSlot;

import java.util.Date;

public class HistoryModel extends ConsultationModel{
    String bloodPressureSystolic;
    String bloodPressureDiastolic;
    String temperature;
    String symptoms;
    String diagnosis;

    public HistoryModel(Date date, TimeSlot time, UserModel user, String bloodPressureSystolic, String bloodPressureDiastolic, String temperature, String symptoms, String diagnosis) {
        super(date, time, user);
        this.bloodPressureSystolic = bloodPressureSystolic;
        this.bloodPressureDiastolic = bloodPressureDiastolic;
        this.temperature = temperature;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
    }

    public String getBloodPressureSystolic() {
        return bloodPressureSystolic ;
    }

    public String getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
}
