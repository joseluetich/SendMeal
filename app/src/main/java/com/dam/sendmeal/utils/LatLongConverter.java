package com.dam.sendmeal.utils;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class LatLongConverter {

    @TypeConverter
    public LatLng toLatLong(String locationString) {
        try {
            return new Gson().fromJson(locationString, LatLng.class);
        } catch (Exception e) {
            return null;
        }
    }

    @TypeConverter
    public String toLatLongString(LatLng location) {
        return new Gson().toJson(location);
    }
}

