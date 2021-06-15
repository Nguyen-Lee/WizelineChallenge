package com.saucedemo.test;

import com.saucedemo.dataProvider.CheckoutProvider;
import com.saucedemo.models.CartItem;
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

    public void clearCart() {
        if (!productPage.getHeader().isEmptyCart()) {
            productPage.getHeader().goToShoppingCart()
                        .clearCart()
                        .continueShopping();
        }
    }

    @Test(description = "Add all available items to empty cart")
    public void addAvailableItemsToCart() {
        clearCart();
        ArrayList<String> availableItemNames = productPage.getAvailableItems();
        ShoppingCartPage shoppingCartPage = productPage.addAvailableItemsToCart()
                                                        .getHeader().verifyCartItemCount(availableItemNames.size())
                                                        .goToShoppingCart();
        for (String itemName : availableItemNames) {
            shoppingCartPage.isInCart(itemName);
        }
    }

    @Test(description = "Add some specific items to empty cart",
            dependsOnMethods = "addAvailableItemsToCart")
    public void addNeededItemsToCart() {
        clearCart();
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

    @Test(description = "Place order with cart of previous test",
            dependsOnMethods = "addNeededItemsToCart",
            dataProvider="checkoutInfo",
            dataProviderClass = CheckoutProvider.class)
    public void checkout(String firstName, String lastName, String postalCode) throws Exception {
        if (productPage.getHeader().isEmptyCart()) {
            throw new Exception("Don't run test with empty cart");
        }
        
        ShoppingCartPage shoppingCartPage = productPage.getHeader().goToShoppingCart();
        List<CartItem> orderItems = shoppingCartPage.getCheckoutItems();
        shoppingCartPage.checkoutStepOne()
                        .continueStepTwo(firstName, lastName, postalCode)
                        .withCartItems(orderItems)
                        .verifyDisplayedItems()
                        .placeOrder()
                        .verifyCheckoutSuccessful();
    }
}
