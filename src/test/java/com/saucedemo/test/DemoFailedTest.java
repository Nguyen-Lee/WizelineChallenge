package com.saucedemo.test;

import com.saucedemo.common.PredefinedText;
import com.saucedemo.dataProvider.AuthenticateProvider;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoFailedTest extends BaseTestCase {
    @Test(description = "Demo for failed testcase")
    public void failedDemo() {
        LoginPage loginPage = new LoginPage(getWebDriver());
        startTest("DemoFailedTC_InvalidAssertion", "Testcase failed with screenshot");
        Assert.assertEquals(getWebDriver().getTitle(), "Sauce demo site");
    }
}
