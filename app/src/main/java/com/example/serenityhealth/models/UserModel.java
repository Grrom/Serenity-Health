package com.example.serenityhealth.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    String firstName;
    String lastName;
    double weight;
    double height;
    String userName;
    String password;


    public UserModel(String firstName, String lastName, double weight, double height, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.userName = userName;
        this.password = password;
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

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
