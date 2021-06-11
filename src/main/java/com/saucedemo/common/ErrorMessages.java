package com.saucedemo.common;

public enum ErrorMessages {
    LOGIN_MISSING_USERNAME_ERROR("Epic sadface: Username is required"),
    LOGIN_MISSING_PASSWORD_ERROR("Epic sadface: Password is required"),
    LOGIN_LOCKED_USER_ERROR("Epic sadface: Sorry, this user has been locked out."),
    LOGIN_INVALID_CREDENTIAL_ERROR("Epic sadface: Username and password do not match any user in this service");

    private String message;
    public String getMessage() {
        return message;
    }

    ErrorMessages(String message) {
        this.message = message;
    }
}
