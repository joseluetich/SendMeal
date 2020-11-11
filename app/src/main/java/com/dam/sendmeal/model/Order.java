package com.dam.sendmeal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String email;
    private String streetAddress;
    private Integer numberAddress;
    private Integer floorAddress;
    private String apartmentAddress;
    private Boolean toShip;
    private List<Plate> plates = new ArrayList<>();

    public Order() {
    }

    public Order(String email, Boolean toShip, ArrayList<Plate> plates) {
        this.email = email;
        this.toShip = toShip;
        this.plates = plates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getToShip() {
        return toShip;
    }

    public void setToShip(Boolean toShip) {
        this.toShip = toShip;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getNumberAddress() {
        return numberAddress;
    }

    public void setNumberAddress(Integer numberAddress) {
        this.numberAddress = numberAddress;
    }

    public Integer getFloorAddress() {
        return floorAddress;
    }

    public void setFloorAddress(Integer floorAddress) {
        this.floorAddress = floorAddress;
    }

    public String getApartmentAddress() {
        return apartmentAddress;
    }

    public void setApartmentAddress(String apartmentAddress) {
        this.apartmentAddress = apartmentAddress;
    }

    public List<Plate> getPlates() {
        return plates;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }
}
