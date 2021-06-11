package com.saucedemo.pages;

import com.saucedemo.pages.BasePage;
import commonLibs.ElementControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

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

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        logger.info(String.format("Login with username %s, password: %s", username, password));
        ElementControl.inputText(usernameEle, username);
        ElementControl.inputText(passwordEle, password);
        loginButtonEle.click();
    }

    public void verifyLoginFailed(String errorCode) {

    }
}
