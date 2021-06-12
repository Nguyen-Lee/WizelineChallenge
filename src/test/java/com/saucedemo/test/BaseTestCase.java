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
    private static WebDriver driver;
    public static WebDriver getDriver() {
        return driver;
    }

    @BeforeTest
    public void setup() throws Exception {
        driver = DriverFactory.startBrowser(ConfigUtils.getTestingBrowser());
        DriverFactory.navigateToUrl(ConfigUtils.getBaseUrl(), ConfigUtils.getLongTimeoutSecond());
        driver.manage().deleteAllCookies();
    }

    public ProductPage login() {
        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = loginPage.login(ConfigUtils.getLoginName(), ConfigUtils.getPassword())
                                            .verifySuccessfulAuthentication(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        return productPage;
    }

   @AfterTest(alwaysRun = true)
    public void tearDown() {
        DriverFactory.closeBrowser();
    }
}
