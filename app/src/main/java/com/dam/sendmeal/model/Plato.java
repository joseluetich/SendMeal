package com.dam.sendmeal.model;

import android.widget.Toast;

import com.dam.sendmeal.NuevoPlatoActivity;

import java.util.ArrayList;
import java.util.List;

public class Plato {
    private int id;
    private String titulo;
    private String descripcion;
    private double precio;
    private int calorias;
    private static List<Plato> listaPlatos = new ArrayList<>();

    public Plato(int id, String titulo, String descripcion, double precio, int calorias) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public List<Plato> getListaPlatos() {
        return listaPlatos;
    }

    public void setListaPlatos(List<Plato> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    public void agregarALista() {
        listaPlatos.add(this);
    }

    /*public static String getStringListaPlatos() {
        String plato = "";

        for (Plato p : listaPlatos) {
            plato = (plato+" "+p.getTitulo());
        }
        return plato;
    }*/
}
