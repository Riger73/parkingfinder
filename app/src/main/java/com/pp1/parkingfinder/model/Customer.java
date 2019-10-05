package com.pp1.parkingfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

@Keep
public class Customer extends User {

    private String email, firstname, lastname, rego, custId;

    public Customer() {

    }

    public Customer
            (String email, String firstname, String lastname, String rego, String custId){
        this.custId = custId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rego = rego;
    }


    protected Customer(Parcel in) {

        custId = in.readString();
        email = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        rego = in.readString();
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
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

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getCustId() {
        return custId;
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
