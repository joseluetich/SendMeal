package com.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dam.sendmeal.model.Plate;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class PlatesListActivity extends AppCompatActivity {

    private RecyclerView platesListRecyclerView;
    private RecyclerView.Adapter platesListAdapter;
    private RecyclerView.LayoutManager platesListLayoutManager;
    Toolbar platesListToolbar;

    ExtendedFloatingActionButton orderFloatingActionButton;
    ArrayList<String> selectedPlates = new ArrayList<>();

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

        platesListAdapter = new PlatesListAdapter(Plate.getListPlates(),this,selectedPlates);
        platesListRecyclerView.setAdapter(platesListAdapter);

        orderFloatingActionButton = (ExtendedFloatingActionButton) findViewById(R.id.orderFloatingActionButton);

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
                finish();
            }
        });
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
}