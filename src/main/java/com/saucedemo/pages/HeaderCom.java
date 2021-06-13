package com.saucedemo.pages;

import com.saucedemo.common.PageUrls;
import commonLibs.utils.ConfigUtils;
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

    @CacheLookup
    @FindBy(className = "shopping_cart_link")
    WebElement shoppingCartLink;

    public HeaderCom(WebDriver webDriver) {
        super(webDriver);
        menuCom = new MainMenuCom(webDriver);
    }

    public MainMenuCom showMainMenu() {
        burgerMenuButton.click();
        waitUtils.waitForElement(menuCom.menuPanelEle, ConfigUtils.getShortTimeoutSecond());
        return menuCom;
    }

    public void goToShoppingCart() {
        shoppingCartLink.click();
        waitUtils.waitForPageLoad(ConfigUtils.getDefaultTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.SHOPPING_CART_PAGE);
    }
}
