package com.dam.sendmeal.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.dam.sendmeal.dao.OrderDAO;
import com.dam.sendmeal.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements OnOrderResultCallback {
    private OrderDAO orderDAO;
    private OnResultCallback callback;

    public OrderRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        orderDAO = db.orderDao();
        callback = context;
    }

    @Override
    public void onResult(List<Order> orders) {
        Log.d("DEBUG", "Plato found");
        callback.onResult(orders);
    }

    public void insert(final Order order){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderDAO.insert(order);
                callback.onInsert();
            }
        });
    }

    public void delete(final Order order){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderDAO.delete(order);
            }
        });
    }

    public void update(final Order order){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderDAO.update(order);
            }
        });
    }

    public void search(String id) {
        new SearchOrderById(orderDAO, this).execute(id);
    }

    public void searchAll() {
        new SearchOrders(orderDAO, this).execute();
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
        void onInsert();
    }
}