package com.saucedemo.pages;

import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    MainMenuCom menu;

    @CacheLookup
    @FindBy(id="react-burger-menu-btn")
    WebElement burgerMenuEle;

    @CacheLookup
    @FindBy(className="bm-menu-wrap")
    WebElement menuPanelEle;

    public ProductPage(WebDriver driver) {
        super(driver);
        menu = new MainMenuCom(driver);
    }
    
    public LoginPage logout() {
        burgerMenuEle.click();
        waitUtils.waitForElement(menuPanelEle, ConfigUtils.getShortTimeoutSecond());
        return menu.selectItem(menu.logoutLink).verifyLogoutSuccessfully();

    }
}
