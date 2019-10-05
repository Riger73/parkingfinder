package com.pp1.parkingfinder.model.data;

import com.pp1.parkingfinder.model.Listing;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IEndpoint_Listing {
    @POST("/listings")
    Call<ResponseBody> createListing(@Body Listing listing);

    @FormUrlEncoded
    @POST("/listings")
    Call<ResponseBody> createListing(
            @Field("owner") String owner,
            @Field("contact") Integer contact,
            @Field("parkingspotID") String parkingspotID,
            @Field("description") String description,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );
}
