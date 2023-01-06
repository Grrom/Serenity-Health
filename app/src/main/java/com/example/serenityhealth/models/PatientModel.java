package com.example.serenityhealth.models;

import java.io.Serializable;

public class PatientModel implements Serializable {
    int image;
    String firstName;
    String lastName;
    double weight;
    double height;
    String familyHistory;
    String section;

    public PatientModel(int image, String firstName, String lastName, double weight, double height, String familyHistory, String section) {
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.familyHistory = familyHistory;
        this.section = section;
    }

    public String getFullName() {
        return firstName + " " + lastName;
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

    public int getImage() {
        return image;
    }

    public String getSection() {
        return section;
    }
}
