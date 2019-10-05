package com.pp1.parkingfinder.model.data;

import com.pp1.parkingfinder.model.Customer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
