package com.dam.sendmeal.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;

import java.util.List;


public class PlateRepository  {

    private static PlateRepository INSTANCE;

    private PlateDAO plateDAO;
    private LiveData<List<Plate>> allPlates;


    public PlateRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        plateDAO = db.plateDAO();
        allPlates = plateDAO.getAllPlates();
    }

    public LiveData<List<Plate>> getAllPlates() {
        return allPlates;
    }

    public void add (final Plate plate) {
        AppDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.add(plate);
            }
        });
    }

    public void update(final Plate plate){
        AppDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.update(plate);
            }
        });
    }

    public void delete(final Plate plate){
        AppDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateDAO.delete(plate);
            }
        });
    }

    public void searchById (String id) {
        new searchByIdAsyncTask(plateDAO).execute(id);
    }

    private static class searchByIdAsyncTask extends AsyncTask<String, Void, Plate> {

        private PlateDAO mAsyncTaskDao;
        private OnResultCallback callback;

        searchByIdAsyncTask(PlateDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Plate doInBackground(final String... params) {
            Plate plate = mAsyncTaskDao.searchById(params[0]);
            return plate;
        }

        @Override
        protected void onPostExecute(Plate plate) {
            super.onPostExecute(plate);
            callback.onResult(plate);
        }
    }

    public interface OnResultCallback<T> {
        void onResult(T result);
    }

}
