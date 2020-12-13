package com.dam.sendmeal.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dam.sendmeal.model.Plate;

import java.util.List;

@Dao
public interface PlateDAO {

    @Insert
    void add(Plate plate);

    @Delete
    void delete(Plate plate);

    @Update
    void update(Plate plate);

    @Query("DELETE FROM plates")
    void deleteAll();

    @Query("SELECT * FROM plates WHERE id = :id LIMIT 1")
    Plate searchById(String id);

    @Query("SELECT * from plates ")
    LiveData<List<Plate>> getAllPlates();

}
