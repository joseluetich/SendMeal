package com.dam.sendmeal.repository;

import android.os.AsyncTask;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;

import java.util.List;

public class SearchPlates extends AsyncTask<String, Void, List<Plate>> {

    private PlateDAO plateDAO;
    private OnPlateResultCallback callback;

    public SearchPlates(PlateDAO plateDAO, OnPlateResultCallback context) {
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
        callback.onResultPlate(plates);
    }
}