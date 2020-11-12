package com.dam.sendmeal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Plate {
    @PrimaryKey(autoGenerate = true)
    private Long idPlate;
    private String title;
    private String description;
    private Double price;
    private Integer calories;
    private Integer quantity;
    private static List<Plate> plates = new ArrayList<>();

    public Plate() {

    }

    public Plate(String title, String description, Double price, Integer calories) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.calories = calories;
    }
    public Long getIdPlate() {
        return idPlate;
    }

    public void setIdPlate(Long id) {
        this.idPlate = id;
    }

    public static List<Plate> getPlates() {
        return plates;
    }

    public static void setPlates(List<Plate> plates) {
        Plate.plates = plates;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStringPrice() {
        return ("Precio $ "+this.price);
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public static List<Plate> getListPlates() {
        return plates;
    }

    public static void setListPlates(List<Plate> listPlates) {
        Plate.plates = listPlates;
    }

    public void addToPlates() {
        plates.add(this);
    }

    public static String getStringListaPlatos() {
        String plato = "";

        for (Plate p : plates) {
            plato = (plato+" "+p.getTitle());
        }
        return plato;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
