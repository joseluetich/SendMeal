package com.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dam.sendmeal.model.Plato;

public class PlatesListActivity extends AppCompatActivity {

    private RecyclerView platesListRecyclerView;
    private RecyclerView.Adapter platesListAdapter;
    private RecyclerView.LayoutManager platesListLayoutManager;
    Toolbar platesListToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plates);

        platesListToolbar = findViewById(R.id.platesListToolbar);
        setSupportActionBar(platesListToolbar);

        platesListRecyclerView = (RecyclerView) findViewById(R.id.platesRecyclerView);
        platesListRecyclerView.setHasFixedSize(true);

        platesListLayoutManager = new LinearLayoutManager(this);
        platesListRecyclerView.setLayoutManager(platesListLayoutManager);

        platesListAdapter = new PlatesListAdapter(Plato.getListPlates());
        platesListRecyclerView.setAdapter(platesListAdapter);

    }
}