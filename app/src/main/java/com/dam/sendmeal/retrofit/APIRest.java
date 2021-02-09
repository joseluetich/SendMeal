package com.dam.sendmeal.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRest {

    private static APIRest INSTANCE;
    private static final String urlApiRest = "http://10.0.2.2:3001/";
    private static PlateService plateService;
    Retrofit retrofit;

    private APIRest () {
        Gson gson = new GsonBuilder().setLenient().create();
        // Add logging into retrofit 2.0
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(logging);
        retrofit = new Retrofit.Builder()
                .baseUrl(urlApiRest)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build()).build();
    }

    public PlateService getPlateService(){
        return retrofit.create(PlateService.class);
    }

    public OrderService getOrderService(){
        return retrofit.create(OrderService.class);
    }

    public static APIRest getInstance(){
        if(INSTANCE == null){
            INSTANCE = new APIRest();
        }
        return INSTANCE;
    }

}