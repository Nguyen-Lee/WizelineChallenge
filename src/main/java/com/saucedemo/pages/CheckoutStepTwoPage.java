package com.saucedemo.pages;

import com.saucedemo.common.PageUrls;
import com.saucedemo.models.CartItem;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import java.util.List;

public class CheckoutStepTwoPage extends BasePage {
    HeaderCom headerCom;

    @CacheLookup
    @FindBy(id = "finish")
    WebElement finishButton;

    @CacheLookup
    @FindBy(id = "cancel")
    WebElement cancelButton;

    @FindBy(className = "cart_item")
    List<WebElement> displayedItemElements;

    List<CartItem> cartItems;

    public CheckoutStepTwoPage(WebDriver webDriver) {
        super(webDriver);
        headerCom = new HeaderCom(webDriver);
    }

    public CheckoutStepTwoPage withCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public CheckoutStepTwoPage verifyDisplayedItems() {
        for (int i = 0; i < displayedItemElements.size(); i++) {
            CartItem cartItem = this.cartItems.get(i);
            logger.info(String.format("Verify displayed item for cart item '%s' - Quantity: %s - Price: $%s", cartItem.getName(), cartItem.getQuantity(), cartItem.getPrice()));
            WebElement itemEle = displayedItemElements.get(i);
            Assert.assertEquals(cartItem.getName(), itemEle.findElement(By.className("inventory_item_name")).getText());
            Assert.assertEquals(cartItem.getQuantity(), Integer.parseInt(itemEle.findElement(By.className("cart_quantity")).getText()));
            Assert.assertEquals(cartItem.getPrice(), Float.parseFloat(itemEle.findElement(By.className("inventory_item_price")).getText().replace("$", "")));
        }
        return this;
    }

    public ShoppingCartPage backToCart() {
        cancelButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getShortTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.SHOPPING_CART_PAGE);
        return new  ShoppingCartPage(webDriver);
    }

    public CheckoutCompletePage placeOrder() {
        finishButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getDefaultTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.CHECKOUT_COMPLETE_PAGE);
        return new CheckoutCompletePage(webDriver);
    }
}
