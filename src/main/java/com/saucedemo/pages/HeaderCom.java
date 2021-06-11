package com.saucedemo.pages;

import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class HeaderCom extends BasePage {
    MainMenuCom menuCom;

    @CacheLookup
    @FindBy(id="react-burger-menu-btn")
    WebElement burgerMenuEle;

    public HeaderCom(WebDriver driver) {
        super(driver);
        menuCom = new MainMenuCom(driver);
    }

    public MainMenuCom showMainMenu() {
        burgerMenuEle.click();
        waitUtils.waitForElement(menuCom.menuPanelEle, ConfigUtils.getShortTimeoutSecond());
        return menuCom;
    }
}
