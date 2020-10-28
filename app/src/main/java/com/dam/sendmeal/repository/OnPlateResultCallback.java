package com.dam.sendmeal.repository;

import com.dam.sendmeal.model.Plate;

import java.util.List;

public interface OnPlateResultCallback {
    void onResult(List<Plate> plates);
}
