package com.dam.sendmeal.repository;

import com.dam.sendmeal.model.Order;

import java.util.List;

public interface OnOrderResultCallback {
    void onResult(List<Order> orders);
}
