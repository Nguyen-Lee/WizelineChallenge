package com.saucedemo.test;

import com.saucedemo.pages.ProductPage;
import org.testng.annotations.*;
import java.util.Arrays;
import java.util.List;

public class ShoppingTest extends BaseTestCase {
    ProductPage productPage;

    @BeforeClass
    public void initData() {
        productPage = goToProductPage();
    }

    @BeforeMethod
    @AfterClass
    public void clearCart() {
        if (!productPage.getHeader().isEmptyCart()) {
            productPage.getHeader().goToShoppingCart()
                        .clearCart()
                        .continueShopping();
        }
    }

    @Test(enabled = false)
    public void addAllItemsToCart() {
        //Array<String> products = new Arr
    }

    @Test
    public void addNeededItemsToCart() {
        String[] neededItems = {"Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)", "Sauce Labs Fleece Jacket"};
        List<String> neededProducts = Arrays.asList(neededItems);
        for (String productName : neededProducts) {
            int cartItemCount = productPage.getHeader().getNumbersOfItemInCart();
            productPage.addToCart(productName)
                        .canRemoveFromCart(productName)
                        .getHeader().verifyCartItemsIncreased(cartItemCount);
            productPage.getHeader().goToShoppingCart()
                        .isInCart(productName)
                        .continueShopping();
        }

    }
}
