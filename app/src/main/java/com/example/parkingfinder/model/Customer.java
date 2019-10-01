package com.example.parkingfinder.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import com.google.gson.annotations.SerializedName;
import androidx.annotation.Keep;

@Keep
public class Customer extends User {

    private String email, firstname, lastname, rego;

    public Customer() {

    }

    public Customer
            (String email, String firstname, String lastname, String rego){
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rego = rego;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getrego() {
        return rego;
    }

    public String toString() {
        return "email: " + email + ", firstname: " + firstname + ", lastname: " + lastname +
                ", rego: " + rego;
    }
}
