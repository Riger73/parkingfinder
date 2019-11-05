
package com.pp1.parkingfinder.model;

public class Listing {

    private String availability;
    private String address;

    public Listing()
    {
        this.address = address;
        this.availability = availability;
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
        this.availability = availability;
    }

}
