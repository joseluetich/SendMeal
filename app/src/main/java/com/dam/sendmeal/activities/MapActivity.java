package com.dam.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dam.sendmeal.R;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener, OnMapReadyCallback {
    private GoogleMap mMap;
    Toolbar mapToolbar;
    Button confirmLocationButton;
    LatLng location;
    Marker m = null;
    PolylineOptions polylineOptions = new PolylineOptions(); //para realizar el recorrido
    ArrayList<Polyline> polylines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        confirmLocationButton = findViewById(R.id.confirmLocationButton);

        mapToolbar = findViewById(R.id.mapToolbar);
        setSupportActionBar(mapToolbar);//configuro la toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Context context = this;
        confirmLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m==null) {
                    Toast.makeText(context, "Seleccione una ubicacion en el mapa",Toast.LENGTH_LONG).show();
                }
                else {
                    double latitude = location.latitude;
                    double longitude = location.longitude;
                    Intent intent = new Intent(MapActivity.this, NewOrderActivity.class);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng initialPoint = null;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        }

        //Creo el marker del restaurant

        Random r = new Random();

        // Una direccion aleatoria de 0 a 359 grados
        int direccionRandomEnGrados = r.nextInt(360);

        // Una distancia aleatoria de 100 a 1000 metros
        int distanciaMinima = 100;
        int distanciaMaxima = 1000;
        int distanciaRandomEnMetros = r.nextInt(distanciaMaxima - distanciaMinima) + distanciaMinima;

        //Defino el punto sobre el que se estimara
        initialPoint = new LatLng(-31.636633, -60.699569);

        final LatLng newPoint = SphericalUtil.computeOffset(
                initialPoint,
                distanciaRandomEnMetros,
                direccionRandomEnGrados
        );

        mMap.addMarker(new MarkerOptions()
                .position(newPoint)
                .title("Restaurante")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPoint,15));



        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if(m == null) { // si todavia no existe
                    m = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Usted esta aqui")
                            .draggable(false));
                }
                else {
                    m.setPosition(latLng); //reubico el marcador
                }
                location = latLng;

                if(!polylines.isEmpty()) {
                    polylines.get(0).remove();
                    polylines.clear();
                }

                final PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(0x66FF0000);

                // Agregar ambos puntos
                polylineOptions.add(newPoint,location);

                Polyline line = mMap.addPolyline(polylineOptions);
                polylines.add(line);

            }
        });



    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}