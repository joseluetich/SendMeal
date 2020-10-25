package com.dam.sendmeal.model;

import java.util.ArrayList;

public class Order {
    private String email;
    private Address address;
    private Boolean toShip;
    private ArrayList<Plate> plates = new ArrayList<>();

    public Order() {
    }

    public Order(String email, Boolean toShip, ArrayList<Plate> plates) {
        this.email = email;
        this.toShip = toShip;
        this.plates = plates;
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

    public ArrayList<Plate> getPlates() {
        return plates;
    }

    public void setPlates(ArrayList<Plate> plates) {
        this.plates = plates;
    }
}
