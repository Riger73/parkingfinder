
package com.pp1.parkingfinder.model;

import android.widget.DatePicker;

public class Booking {
    private String customer;
    private String availability;
    private String email;
    private String address;
    private String registration;

    public Booking()
    {
        this.customer = customer;
        this.availability = availability;
        this.email = email;
        this.address = address;
        this.registration = registration;

    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.registration = availability;
    }

}
