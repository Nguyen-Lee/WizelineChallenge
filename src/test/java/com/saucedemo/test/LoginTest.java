package com.saucedemo.test;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.common.ErrorMessages;
import com.saucedemo.pages.ProductPage;
import commonLibs.utils.ConfigUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginTest extends BaseTestCase {
    LoginPage loginPage;
    @BeforeClass
    public void initData() {
        loginPage = new LoginPage(getDriver());
    }

    @DataProvider
    private Object[][] missingUsernameCases() {
        return new Object[][]{
                {"", ""},
                {"", "password"}
        };
    }

    @Test(description = "Login failed due to missing username", dataProvider = "missingUsernameCases")
    public void missingUsernameFailsLogin(String username, String password) {
        loginPage.login(username, password)
                    .verifyFailedAuthentication(ErrorMessages.LOGIN_MISSING_USERNAME_ERROR.getMessage());
    }

    @Test(description = "Login failed due to missing password",
            dependsOnMethods = "missingUsernameFailsLogin")
    public void missingPasswordFailsLogin() {
        loginPage.login("username", "")
                    .verifyFailedAuthentication(ErrorMessages.LOGIN_MISSING_PASSWORD_ERROR.getMessage());
    }

    private void pauseBetweenTest() {
        getDriver().manage().timeouts().implicitlyWait(ConfigUtils.getShortTimeoutSecond(), TimeUnit.SECONDS);
    }

    @DataProvider
    private Object[][] invalidCredentials() {
        return new Object[][]{
                {"admin", "password"},
                {"admin", "admin"},
                {"sofia", "' or '1'='1"},
                {"sofia", "' or 1='1"},
                {"sofia", "1' or 1=1 -- -"},
                {"sofia", "'' OR '1'='1'"},
                {"' or ' 1=1", "' or ' 1=1"},
                {"1' or 1=1 -- -", "blah"}
        };
    }

    @Test(description = "Login failed due to invalid credentials",
            dataProvider = "invalidCredentials",
            dependsOnMethods = "missingPasswordFailsLogin")
    public void invalidCredentialFailsLogin(String username, String password) {
        loginPage.login(username, password)
                    .verifyFailedAuthentication(ErrorMessages.LOGIN_INVALID_CREDENTIAL_ERROR.getMessage());
        pauseBetweenTest();
    }

    @DataProvider
    private Object[][] validCredentials() {
        return new Object[][]{
                {"standard_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"}
        };
    }

    @Test(description = "Login failed due to invalid credentials",
            dataProvider = "validCredentials",
            dependsOnMethods = "lockedCredentialFailsLogin")
    public void validCredentialLogin(String username, String password) {
        ProductPage productPage = loginPage.login(username, password)
                    .verifySuccessfulAuthentication(ConfigUtils.getBaseUrl() + ConfigUtils.getProductPage(), ConfigUtils.getDefaultTimeoutSecond());
        loginPage = productPage.getHeader().showMainMenu().logout().verifyLogoutSuccessfully();
        pauseBetweenTest();
    }

    @DataProvider
    private Object[][] lockedCredentials() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce"}
        };
    }
    @Test(description = "Login failed due to invalid credentials",
            dataProvider = "lockedCredentials",
            dependsOnMethods = "invalidCredentialFailsLogin")
    public void lockedCredentialFailsLogin(String username, String password) {
        loginPage.login(username, password)
                .verifyFailedAuthentication(ErrorMessages.LOGIN_LOCKED_USER_ERROR.getMessage());
    }


}
