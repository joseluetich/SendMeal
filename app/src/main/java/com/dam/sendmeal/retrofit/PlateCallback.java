package com.dam.sendmeal.retrofit;

import android.util.Log;

import com.dam.sendmeal.model.Plate;

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
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plate>> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                    }
                }
        );

    }
}
