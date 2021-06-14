package com.saucedemo.dataProvider;

import org.testng.annotations.DataProvider;

public class CheckoutProvider {
    @DataProvider
    private Object[][] checkoutInfo() {
        return new Object[][]{
                {"Sofia", "Lee", "70000"}
        };
    }
}
