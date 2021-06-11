package com.saucedemo.test;

import com.saucedemo.pages.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends BaseTestCase {
    LoginPage loginPage;
    @BeforeClass
    public void initData() {
        loginPage = new LoginPage(getDriver());
    }
    @Test
    public void login() {
        loginPage.login("standard_user", "secret_sauce");
    }


}
