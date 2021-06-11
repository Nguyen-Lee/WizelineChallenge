package com.saucedemo.pages;

import commonLibs.DriverFactory;
import commonLibs.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(DriverFactory.class);
    protected static WaitUtils waitUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        waitUtils = new WaitUtils(this.driver);
    }
}
