package com.dam.sendmeal.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dam.sendmeal.R;
import com.dam.sendmeal.model.Plate;
import com.dam.sendmeal.repository.PlateRepository;
import com.dam.sendmeal.viewModel.PlateViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PlatesListActivity extends AppCompatActivity {

    private RecyclerView platesListRecyclerView;
    private PlatesListAdapter platesListAdapter;
    private RecyclerView.LayoutManager platesListLayoutManager;
    Toolbar platesListToolbar;

    ExtendedFloatingActionButton orderFloatingActionButton;
    ArrayList<String> selectedPlates = new ArrayList<>();

    private PlateViewModel plateViewModel;
    private PlateRepository plateRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plates);

        plateViewModel = ViewModelProviders.of(this).get(PlateViewModel.class);

        platesListToolbar = findViewById(R.id.platesListToolbar);
        setSupportActionBar(platesListToolbar);

        platesListRecyclerView = findViewById(R.id.platesRecyclerView);
        platesListRecyclerView.setHasFixedSize(true);

        platesListLayoutManager = new LinearLayoutManager(this);
        platesListRecyclerView.setLayoutManager(platesListLayoutManager);

        platesListAdapter = new PlatesListAdapter(this);
        // platesListAdapter = new PlatesListAdapter(Plate.getListPlates(),this,selectedPlates);
        platesListRecyclerView.setAdapter(platesListAdapter);

        plateViewModel.getAllPlates().observe(this, new Observer<List<Plate>>() {
            @Override
            public void onChanged(@Nullable final List<Plate> plates) {
                // Update the cached copy of the words in the adapter.
                platesListAdapter.setPlates(plates);
            }
        });

        orderFloatingActionButton = findViewById(R.id.orderFloatingActionButton);

        if (getIntent().getStringExtra("from").equals("NewOrderActivity")) {
            orderFloatingActionButton.show();
        }
        else {
            orderFloatingActionButton.hide();
        }

        orderFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("PLATE", selectedPlates);
                setResult(RESULT_OK, intent);
                System.out.println("selectedPlates 2 : "+selectedPlates);
                finish();
            }
        });

//        orderFloatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.putStringArrayListExtra("PLATE", selectedPlates);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        if (!getIntent().getStringExtra("from").equals("NewOrderActivity")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_plates_list, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newOrder:
                Intent newOrderIntent = new Intent(this, NewOrderActivity.class);
                startActivity(newOrderIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //@Override
    public void onResult(List<Plate> result) {
        System.out.println("result: "+result.toString());
        platesListAdapter = new PlatesListAdapter(this);
        platesListRecyclerView.setAdapter(platesListAdapter);
        orderFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("PLATE", selectedPlates);
                setResult(RESULT_OK, intent);
                System.out.println("selectedPlates 2 : "+selectedPlates);
                finish();
            }
        });
    }
}