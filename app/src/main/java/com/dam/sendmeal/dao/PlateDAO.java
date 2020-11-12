package com.dam.sendmeal.dao;

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
    void insert(Plate plate);

    @Delete
    void delete(Plate plate);

    @Update
    void update(Plate plate);

    @Query("SELECT * FROM plate WHERE idPlate = :id LIMIT 1")
    Plate search(String id);

    @Query("SELECT * FROM plate")
    List<Plate> searchAll();

    @Query("DELETE FROM plate")
    void deletePlates();
}
