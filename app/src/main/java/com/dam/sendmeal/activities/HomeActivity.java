package com.dam.sendmeal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.dam.sendmeal.R;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.repository.OrderRepository;
import com.dam.sendmeal.repository.PlateRepository;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements PlateRepository.OnResultCallback, OrderRepository.OnResultCallback{

    PlateRepository plateRepository;
    OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        plateRepository = new PlateRepository(this.getApplication(), this);
        orderRepository = new OrderRepository(this.getApplication(), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onResultOrder(List result) {

    }

    @Override
    public void onResultPlate(List<Plate> result) {

    }

    @Override
    public void onInsert() {

    }
}