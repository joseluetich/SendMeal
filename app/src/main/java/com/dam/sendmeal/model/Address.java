package com.dam.sendmeal.model;

public class Address {
    private String street;
    private Integer number;
    private Integer floor;
    private String apartment;

    public Address() {

    }

    public Address(String street, Integer number, Integer floor, String apartment) {
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.apartment = apartment;
    }

    public Address(String street, Integer number) {
        this.street = street;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}
