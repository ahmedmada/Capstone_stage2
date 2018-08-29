package com.e.k.m.a.capstone_stage2.Network.rest;

import com.e.k.m.a.capstone_stage2.Network.BaseUrls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    private static BaseUrls baseUrls = new BaseUrls();

    public ApiClient() {
    }
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrls.getBASE_URL())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
