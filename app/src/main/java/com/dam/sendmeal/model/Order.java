package com.dam.sendmeal.model;

public class Order {
    private String email;
    private Address address;
    private Boolean toShip;
    private Plate plate;

    public Order() {
    }

    public Order(String email, Boolean toShip, Plate plate) {
        this.email = email;
        this.toShip = toShip;
        this.plate = plate;
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

    public Plate getPlate() {
        return plate;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
