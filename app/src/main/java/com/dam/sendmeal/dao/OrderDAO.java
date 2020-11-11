package com.dam.sendmeal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dam.sendmeal.model.Order;

import java.util.List;

@Dao
public interface OrderDAO {
    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);

    @Update
    void update(Order order);

    @Query("SELECT * FROM order WHERE id = :id LIMIT 1")
    Order search(String id);

    @Query("SELECT * FROM order")
    List<Order> searchAll();

    @Query("DELETE FROM `order`")
    void deleteOrders();
}
