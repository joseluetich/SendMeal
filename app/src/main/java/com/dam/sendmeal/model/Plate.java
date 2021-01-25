package com.dam.sendmeal.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Plate {

    private static List<Plate> plates = new ArrayList<>();
    @Expose
    @SerializedName("id")
    @PrimaryKey()
    @NonNull
    private String idPlate;
    @SerializedName("titulo")
    @Expose
    private String title;
    @SerializedName("descripcion")
    @Expose
    private String description;
    @SerializedName("precio")
    @Expose
    private Double price;
    @SerializedName("calorias")
    @Expose
    private Integer calories;
    private Integer quantity;

    public Plate() {
    }

    public Plate(String title, String description, Double price, Integer calories) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.calories = calories;
    }

    public static List<Plate> getPlates() {
        return plates;
    }

    public static void setPlates(List<Plate> plates) {
        Plate.plates = plates;
    }

    public static List<Plate> getListPlates() {
        return plates;
    }

    public static void setListPlates(List<Plate> listPlates) {
        Plate.plates = listPlates;
    }

    public static String getStringListaPlatos() {
        String plato = "";

        for (Plate p : plates) {
            plato = (plato + " " + p.getTitle());
        }
        return plato;
    }

    public String getIdPlate() {
        return idPlate;
    }

    public void setIdPlate(String id) {
        this.idPlate = id;
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
        return ("Precio $ " + this.price);
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void addToPlates() {
        plates.add(this);
    }

    public Integer getQuantity() {
        return quantity != null ? quantity : 0;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
