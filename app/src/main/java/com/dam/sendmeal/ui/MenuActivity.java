package com.dam.sendmeal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.dam.sendmeal.R;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout menuDrawerLayout;
    NavigationView menuNavigationView;
    Toolbar homeToolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        homeToolbar = findViewById(R.id.homeToolbar);
        menuDrawerLayout = findViewById(R.id.menuDrawerLayout);
        menuNavigationView = findViewById(R.id.menuNavigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, menuDrawerLayout, homeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menuDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        menuNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createAccountOption:
                Intent createAccount = new Intent(this, NewAccountActivity.class);
                startActivity(createAccount);
                return true;
            case R.id.createPlateOption:
                Intent createPlate = new Intent(this, NewPlateActivity.class);
                startActivity(createPlate);
                return true;
            case R.id.listPlatesOption:
                Intent listPlates = new Intent(this, PlatesListActivity.class).putExtra("from","MenuActivity");
                startActivity(listPlates);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}