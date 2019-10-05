package com.pp1.parkingfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

@Keep
public class User {

    private String email, firstname, lastname, rego, userId;

    public User() {

    }

    public User
            (String email, String firstname, String lastname, String rego){
        this.userId = userId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rego = rego;
    }


    protected User(Parcel in) {

        userId = in.readString();
        email = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        rego = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserId() {
        return userId;
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

    public String getRego() {
        return rego;
    }

    public String toString() {
        return "email: " + email + ", firstname: " + firstname + ", lastname: " + lastname +
                ", rego: " + rego;
    }
}
