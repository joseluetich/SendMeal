package com.dam.sendmeal.model;

import com.dam.sendmeal.utils.LatLongConverter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.List;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    private Long idOrder;
    private String email;
    @Embedded
    private Address address;
    private LatLng location;
    private Boolean toDeliver;
    private List<Plate> plates;

    public Order() {
    }

    public Order(String email, Boolean toDeliver, List<Plate> plates) {
        this.email = email;
        this.toDeliver = toDeliver;
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

    public Boolean getToDeliver() {
        return toDeliver;
    }

    public void setToDeliver(Boolean toDeliver) {
        this.toDeliver = toDeliver;
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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
