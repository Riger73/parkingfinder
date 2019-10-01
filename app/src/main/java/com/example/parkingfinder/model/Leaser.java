package com.example.parkingfinder.model;

public class Leaser extends User {
    private String email, firstname, lastname, parkAddress;

    public Leaser() {

    }

    public Leaser
            (String email, String firstname, String lastname, String parkAddress) {

        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.parkAddress = parkAddress;
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

    public String setParkAddree() {
        return parkAddress = parkAddress;
    }


    public String getUsername() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getParkAddree() {
        return parkAddress;
    }

    public String toString() {
        return "email: " + email + ", firstname: " + firstname + ", lastname: " + lastname +
                ", parkAddress: " + parkAddress;
    }
}
