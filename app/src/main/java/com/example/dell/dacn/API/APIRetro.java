package com.example.dell.dacn.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 11/10/17.
 */

public class APIRetro {
    public static final String BASE_URL = "http://tinxoixa.com/tonghop/api/";
    public static Interface apiInterface;
    public static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            apiInterface = retrofit.create(Interface.class);

        }
        return retrofit;
    }
}