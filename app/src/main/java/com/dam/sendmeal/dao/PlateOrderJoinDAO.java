package com.dam.sendmeal.dao;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.dam.sendmeal.model.Order;
import com.dam.sendmeal.model.Plate;

import java.util.List;

public interface PlateOrderJoinDAO {
    /*@Insert
    void insert(PlateOrderJoin plateOrderJoin);

    @Query("SELECT * FROM plate "+
            "INNER JOIN plateOrderJoin "+
            "ON plate.idPlate=plateOrderJoin.idPlate "+
            "WHERE plateOrderJoin.idOrder=:idOrder")
    List<Plate> getPlatesOfOrder(final long idOrder);

    @Query("SELECT * FROM [order] "+
            "INNER JOIN plateOrderJoin "+
            "ON [order].idOrder=plateOrderJoin.idOrder "+
            "WHERE plateOrderJoin.idPlate=:idPlate")
    List<Order> getOrdersWithPlate(final long idPlate);

    @Query("INSERT INTO [order] "+
            "VALUES (plates)")
    void setOrdersWithPlate(final String plates);*/

    /*@Transaction
    @Query("SELECT plates FROM [order] "+
            "WHERE [order].idOrder=:idOrder")
    List<Plate> getPlatesOfOrder(final long idOrder);

    @Transaction
    @Query("INSERT INTO [order] "+
            "VALUES (orderPlates)")
    void setPlatesOfOrder(final List<Plate> orderPlates);*/

}
