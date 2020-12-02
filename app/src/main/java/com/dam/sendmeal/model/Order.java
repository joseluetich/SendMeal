package com.dam.sendmeal.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverter;

import com.dam.sendmeal.converter.Converters;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    private Long idOrder;
    private String email;
    @Embedded
    private Address address;
    private Boolean toShip;
    private List<Plate> plates;

    public Order() {
    }

    public Order(String email, Boolean toShip, List<Plate> plates) {
        this.email = email;
        this.toShip = toShip;
        this.plates = plates;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Plate> getPlates() {
        return plates;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }

}
