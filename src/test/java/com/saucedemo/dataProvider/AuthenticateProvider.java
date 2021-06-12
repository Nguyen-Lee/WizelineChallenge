package com.saucedemo.dataProvider;

import org.testng.annotations.DataProvider;

public class AuthenticateProvider {
    @DataProvider
    private Object[][] missingUsernameCases() {
        return new Object[][]{
                {"", ""},
                {"", "password"}
        };
    }

    @DataProvider
    private Object[][] invalidCredentials() {
        return new Object[][]{
                {"admin", "password"},
                {"admin", "admin"},
                {"\" or \"\"=\"", "\" or \"\"=\""},
                {"standard_user", "' or '1'='1"},
                {"standard_user", "' or 1='1"},
                {"standard_user", "1' or 1=1 -- -"},
                {"standard_user", "'' OR '1'='1'"},
                {"' or ' 1=1", "' or ' 1=1"},
                {"1' or 1=1 -- -", "blah"}
        };
    }

    @DataProvider
    private Object[][] lockedCredentials() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce"}
        };
    }

    @DataProvider
    private Object[][] validCredentials() {
        return new Object[][]{
                {"standard_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"}
        };
    }
}
