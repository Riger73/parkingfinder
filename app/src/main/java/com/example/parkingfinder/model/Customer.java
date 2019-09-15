package com.example.parkingfinder.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import com.google.gson.annotations.SerializedName;
import androidx.annotation.Keep;

@Keep
public class Customer {
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public Customer() {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String toString() {
        return "username: " + username + ", password: " + password + ", firstname: "
                + firstname + ", lastname: " + lastname;
    }
}
