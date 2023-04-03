package com.exemple.model;

public class product {
    private long prod_id;
    private String name;
    private String description;
    private float price;


    public product(long prod_id, String name, String description, String price) {
        this.prod_id = prod_id;
        this.name = name;
        this.description = description;
        this.price = Float.parseFloat(price);
    }

    public long getProd_id() {
        return prod_id;
    }

    public void setProd_id(long prod_id) {
        this.prod_id = prod_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "product{" +
                "price=" + price +
                '}';
    }
}
