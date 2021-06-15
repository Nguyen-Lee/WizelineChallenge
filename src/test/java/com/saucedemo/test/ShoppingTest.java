package com.saucedemo.test;

import com.aventstack.extentreports.ExtentTest;
import com.saucedemo.dataProvider.CheckoutProvider;
import com.saucedemo.dataProvider.ProductProvider;
import com.saucedemo.models.CartItem;
import com.saucedemo.pages.CheckoutStepTwoPage;
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
            ShoppingCartPage shoppingCartPage = productPage.getHeader().goToShoppingCart();
            shoppingCartPage.clearCart()
                            .continueShopping();
        }
    }

    @Test(description = "Add all available items to empty cart")
    public void addAvailableItemsToCart() {
        startTest("AddToCart_AvailableItems", "Add all available items to cart");
        clearCart();
        ArrayList<String> availableItemNames = productPage.getAvailableItems();
        ShoppingCartPage shoppingCartPage = productPage.addAvailableItemsToCart()
                                                        .getHeader()
                                                        .verifyCartItemCount(availableItemNames.size())
                                                        .goToShoppingCart();
        for (String itemName : availableItemNames) {
            shoppingCartPage.isInCart(itemName);
        }
        test.info("Available items: " + availableItemNames.toString());
    }

    @Test(description = "Add some specific items to empty cart",
            dataProviderClass = ProductProvider.class,
            dataProvider = "neededItems",
            dependsOnMethods = "addAvailableItemsToCart")
    public void addNeededItemsToCart(String[] neededItems) {
        startTest("AddToCart_SpecificItems", "Add some specific items to cart");
        clearCart();
        test.info("Needed items: " + Arrays.toString(neededItems));
        List<String> neededProducts = Arrays.asList(neededItems);
        for (String productName : neededProducts) {
            int currentCartItems = productPage.getHeader().getNumbersOfItemInCart();
            ShoppingCartPage shoppingCartPage =  productPage.addToCart(productName)
                                                            .canRemoveFromCart(productName)
                                                            .getHeader()
                                                            .verifyCartItemCount(++currentCartItems)
                                                            .goToShoppingCart();
            shoppingCartPage.isInCart(productName)
                            .continueShopping();
        }
    }

    @Test(description = "Place order with cart of previous test",
            dependsOnMethods = "addNeededItemsToCart",
            dataProvider="checkoutInfo",
            dataProviderClass = CheckoutProvider.class)
    public void checkout(String firstName, String lastName, String postalCode) throws Exception {
        startTest("Checkout", "Place an order");
        if (productPage.getHeader().isEmptyCart()) {
            throw new Exception("Don't run test with empty cart");
        }
        
        ShoppingCartPage shoppingCartPage = productPage.getHeader().goToShoppingCart();
        List<CartItem> orderItems = shoppingCartPage.getCheckoutItems();
        CheckoutStepTwoPage checkoutStepTwoPage = shoppingCartPage.checkoutStepOne()
                                                                    .continueStepTwo(firstName, lastName, postalCode);
        checkoutStepTwoPage.withCartItems(orderItems)
                            .verifyDisplayedItems()
                            .placeOrder()
                            .verifyCheckoutSuccessful();
    }
}
