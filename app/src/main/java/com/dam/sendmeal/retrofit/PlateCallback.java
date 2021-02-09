package com.dam.sendmeal.retrofit;

import android.util.Log;

import com.dam.sendmeal.model.Plate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlateCallback {

    PlateService plateService = APIRest.getInstance().getPlateService();

    public void getPlates(){

        Call<List<Plate>> callPlates = plateService.getPlateList();

        callPlates.enqueue(
                new Callback<List<Plate>>() {
                    @Override
                    public void onResponse(Call<List<Plate>> call, Response<List<Plate>> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Retorno Exitoso");
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plate>> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                    }
                }
        );

    }

    public void getPlateById(String id){

        Call<Plate> callPlate = plateService.getPlate(id);

        callPlate.enqueue(
                new Callback<Plate>() {
                    @Override
                    public void onResponse(Call<Plate> call, Response<Plate> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Retorno Exitoso");
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Plate> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                    }
                }
        );

    }

    public void createPlate(Plate plate){

        Call<Plate> callPlate = plateService.createPlate(plate);

        callPlate.enqueue(
                new Callback<Plate>() {
                    @Override
                    public void onResponse(Call<Plate> call, Response<Plate> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Retorno Exitoso");
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Plate> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                    }
                }
        );

    }

}
