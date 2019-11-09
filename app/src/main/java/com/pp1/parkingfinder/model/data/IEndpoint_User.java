package com.pp1.parkingfinder.model.data;

import com.pp1.parkingfinder.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IEndpoint_User {
    @POST("/signup")
    Call<ResponseBody> createUser(@Body User user);

    @FormUrlEncoded
    @POST("/signup")
    Call<ResponseBody> createUser(
        @Field("email") String username,
        @Field("password") String password,
        @Field("firstname") String firstname,
        @Field("lastname") String lastname
    );
}
