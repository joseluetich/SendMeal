package com.dam.sendmeal.retrofit;

import com.dam.sendmeal.model.Order;
import com.dam.sendmeal.model.Plate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {

    @GET("pedidos/{id}")
    Call<Order> getOrder(@Path("id") String id);

    @GET("pedidos")
    Call<List<Order>> getOrderList();

    @POST("pedidos")
    Call<Order> createOrder(@Body String order);
    /*
    Si deciden usar SendMeal-Fake-API deberán usar un body
    del tipo @Body String body. Lo cual les facilitara cumplir el formato esperado

    JSONObject bodyExample = new JSONObject();
    paramObject.put("email", "sample@gmail.com");
    paramObject.put("pass", "4384984938943");
    createPlato(bodyExample.toString())
  */
}