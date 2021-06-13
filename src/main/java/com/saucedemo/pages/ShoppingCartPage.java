package com.saucedemo.pages;

import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class ShoppingCartPage extends BasePage {
    HeaderCom headerCom;

    @CacheLookup
    @FindBy(id="continue-shopping")
    WebElement continueShoppingButton;

    String itemLocator = "//div[@class='inventory_item_name' and contains(text(), '%s')]";

    public ShoppingCartPage(WebDriver webDriver) {
        super(webDriver);
        headerCom = new HeaderCom(webDriver);
    }

    public ProductPage goToProductPage() {
        continueShoppingButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getShortTimeoutSecond());
        return new ProductPage(webDriver);
    }

    public void isProductInCart(String productName) {
        WebElement itemInCart = webDriver.findElement(By.xpath(String.format(itemLocator, productName)));
        Assert.assertNotNull(itemInCart);
    }
}
