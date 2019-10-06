package com.pp1.parkingfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

public class Leaser implements Parcelable {

    private String email;
    private String username;
    private String leaser_id;
    private String firstname;
    private String lastname;
    private GeoPoint carpark = null;

    public Leaser(String email, String leaser_id, String username, String firstname, String lastname,
                  GeoPoint carpark) {
        this.email = email;
        this.leaser_id = leaser_id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.carpark = carpark;
    }

    public Leaser() {

    }

    protected Leaser(Parcel in) {
        email = in.readString();
        leaser_id = in.readString();
        username = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        carpark = in.readParcelable(LatLng.class.getClassLoader());;
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
    public GeoPoint getCarpark() {
        return carpark;
    }

    public void setCarpark(GeoPoint carpark) {
        this.carpark = carpark;
    }


    public static Parcelable.Creator<Leaser> getCREATOR() {
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
                ", user_id='" + leaser_id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + firstname + '\'' +
                ", avatar='" + lastname + '\'' +
                ", avatar='" + carpark + '\'' +
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