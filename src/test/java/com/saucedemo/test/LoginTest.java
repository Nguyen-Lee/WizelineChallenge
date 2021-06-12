package com.saucedemo.test;

import com.saucedemo.common.PageUrls;
import com.saucedemo.dataProvider.AuthenticateProvider;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.common.PredefinedText;
import com.saucedemo.pages.ProductPage;
import commonLibs.utils.ConfigUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginTest extends BaseTestCase {
    LoginPage loginPage;
    @BeforeClass
    public void initData() {
        loginPage = new LoginPage(getDriver());
    }

    @Test(description = "Login failed due to missing username",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "missingUsernameCases")
    public void missingUsernameFailsLogin(String username, String password) {
        loginPage.login(username, password)
                    .verifyFailedAuthentication(PredefinedText.LOGIN_MISSING_USERNAME_ERROR);
    }

    @Test(description = "Login failed due to missing password",
            dependsOnMethods = "missingUsernameFailsLogin")
    public void missingPasswordFailsLogin() {
        loginPage.login("username", "")
                    .verifyFailedAuthentication(PredefinedText.LOGIN_MISSING_PASSWORD_ERROR);
    }

    private void pauseBetweenTest() {
        getDriver().manage().timeouts().implicitlyWait(ConfigUtils.getShortTimeoutSecond(), TimeUnit.SECONDS);
    }

    @Test(description = "Login failed due to invalid credentials",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "invalidCredentials",
            dependsOnMethods = "missingPasswordFailsLogin")
    public void invalidCredentialFailsLogin(String username, String password) {
        loginPage.login(username, password)
                    .verifyFailedAuthentication(PredefinedText.LOGIN_INVALID_CREDENTIAL_ERROR);
        pauseBetweenTest();
    }

    @Test(description = "Login failed due to invalid credentials",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "validCredentials",
            dependsOnMethods = "lockedCredentialFailsLogin")
    public void validCredentialLogin(String username, String password) {
        ProductPage productPage = loginPage.login(username, password)
                    .verifySuccessfulAuthentication(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        loginPage = productPage.getHeader().showMainMenu().logout().verifyLogoutSuccessfully();
        pauseBetweenTest();
    }


    @Test(description = "Login failed due to invalid credentials",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "lockedCredentials",
            dependsOnMethods = "invalidCredentialFailsLogin")
    public void lockedCredentialFailsLogin(String username, String password) {
        loginPage.login(username, password)
                .verifyFailedAuthentication(PredefinedText.LOGIN_LOCKED_USER_ERROR);
    }
}