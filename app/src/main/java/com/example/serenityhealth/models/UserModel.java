package com.example.serenityhealth.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    String firstName;
    String lastName;
    double weight;
    double height;
    String userName;
    String password;
    String imageUri;
    double id;


    public UserModel(String firstName, String lastName, double weight, double height, String userName, String password,String imageUri, double id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.userName = userName;
        this.password = password;
        this.imageUri = imageUri;
        this.id = id;
    }

    public String getFullName() {
        return firstName+ " " + lastName;
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

    public String getWeightKg() {
        return weight + " kg";
    }

    public String getHeightCm() {
        return height + " cm";
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUri() {
        return imageUri;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }
}
