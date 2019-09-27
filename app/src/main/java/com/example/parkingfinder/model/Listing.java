package com.example.parkingfinder.model;

import androidx.annotation.IntegerRes;
import androidx.annotation.Keep;

@Keep
public class Listing {
    private String owner;
    private Integer contact;
    private String parkingspotID;
    private String description;
    private String latitude;
    private String longitude;

    public Listing ()
    {

    }
    //constructor
    public Listing(String owner, Integer contact, String parkingspotID, String description,
                   String latitude, String longitude)
    {
        this.owner = owner;
        this.contact = contact;
        this.parkingspotID = parkingspotID;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getContact() { return contact; }

    public void setContact(String Contact) {
        this.contact = contact;
    }

    public String getParkingSpotID() { return parkingspotID; }

    public void setParkingSpotID(String parkingspotID) {
        this.parkingspotID = parkingspotID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
