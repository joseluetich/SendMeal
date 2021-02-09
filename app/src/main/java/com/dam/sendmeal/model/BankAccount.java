package com.dam.sendmeal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BankAccount {
    @PrimaryKey(autoGenerate = true)
    private long idBankAccount;
    private String cbu;
    private String alias;

    public BankAccount() {
    }

    public BankAccount(String cbu, String alias) {
        this.cbu = cbu;
        this.alias = alias;
    }

    public long getIdBankAccount() {
        return idBankAccount;
    }

    public void setIdBankAccount(long idBankAccount) {
        this.idBankAccount = idBankAccount;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
