package com.dam.sendmeal.model;

import java.util.ArrayList;
import java.util.List;

public class Plato {
    private String title;
    private String description;
    private double price;
    private int calories;
    private static List<Plato> plates = new ArrayList<>();

    public Plato(String title, String description, double price, int calories) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.calories = calories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public static List<Plato> getLisaPlates() {
        return plates;
    }

    public static void setLisaPlates(List<Plato> lisaPlates) {
        Plato.plates = lisaPlates;
    }

    public void addToPlates() {
        plates.add(this);
    }

    /*public static String getStringListaPlatos() {
        String plato = "";

        for (Plato p : listaPlatos) {
            plato = (plato+" "+p.getTitulo());
        }
        return plato;
    }*/
}
