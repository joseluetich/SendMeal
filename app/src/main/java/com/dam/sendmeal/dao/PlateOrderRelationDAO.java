package com.dam.sendmeal.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dam.sendmeal.model.Order;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.model.PlateOrderRelation;

import java.util.List;

public interface PlateOrderRelationDAO {

    @Insert
    void insert(PlateOrderRelation plateOrderRelation);

    @Transaction
    @Query ("SELECT * FROM [order] "+
            "INNER JOIN plateOrderRelation "+
            "ON [order].idOrder=plateOrderRelation.idOrder "+
            "WHERE plateOrderRelation.idPlate=:idPlate")

    List <Order> getOrdersOfPlate(final long idPlate);

    @Transaction
    @Query ("SELECT * FROM plate "+
            "INNER JOIN plateOrderRelation "+
            "ON plate.idPlate=plateOrderRelation.idPlate "+
            "WHERE plateOrderRelation.idOrder=:idOrder")

    List <Order> getPlatesOfOrder(final long idOrder);
}
