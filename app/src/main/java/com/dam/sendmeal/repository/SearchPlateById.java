package com.dam.sendmeal.repository;

import android.os.AsyncTask;

import com.dam.sendmeal.dao.PlateDAO;
import com.dam.sendmeal.model.Plate;

import java.util.ArrayList;
import java.util.List;

public class SearchPlateById extends AsyncTask<String, Void, Plate> {

    private PlateDAO plateDAO;
    private OnPlateResultCallback callback;

    public SearchPlateById(PlateDAO plateDAO, OnPlateResultCallback context) {
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
        List<Plate> plates = new ArrayList<>();
        plates.add(plate);
        callback.onResult(plates);
    }
}