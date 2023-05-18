package com.example.personalitytestapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceUser {

    @FormUrlEncoded
    @POST("/api/api_user")
    Call<ModalResponseUser> getUserInformation(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("role") String role, @Field("google_id") String google_id);
}
