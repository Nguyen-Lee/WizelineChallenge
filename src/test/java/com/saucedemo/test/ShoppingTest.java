package com.saucedemo.test;

import com.saucedemo.pages.ProductPage;
import com.saucedemo.pages.ShoppingCartPage;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingTest extends BaseTestCase {
    ProductPage productPage;

    @BeforeClass
    public void initData() {
        productPage = goToProductPage();
    }

    @BeforeMethod
    public void clearCart() {
        if (!productPage.getHeader().isEmptyCart()) {
            productPage.getHeader().goToShoppingCart()
                        .clearCart()
                        .continueShopping();
        }
    }

    @Test
    public void addAvailableItemsToCart() {
        ArrayList<String> availableItemNames = productPage.getAvailableItems();
        ShoppingCartPage shoppingCartPage = productPage.addAvailableItemsToCart()
                                                        .getHeader().verifyCartItemCount(availableItemNames.size())
                                                        .goToShoppingCart();
        for (String itemName : availableItemNames) {
            shoppingCartPage.isInCart(itemName);
        }
    }

    @Test
    public void addNeededItemsToCart() {
        String[] neededItems = {"Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)", "Sauce Labs Fleece Jacket"};
        List<String> neededProducts = Arrays.asList(neededItems);
        for (String productName : neededProducts) {
            int currentCartItems = productPage.getHeader().getNumbersOfItemInCart();
            productPage.addToCart(productName)
                        .canRemoveFromCart(productName)
                        .getHeader().verifyCartItemCount(++currentCartItems);

            productPage.getHeader().goToShoppingCart()
                        .isInCart(productName)
                        .continueShopping();
        }
    }
}
