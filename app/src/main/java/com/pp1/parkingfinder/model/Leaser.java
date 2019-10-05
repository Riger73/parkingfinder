package com.pp1.parkingfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Leaser extends User {
    private String email, firstname, lastname, parkAddress, leaserId;

    public Leaser() {

    }

    public Leaser
            (String email, String firstname, String lastname, String parkAddress, String leaserId) {

        this.leaserId = leaserId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.parkAddress = parkAddress;
    }

    protected Leaser(Parcel in) {

        leaserId = in.readString();
        email = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        parkAddress = in.readString();
    }

    public static final Parcelable.Creator<Leaser> CREATOR = new Parcelable.Creator<Leaser>() {
        @Override
        public Leaser createFromParcel(Parcel in) {
            return new Leaser(in);
        }

        @Override
        public Leaser[] newArray(int size) {
            return new Leaser[size];
        }
    };

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

    public String getLeaserId() {
        return leaserId;
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
