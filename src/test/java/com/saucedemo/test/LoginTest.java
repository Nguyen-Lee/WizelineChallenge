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
        loginPage = new LoginPage(getWebDriver());
    }

    @Test(description = "Login failed due to missing username",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "missingUsernameCases")
    public void missingUsernameFailsLogin(String username, String password) {
        startTest("LoginFailed_MissingUsername", "Login failed due to missing username");
        test.info(String.format("Test data: '%s' / '%s'", username, password) );
        loginPage.login(username, password)
                .verifyFailedAuthentication(PredefinedText.LOGIN_MISSING_USERNAME_ERROR);
    }

    @Test(description = "Login failed due to missing password")
    public void missingPasswordFailsLogin() {
        startTest("LoginFailed_MissingPassword", "Login failed due to missing password");
        test.info("Test data: 'username' / ''");
        loginPage.login("username", "")
                .verifyFailedAuthentication(PredefinedText.LOGIN_MISSING_PASSWORD_ERROR);
    }

    private void pauseBetweenTest() {
        getWebDriver().manage().timeouts().implicitlyWait(ConfigUtils.getShortTimeoutSecond(), TimeUnit.SECONDS);
    }

    @Test(description = "Login failed due to invalid credentials",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "invalidCredentials")
    public void invalidCredentialFailsLogin(String username, String password) {
        startTest("LoginFailed_InvalidCredential", "Login failed due to invalid username/ password");
        test.info(String.format("Test data: '%s' / '%s'", username, password) );
        loginPage.login(username, password)
                    .verifyFailedAuthentication(PredefinedText.LOGIN_INVALID_CREDENTIAL_ERROR);
        pauseBetweenTest();
    }

    @Test(description = "Login with valid credentials",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "validCredentials")
    public void validCredentialLogin(String username, String password) {
        startTest("LoginPassed", "Login with valid credentials");
        test.info(String.format("Test data: '%s' / '%s'", username, password) );
        ProductPage productPage = loginPage.login(username, password)
                    .verifySuccessfulAuthentication(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        loginPage = productPage.getHeader().showMainMenu().logout().verifyLogoutSuccessfully();
        pauseBetweenTest();
    }


    @Test(description = "Login failed due to locked account",
            dataProviderClass = AuthenticateProvider.class,
            dataProvider = "lockedCredentials")
    public void lockedCredentialFailsLogin(String username, String password) {
        startTest("LoginFailed_LockedAccount", "Login failed due to locked account");
        test.info(String.format("Test data: '%s' / '%s'", username, password) );
        loginPage.login(username, password)
                .verifyFailedAuthentication(PredefinedText.LOGIN_LOCKED_USER_ERROR);
    }
}