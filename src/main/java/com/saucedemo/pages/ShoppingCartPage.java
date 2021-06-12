package com.saucedemo.pages;

import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage {
    HeaderCom headerCom;

    @CacheLookup
    @FindBy(id="continue-shopping")
    WebElement continueShoppingButton;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        headerCom = new HeaderCom(driver);
    }

    public ProductPage goToProductPage() {
        continueShoppingButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getShortTimeoutSecond());
        return new ProductPage(driver);
    }
}
