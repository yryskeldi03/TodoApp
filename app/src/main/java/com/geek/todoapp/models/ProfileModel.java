package com.geek.todoapp.models;

public class ProfileModel {
    private String userName, email, number, date, address;

    public ProfileModel() {
    }

    public ProfileModel(String userName, String email, String number, String date, String address) {
        this.userName = userName;
        this.email = email;
        this.number = number;
        this.date = date;
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }
}
