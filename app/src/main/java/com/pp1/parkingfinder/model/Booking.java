
package com.pp1.parkingfinder.model;

import android.widget.DatePicker;

public class Booking {
    private String customer;
    private DatePicker bookingdate;
    private Integer email;
    private String address;
    private String registration;

    public Booking()
    {
        this.customer = customer;
        this.bookingdate = bookingdate;
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

    public Integer getEmail() { return email; }

    public void setEmail(Integer email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public DatePicker getbookingdate() {
        return bookingdate;
    }

    public void setBookingdate(String bookingdate) {
        this.registration = bookingdate;
    }

}
