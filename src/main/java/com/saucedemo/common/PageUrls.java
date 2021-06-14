package com.saucedemo.common;

import commonLibs.utils.ConfigUtils;

public class PageUrls {
    public static String PRODUCT_PAGE = ConfigUtils.getBaseUrl() + "inventory.html";
    public static String SHOPPING_CART_PAGE = ConfigUtils.getBaseUrl() + "cart.html";
    public static String CHECKOUT_STEP_ONE_PAGE = ConfigUtils.getBaseUrl() + "checkout-step-one.html";
    public static String CHECKOUT_STEP_TWO_PAGE = ConfigUtils.getBaseUrl() + "checkout-step-two.html";
    public static String CHECKOUT_COMPLETE_PAGE = ConfigUtils.getBaseUrl() + "checkout-complete.html";
}
