package com.saucedemo.common;

public enum ProductSortOptions {
    NAME_ASC("az"),
    NAME_DESC("za"),
    PRICE_ASC("lohi"),
    PRICE_DESC("hilo");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ProductSortOptions(String value) {
        this.value = value;
    }
}
