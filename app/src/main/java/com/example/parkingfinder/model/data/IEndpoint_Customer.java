package com.example.parkingfinder.model.data;

import com.example.parkingfinder.model.Customer;

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

public interface IEndpoint_Customer {
    @POST("/signup")
    Call<ResponseBody> createCustomer(@Body Customer customer);

    @FormUrlEncoded
    @POST("/signup")
    Call<ResponseBody> createCustomer(
        @Field("email") String username,
        @Field("password") String password,
        @Field("firstname") String firstname,
        @Field("lastname") String lastname
    );
}
