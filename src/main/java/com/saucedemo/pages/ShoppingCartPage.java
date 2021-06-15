package com.saucedemo.pages;

import com.saucedemo.common.PageUrls;
import com.saucedemo.models.CartItem;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPage extends BasePage {
    HeaderCom headerCom;

    @CacheLookup
    @FindBy(id="continue-shopping")
    WebElement continueShoppingButton;

    @CacheLookup
    @FindBy(id="checkout")
    WebElement checkoutButton;

    @FindBy(className = "cart_item")
    List<WebElement> cartItems;

    String inventoryClassName = "inventory_item_name";
    String itemLocator = "//div[@class='%s' and contains(text(), '%s')]";

    public ShoppingCartPage(WebDriver webDriver) {
        super(webDriver);
        headerCom = new HeaderCom(webDriver);
    }

    public ProductPage continueShopping() {
        continueShoppingButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getShortTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.PRODUCT_PAGE);
        return new ProductPage(webDriver);
    }

    public int itemRowsInCart(String productName) {
        return webDriver.findElements(By.xpath(String.format(itemLocator, inventoryClassName, productName))).size();
    }

    public ShoppingCartPage isInCart(String productName) {
        logger.info(String.format("Verify '%s' in shopping cart", productName));
        Assert.assertEquals(itemRowsInCart(productName), 1, String.format("Cart doesn't have item '%s'", productName));
        return this;
    }

    public ShoppingCartPage isNotInCart(String productName) {
        logger.info(String.format("Verify '%s' not in shopping cart", productName));
        Assert.assertEquals(itemRowsInCart(productName), 0, String.format("Cart still has item '%s'", productName));
        return this;
    }

    public ShoppingCartPage clearCart() {
        try {
            WebElement item = webDriver.findElement(By.className("cart_item_label"));
            while (item instanceof WebElement) {
                int cartItemCount = this.headerCom.getNumbersOfItemInCart();
                String productName = item.findElement(By.className(inventoryClassName)).getText();
                String removeButtonXpath = "div[@class='item_pricebar']/button[starts-with(@id, 'remove-')]";
                WebElement removeButton = item.findElement(By.xpath(removeButtonXpath));
                removeButton.click();
                this.isNotInCart(productName)
                    .headerCom.verifyCartItemCount(--cartItemCount);
                item = webDriver.findElement(By.className("cart_item_label"));
            }
        } catch (NoSuchElementException ex) {
            logger.info("Empty cart");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
        Assert.assertTrue(this.headerCom.isEmptyCart());
        return this;
    }

    public List<CartItem> getCheckoutItems() {
        ArrayList<CartItem> orderItems = new ArrayList<>();
        for (WebElement cartItem : cartItems) {
            CartItem item = new CartItem()
                            .withName(cartItem.findElement(By.className(inventoryClassName)).getText())
                            .withQuantity(Integer.parseInt(cartItem.findElement(By.className("cart_quantity")).getText()))
                            .withPrice(Float.parseFloat(cartItem.findElement(By.className("inventory_item_price")).getText().replace("$", "")));
            orderItems.add(item);
        }
        return orderItems;
    }

    public CheckoutStepOnePage checkoutStepOne() {
        checkoutButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getDefaultTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.CHECKOUT_STEP_ONE_PAGE);
        return new CheckoutStepOnePage(webDriver);
    }
}
