package com.saucedemo.test;

import com.saucedemo.common.PageUrls;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductPage;
import commonLibs.DriverFactory;
import commonLibs.utils.ConfigUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTestCase {
    private static final Logger logger = LogManager.getLogger(BaseTestCase.class);
    private static WebDriver webDriver;
    public static WebDriver getWebDriver() {
        return webDriver;
    }

    @BeforeTest
    public void setup() throws Exception {
        webDriver = DriverFactory.startBrowser(ConfigUtils.getTestingBrowser());
        DriverFactory.navigateToUrl(ConfigUtils.getBaseUrl(), ConfigUtils.getLongTimeoutSecond());
        webDriver.manage().deleteAllCookies();
    }

    public ProductPage goToProductPage() {
        DriverFactory.navigateToUrl(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        ProductPage productPage;
        // Login if user doesn't login
        if (getWebDriver().getCurrentUrl().equalsIgnoreCase(ConfigUtils.getBaseUrl())) {
            LoginPage loginPage = new LoginPage(webDriver);
            productPage = loginPage.login(ConfigUtils.getLoginName(), ConfigUtils.getPassword())
                    .verifySuccessfulAuthentication(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        } else {
            productPage = new ProductPage(getWebDriver());
        }
        return productPage;
    }

   @AfterTest(alwaysRun = true)
    public void tearDown() {
        DriverFactory.closeBrowser();
    }
}
