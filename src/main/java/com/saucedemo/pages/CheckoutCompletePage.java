package com.saucedemo.pages;

import com.saucedemo.common.PageUrls;
import com.saucedemo.common.PredefinedText;
import commonLibs.utils.ConfigUtils;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {
    HeaderCom headerCom;

    @CacheLookup
    @FindBy(id = "back-to-products")
    WebElement homeButton;

    @FindBy(className = "complete-header")
    WebElement completeHeaderEle;

    @FindBy(className = "complete-text")
    WebElement completeTextEle;

    public CheckoutCompletePage(WebDriver webDriver) {
        super(webDriver);
        headerCom = new HeaderCom(webDriver);
    }

    public void verifyCheckoutSuccessful() {
        Assert.assertTrue(headerCom.isEmptyCart());
        Assert.assertEquals(completeHeaderEle.getText(), PredefinedText.COMPLETE_HEADER);
        Assert.assertEquals(completeTextEle.getText(), PredefinedText.COMPLETE_TEXT);
    }

    public ProductPage goToProductPage() {
        homeButton.click();
        waitUtils.waitForPageLoad(ConfigUtils.getDefaultTimeoutSecond());
        org.testng.Assert.assertEquals(webDriver.getCurrentUrl(), PageUrls.PRODUCT_PAGE);
        return new ProductPage(webDriver);
    }
}
