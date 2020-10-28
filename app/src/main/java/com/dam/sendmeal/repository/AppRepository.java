package com.dam.sendmeal.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppRepository {
    private PlateDAO plateDAO;
    private OnResultCallback callback;

    public AppRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        plateDAO = db.platoDao();
        callback = context;
    }

    public void insert(final Plate plate){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.insert(plate);
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

    @Override
    public void onResult(List<Plate> plates) {
        Log.d("DEBUG", "Plate found");
        callback.onResult(plates);
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
    }
}
