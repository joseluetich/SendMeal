package com.dam.sendmeal.retrofit;

import android.util.Log;

import com.dam.sendmeal.model.Order;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCallback {

    OrderService orderService = APIRest.getInstance().getOrderService();

    public void getOrders() {

        Call<List<Order>> callOrders = orderService.getOrderList();

        callOrders.enqueue(
                new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Retorno Exitoso");
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                    }
                }
        );

    }

    public void getOrderById(String id) {

        Call<Order> callOrder = orderService.getOrder(id);

        callOrder.enqueue(
                new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Retorno Exitoso");
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                    }
                }
        );

    }

    public void createOrder(Order order) throws JSONException {

        JSONObject bodyExample = new JSONObject();
        bodyExample.put("id", order.getIdOrder());
        bodyExample.put("platosId", order.getPlates().stream().map(plate -> plate.getIdPlate()));

        Call<Order> callOrder = orderService.createOrder(bodyExample.toString());

        callOrder.enqueue(
                new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Retorno Exitoso");
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                    }
                }
        );

    }

}
