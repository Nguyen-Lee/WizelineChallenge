package com.saucedemo.pages;

import com.saucedemo.common.PageUrls;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HeaderCom extends BasePage {
    MainMenuCom menuCom;

    @CacheLookup
    @FindBy(id="react-burger-menu-btn")
    WebElement burgerMenuButton;

    @FindBy(className = "shopping_cart_link")
    WebElement shoppingCartLink;

    String shoppingCartBadgeClassName = "shopping_cart_badge";

    public HeaderCom(WebDriver webDriver) {
        super(webDriver);
        menuCom = new MainMenuCom(webDriver);
    }

    public MainMenuCom showMainMenu() {
        burgerMenuButton.click();
        waitUtils.waitForElement(menuCom.menuPanelEle, ConfigUtils.getShortTimeoutSecond());
        return menuCom;
    }

    public ShoppingCartPage goToShoppingCart() {
        shoppingCartLink.click();
        waitUtils.waitForPageLoad(ConfigUtils.getDefaultTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.SHOPPING_CART_PAGE);
        return new ShoppingCartPage(webDriver);
    }

    public boolean isEmptyCart() {
        return (webDriver.findElements(By.className(shoppingCartBadgeClassName)).size() == 0);
    }

    public int getNumbersOfItemInCart() {
        try {
            WebElement shoppingCartBadge = webDriver.findElement(By.className(shoppingCartBadgeClassName));
            return Integer.parseInt(shoppingCartBadge.getText());
        } catch (NoSuchElementException exception) {
            logger.info("Empty cart");
            return 0;
        }
    }

    public HeaderCom verifyCartItemsIncreased(int itemCountBefore) {
        int currentCartItemCount = this.getNumbersOfItemInCart();
        logger.info(String.format("Numbers of item in cart changed from %s to %s", itemCountBefore, currentCartItemCount));
        Assert.assertEquals(currentCartItemCount, ++itemCountBefore, "Incorrect numbers of item in cart");
        return this;
    }

    public HeaderCom verifyCartItemsDecreased(int itemCountBefore) {
        int currentCartItemCount = this.getNumbersOfItemInCart();
        logger.info(String.format("Numbers of item in cart changed from %s to %s", itemCountBefore, currentCartItemCount));
        Assert.assertEquals(currentCartItemCount, --itemCountBefore, "Incorrect numbers of item in cart");
        return this;
    }
}
