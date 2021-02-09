package com.dam.sendmeal.converter;

import androidx.room.TypeConverter;

import com.dam.sendmeal.model.Plate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Plate> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Plate> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}