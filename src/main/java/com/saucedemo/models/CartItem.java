package com.saucedemo.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private String name;
    private float price;
    private int quantity;

    public CartItem withName(String name) {
        this.name = name;
        return this;
    }

    public CartItem withPrice(float price) {
        this.price = price;
        return this;
    }

    public CartItem withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
