package com.example.personalitytestapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUser {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://127.0.0.1:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
            return retrofit;
    }
}
