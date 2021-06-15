package com.saucedemo.dataProvider;

import org.testng.annotations.DataProvider;

public class ProductProvider {
    @DataProvider
    private Object[][] neededItems() {
        return new Object[][]{
                {"Sauce Labs Onesie"},
                {"Test.allTheThings() T-Shirt (Red)", "Sauce Labs Fleece Jacket", "Sauce Labs Onesie"}
        };
    }
}
