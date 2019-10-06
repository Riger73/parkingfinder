package com.pp1.parkingfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

@Keep
public class Listing implements Parcelable {

    private String email;
    private String username;
    private String leaser_id;
    private String firstname;
    private String lastname;
    private String address;
    private GeoPoint carpark = null;

    public Listing(String email, String leaser_id, String username, String firstname, String lastname,
                   String address, GeoPoint carpark) {
        this.email = email;
        this.leaser_id = leaser_id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.carpark = carpark;
    }

    public Listing() {

    }

    protected Listing(Parcel in) {
        email = in.readString();
        leaser_id = in.readString();
        username = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        carpark = in.readParcelable(LatLng.class.getClassLoader());;
    }

    public static final Parcelable.Creator<Listing> CREATOR = new Parcelable.Creator<Listing>() {
        @Override
        public Listing createFromParcel(Parcel in) {
            return new Listing(in);
        }

        @Override
        public Listing[] newArray(int size) {
            return new Listing[size];
        }
    };

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public GeoPoint getCarpark() {
        return carpark;
    }

    public void setCarpark(GeoPoint carpark) {
        this.carpark = carpark;
    }


    public static Parcelable.Creator<Listing> getCREATOR() {
        return CREATOR;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLeaser_id() {
        return leaser_id;
    }

    public void setLeaser_id(String leaser_id) {
        this.leaser_id = leaser_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Leasers{" +
                "email='" + email + '\'' +
                ", leaser_id='" + leaser_id + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", carpark='" + carpark + '\'' +
                '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(leaser_id);
        dest.writeString(username);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeDouble(carpark.getLatitude());
        dest.writeDouble(carpark.getLongitude());
    }
}