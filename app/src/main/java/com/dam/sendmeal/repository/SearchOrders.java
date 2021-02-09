package com.dam.sendmeal.repository;

import android.os.AsyncTask;

import com.dam.sendmeal.dao.OrderDAO;
import com.dam.sendmeal.model.Order;

import java.util.List;

public class SearchOrders extends AsyncTask<String, Void, List<Order>> {

    private OrderDAO orderDAO;
    private OnOrderResultCallback callback;

    public SearchOrders(OrderDAO orderDAO, OnOrderResultCallback context) {
        this.orderDAO = orderDAO;
        this.callback = context;
    }

    @Override
    protected List<Order> doInBackground(String... strings) {
        List<Order> orders = orderDAO.searchAll();
        return orders;
    }

    @Override
    protected void onPostExecute(List<Order> orders) {
        super.onPostExecute(orders);
        callback.onResultOrder(orders);
    }
}
