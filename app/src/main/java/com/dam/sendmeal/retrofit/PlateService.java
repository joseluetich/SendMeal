package com.dam.sendmeal.retrofit;

import com.dam.sendmeal.model.Plate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlateService {
    @GET("plate/{id}")
    Call<Plate> getPlate(@Path("id") String id);

    @GET("plate/list")
    Call<List<Plate>> getPlateList();

    @POST("plate")
    Call<Plate> createPlate(@Body Plate plato);

}
