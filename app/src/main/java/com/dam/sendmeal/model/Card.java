package com.dam.sendmeal.model;

import java.util.Date;

public class Card {
    private String number;
    private String ccv;
    private Date expiration;
    private boolean isCredit;

    public Card() {
    }

    public Card(String number, String ccv, Date expiration, boolean isCredit) {
        this.number = number;
        this.ccv = ccv;
        this.expiration = expiration;
        this.isCredit = isCredit;
    }

    public String getNumero() {
        return number;
    }

    public void setNumero(String number) {
        this.number = number;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public boolean getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(boolean isCredit) {
        this.isCredit = isCredit;
    }
}
