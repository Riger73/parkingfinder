package com.pp1.parkingfinder.model.data;

import android.widget.DatePicker;


import com.pp1.parkingfinder.model.Booking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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