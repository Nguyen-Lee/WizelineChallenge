package com.saucedemo.test;

import com.saucedemo.pages.ProductPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ShoppingTest extends BaseTestCase {
    ProductPage productPage;

    @BeforeClass
    public void initData() {
        productPage = goToProductPage();
    }

    @Test
    public void addItemToCart() {
        String productName = "Sauce Labs Onesie";
        productPage.addProductToCart(productName)
                    .verifyProductInCart(productName)
                    .isProductInCart(productName);
    }
}
