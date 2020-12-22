package com.dam.sendmeal.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRest {

    private static APIRest INSTANCE;
    private static final String urlApiRest = "http://10.0.2.2/";
    private static PlateService plateService;
    Retrofit retrofit;

    private APIRest () {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("{urlApiRest}/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public PlateService getPlateService(){
        return retrofit.create(PlateService.class);
    }

    public static APIRest getInstance(){
        if(INSTANCE == null){
            INSTANCE = new APIRest();
        }
        return INSTANCE;
    }

}