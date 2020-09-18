package com.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    Toolbar homeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeToolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(homeToolbar); //ver si no se define en las demas activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createAccountOption:
                Intent createAccount = new Intent(this, MainActivity.class);
                startActivity(createAccount);
                return true;
            case R.id.createPlateOption:
                Intent createPlate = new Intent(this, NewPlateActivity.class);
                startActivity(createPlate);
                return true;
            case R.id.listPlatesOption:
                /*Intent listPlates = new Intent(this, ListaDePlatosActivity.class);
                startActivity(listPlates);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}