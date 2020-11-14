package com.dam.sendmeal.repository;

import android.app.Application;
import android.util.Log;

import com.dam.sendmeal.dao.OrderDAO;
import com.dam.sendmeal.dao.PlateOrderRelationDAO;
import com.dam.sendmeal.model.Order;

import java.util.List;

public class OrderRepository implements OnOrderResultCallback {
    private OrderDAO orderDAO;
    private PlateOrderRelationDAO plateOrderRelationDAO;
    private OnResultCallback callback;

    public OrderRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        orderDAO = db.orderDao();
        plateOrderRelationDAO = db.plateOrderRelationDAO();
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

    public void getPlatesOfOrder(final long idOrder){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateOrderRelationDAO.getPlatesOfOrder(idOrder);
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