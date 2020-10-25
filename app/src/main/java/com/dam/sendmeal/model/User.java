package com.dam.sendmeal.model;


public class User {

    private int id;
    private String name;
    private String password;
    private String email;
    private Double credit;
    private Card card;
    private BankAccount bankAccount;
    private Address address;

    public User() {
    }

    public User(int id, String name, String password, String email, Double credit, Card card, BankAccount bankAccount) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.credit = credit;
        this.card = card;
        this.bankAccount = bankAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}