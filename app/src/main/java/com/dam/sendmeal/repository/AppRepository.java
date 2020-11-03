package com.dam.sendmeal.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;

import java.util.List;

public class AppRepository implements OnResultCallback {
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
    public void onResult(Object object) {
        callback.onResult(object);
    }

    public class SearchPlateById extends AsyncTask<String, Void, Plate> {

        private PlateDAO plateDAO;
        private OnResultCallback callback;

        public SearchPlateById(PlateDAO plateDAO, OnResultCallback context) {
            this.plateDAO = plateDAO;
            this.callback = context;
        }

        @Override
        protected Plate doInBackground(String... id) {
            Plate plate = plateDAO.search(id[0]);
            return plate;
        }

        @Override
        protected void onPostExecute(Plate plate) {
            super.onPostExecute(plate);
            callback.onResult(plate);
        }
    }

    public class SearchPlates extends AsyncTask<String, Void, List<Plate>> {

        private PlateDAO plateDAO;
        private OnResultCallback callback;

        public SearchPlates(PlateDAO plateDAO, OnResultCallback context) {
            this.plateDAO = plateDAO;
            this.callback = context;
        }

        @Override
        protected List<Plate> doInBackground(String... strings) {
            List<Plate> plates = plateDAO.searchAll();
            return plates;
        }

        @Override
        protected void onPostExecute(List<Plate> plates) {
            super.onPostExecute(plates);
            callback.onResult(plates);
        }
    }
}
