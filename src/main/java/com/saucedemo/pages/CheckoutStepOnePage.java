package com.saucedemo.pages;

import com.saucedemo.common.PageUrls;
import commonLibs.ElementControl;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class CheckoutStepOnePage extends BasePage {
    HeaderCom headerCom;

    @CacheLookup
    @FindBy(id = "first-name")
    WebElement firstNameInput;

    @CacheLookup
    @FindBy(id = "last-name")
    WebElement lastNameInput;

    @CacheLookup
    @FindBy(id = "postal-code")
    WebElement postalCodeInput;

    @CacheLookup
    @FindBy(id = "continue")
    WebElement continueButton;

    @CacheLookup
    @FindBy(id = "cancel")
    WebElement cancelButton;

    public CheckoutStepOnePage(WebDriver webDriver) {
        super(webDriver);
        headerCom = new HeaderCom(webDriver);
    }

    public CheckoutStepTwoPage continueStepTwo(String firstName, String lastName, String postalCode) {
        ElementControl.inputText(firstNameInput, firstName);
        ElementControl.inputText(lastNameInput, lastName);
        ElementControl.inputText(postalCodeInput, postalCode);
        continueButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getShortTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.CHECKOUT_STEP_TWO_PAGE);
        return new CheckoutStepTwoPage(webDriver);
    }

    public ShoppingCartPage backToCart() {
        cancelButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getShortTimeoutSecond());
        Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.SHOPPING_CART_PAGE);
        return new  ShoppingCartPage(webDriver);
    }
}