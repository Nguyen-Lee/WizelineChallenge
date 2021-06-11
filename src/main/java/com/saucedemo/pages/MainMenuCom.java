package com.saucedemo.pages;

import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class MainMenuCom extends BasePage {
    @CacheLookup
    @FindBy(className="bm-menu-wrap")
    WebElement menuPanelEle;

    @CacheLookup
    @FindBy(id="logout_sidebar_link")
    WebElement logoutLink;

    @CacheLookup
    @FindBy(id="inventory_sidebar_link")
    WebElement itemsLink;

    @CacheLookup
    @FindBy(id="react-burger-cross-btn")
    WebElement closeMenuButton;

    public MainMenuCom(WebDriver driver) {
        super(driver);
    }

    public MainMenuCom selectItem(WebElement item) {
        item.click();
        return this;
    }

    public void closeMainMenu() {
        closeMenuButton.click();
        waitUtils.waitForElementInvisible(menuPanelEle, ConfigUtils.getShortTimeoutSecond());
    }

    public MainMenuCom logout() {
        selectItem(logoutLink);
        return this;
    }

    public LoginPage verifyLogoutSuccessfully() {
        waitUtils.waitForPageLoad(ConfigUtils.getDefaultTimeoutSecond());
        Assert.assertEquals(ConfigUtils.getBaseUrl(), driver.getCurrentUrl(), "Redirect to incorrect page");
        return new LoginPage(driver);
    }
}
