package com.example.serenityhealth.models;

public class PatientModel {
    String firstName;
    String lastName;
    double weight;
    double height;
    String familyHistory;

    public PatientModel(String firstName, String lastName, double weight, double height, String familyHistory) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.familyHistory = familyHistory;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }
}
