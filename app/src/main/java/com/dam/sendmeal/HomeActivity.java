package com.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbarHome = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbarHome); //ver si no se define en las demas activity
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
            case R.id.opcionRegistrarse:
                Intent irARegistrarse = new Intent(this, MainActivity.class);
                startActivity(irARegistrarse);
                return true;
            case R.id.opcionCrearPlato:
                Intent irACrearPlato = new Intent(this, NuevoPlatoActivity.class);
                startActivity(irACrearPlato);
                return true;
            case R.id.opcionListaDePlatos:
                /*Intent irAListaDePlatos = new Intent(this, ListaDePlatosActivity.class);
                startActivity(irAListaDePlatos);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}