package com.dam.sendmeal.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;

import java.util.ArrayList;
import java.util.List;

public class PlateRepository implements OnPlateResultCallback {
    private PlateDAO plateDAO;
    private OnResultCallback callback;

    public PlateRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        plateDAO = db.plateDao();
        callback = context;
    }

    @Override
    public void onResult(List<Plate> plates) {
        Log.d("DEBUG", "Plato found");
        callback.onResult(plates);
    }

    public void insert(final Plate plate){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.insert(plate);
                callback.onInsert();
            }
        });
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

    public void search(String id) {
        new SearchPlateById(plateDAO, this).execute(id);
    }

    public void searchAll() {
        new SearchPlates(plateDAO, this).execute();
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
        void onInsert();
    }
}
