package com.saucedemo.pages;

import commonLibs.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver webDriver;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected static WaitUtils waitUtils;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this);
        waitUtils = new WaitUtils(this.webDriver);
    }
}
