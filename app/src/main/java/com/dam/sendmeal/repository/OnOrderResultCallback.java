package com.dam.sendmeal.repository;

import com.dam.sendmeal.model.Order;

import java.util.List;

public interface OnOrderResultCallback {
    void onResultOrder(List<Order> orders);
}
