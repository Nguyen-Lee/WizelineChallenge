package com.saucedemo.test;

import commonLibs.DriverFactory;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTestCase {
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

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        DriverFactory.closeBrowser();
    }
}
