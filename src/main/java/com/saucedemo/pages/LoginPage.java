package com.saucedemo.pages;

import commonLibs.ElementControl;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePage {
    @CacheLookup
    @FindBy(id="user-name")
    private WebElement usernameEle;

    @CacheLookup
    @FindBy(id="password")
    private WebElement passwordEle;

    @CacheLookup
    @FindBy(id="login-button")
    private WebElement loginButtonEle;

    @CacheLookup
    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage login(String username, String password) {
        logger.info(String.format("Login with username %s, password: %s", username, password));
        ElementControl.inputText(usernameEle, username);
        ElementControl.inputText(passwordEle, password);
        loginButtonEle.click();
        return this;
    }

    public void verifyFailedAuthentication(String expectedMessage) {
        waitUtils.waitForElement(errorMessage, ConfigUtils.getShortTimeoutSecond());
        Assert.assertEquals(errorMessage.getText(), expectedMessage, "Failed authentication with different error");
    }

    public ProductPage verifySuccessfulAuthentication(String expectedUrl, long timeout) {
        waitUtils.waitForPageLoad(timeout);
        Assert.assertEquals(this.driver.getCurrentUrl(), expectedUrl, "Failed authentication");
        return new ProductPage(driver);
    }
}
