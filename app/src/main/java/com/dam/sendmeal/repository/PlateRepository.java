package com.dam.sendmeal.repository;

import android.app.Application;
import android.util.Log;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.retrofit.APIRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.dam.sendmeal.dao.PlateOrderRelationDAO;

public class PlateRepository implements OnPlateResultCallback {
    private PlateDAO plateDAO;
    //private PlateOrderRelationDAO plateOrderRelationDAO;
    private OnResultCallback callback;

    public PlateRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        plateDAO = db.plateDao();
        //plateOrderRelationDAO = db.plateOrderRelationDAO();
        callback = context;
    }

    @Override
    public void onResultPlate(List<Plate> plates) {
//        RETROFIT
//        if(plates == null || plates.isEmpty()){
//            Call<List<Plate>> callPlates = APIRest.getInstance().getPlateService().getPlateList();
//
//            callPlates.enqueue(
//                    new Callback<List<Plate>>() {
//                        @Override
//                        public void onResponse(Call<List<Plate>> call, Response<List<Plate>> response) {
//                            Log.d("DEBUG", "Retorno Exitoso");
//                            System.out.println(response);
//                            if (response.code() == 200) {
//                                callback.onResultPlate(response.body());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Plate>> call, Throwable t) {
//                            System.out.println(t);
//                        }
//                    }
//            );
//        }
       callback.onResultPlate(plates);
    }

    public void insert(final Plate plate){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.insert(plate);
                callback.onInsert();
            }
        });

        // Retrofit
        //new PlateCallback().createPlate(plate);
    }

    public void delete(final Plate plate){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.delete(plate);
            }
        });
    }

    public void update(final Plate plate){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.update(plate);
            }
        });
    }

    /*public void getOrdersOfPlate(final long idPlate){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateOrderRelationDAO.getOrdersOfPlate(idPlate);
            }
        });
    }*/

    public void search(String id) {
        new SearchPlateById(plateDAO, this).execute(id);
    }

    public void searchAll() {
        new SearchPlates(plateDAO, this).execute();
    }

    public interface OnResultCallback {
        void onResultPlate(List<Plate> result);
        void onInsert();
    }
}
