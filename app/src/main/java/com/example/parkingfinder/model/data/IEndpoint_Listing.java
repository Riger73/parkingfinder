package com.example.parkingfinder.model.data;

import com.example.parkingfinder.model.Listing;

import org.w3c.dom.Comment;

import java.util.List;
import java.util.Map;

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
