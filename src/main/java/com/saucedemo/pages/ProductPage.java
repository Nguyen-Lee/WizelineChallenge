package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {
    HeaderCom header;
    public HeaderCom getHeader() {
        return header;
    }

    public ProductPage(WebDriver driver) {
        super(driver);
        header = new HeaderCom(driver);
    }
}
