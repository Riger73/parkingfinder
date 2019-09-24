package com.example.parkingfinder.model;

public class Listing {
    private String owner;
    private String parkingID;
    private int phoneNo;
    private String description;
    private String latitude;
    private String longitude;

    //constructor
    public Listing(String owner, String parkingID, int phoneNo, String description,
                   String latitude, String longitude)
    {
        this.owner = owner;
        this.parkingID = parkingID;
        this.phoneNo = phoneNo;
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

    public String getParkingID() {
        return parkingID;
    }

    public void setParkingID(String parkingID) {
        this.parkingID = parkingID;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
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
