package com.example.parkingfinder.model.data;

import android.widget.DatePicker;

import com.example.parkingfinder.model.Booking;

import org.w3c.dom.Comment;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IEndpoint_Booking {

    @POST("/bookings")
    Call<ResponseBody> createBooking(@Body Booking Booking);

    @FormUrlEncoded
    @POST("/bookings")
    Call<ResponseBody> createListing(
            @Field("customer") String customer,
            @Field("email") Integer email,
            @Field("bookingdate") DatePicker bookingdate,
            @Field("address") String address,
            @Field("registration") String registration
    );


}