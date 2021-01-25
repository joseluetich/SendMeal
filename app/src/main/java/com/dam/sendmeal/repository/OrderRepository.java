package com.dam.sendmeal.repository;

import android.app.Application;
import android.util.Log;

import com.dam.sendmeal.dao.OrderDAO;
import com.dam.sendmeal.model.Order;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.retrofit.APIRest;
import com.dam.sendmeal.retrofit.OrderCallback;
import com.dam.sendmeal.retrofit.PlateCallback;

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.dam.sendmeal.dao.PlateOrderRelationDAO;



public class OrderRepository implements OnOrderResultCallback {
    private OrderDAO orderDAO;
    //private PlateOrderRelationDAO plateOrderRelationDAO;
    private OnResultCallback callback;

    public OrderRepository(Application application, OnResultCallback context) {
        AppDatabase db = AppDatabase.getInstance(application);
        orderDAO = db.orderDao();
        //plateOrderRelationDAO = db.plateOrderRelationDAO();
        callback = context;
    }

    @Override
    public void onResultOrder(List<Order> orders) {
        Log.d("DEBUG", "Order found");
        // callback.onResultOrder(orders);
        Call<List<Order>> callPlates = APIRest.getInstance().getOrderService().getOrderList();

        callPlates.enqueue(
                new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        Log.d("DEBUG", "Retorno Exitoso");
                        System.out.println(response);
                        if (response.code() == 200) {
                            callback.onResultOrder(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        Log.d("DEBUG", "Retorno Fallido");
                        System.out.println(t);
                    }
                }
        );
    }
    // callback.onResultPlate(plates);


    public void insert(final Order order) {
//        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                orderDAO.insert(order);
//                callback.onInsert();
//            }
//        });
        try{
            new OrderCallback().createOrder(order);
        } catch (JSONException e){
            System.out.println("Exploto");
        }

    }

    public void delete(final Order order) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderDAO.delete(order);
            }
        });
    }

    public void update(final Order order) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderDAO.update(order);
            }
        });
    }

    /*public void getPlatesOfOrder(final long idOrder){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                plateOrderRelationDAO.getPlatesOfOrder(idOrder);
            }
        });
    }*/

    public void search(String id) {
        new SearchOrderById(orderDAO, this).execute(id);
    }

    public void searchAll() {
        new SearchOrders(orderDAO, this).execute();
    }

    public interface OnResultCallback<T> {
        void onResultOrder(List<T> result);
        void onInsert();
    }
}