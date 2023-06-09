package com.exemple.model;

import java.sql.Timestamp;


public class credit {
        private long credit_id;
        private int quantity;
       private Timestamp credit_date;
       private long client_id;
        private long produit_id;

    public credit(long credit_id, int quantity, Timestamp credit_date, long client_id, long produit_id) {
        this.credit_id = credit_id;
        this.quantity = quantity;
        this.credit_date = credit_date;
        this.client_id = client_id;
        this.produit_id = produit_id;
    }


    public long getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(long credit_id) {
        this.credit_id = credit_id;
    }

    public long getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(long produit_id) {
        this.produit_id = produit_id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }



    public Timestamp getCredit_date() {
        return credit_date;
    }

    public void setCredit_date(Timestamp credit_date) {
        this.credit_date = credit_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
