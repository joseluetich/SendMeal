package com.dam.sendmeal.repository;

import android.os.AsyncTask;

import com.dam.sendmeal.dao.OrderDAO;
import com.dam.sendmeal.model.Order;

import java.util.ArrayList;
import java.util.List;

public class SearchOrderById extends AsyncTask<String, Void, Order> {

    private OrderDAO orderDAO;
    private OnOrderResultCallback callback;

    public SearchOrderById(OrderDAO orderDAO, OnOrderResultCallback context) {
        this.orderDAO = orderDAO;
        this.callback = context;
    }

    @Override
    protected Order doInBackground(String... id) {
        Order order = orderDAO.search(id[0]);
        return order;
    }

    @Override
    protected void onPostExecute(Order order) {
        super.onPostExecute(order);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        callback.onResult(orders);
    }
}
