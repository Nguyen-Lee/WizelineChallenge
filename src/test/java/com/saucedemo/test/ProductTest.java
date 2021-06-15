package com.saucedemo.test;

import com.saucedemo.common.ProductSortOptions;
import com.saucedemo.pages.ProductPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductTest extends BaseTestCase {
    ProductPage productPage;

    @BeforeClass
    public void initData() {
        productPage = goToProductPage();
    }

    @Test(description = "Sort products by Name (A to Z)")
    public void sortProductByAscName() {
        startTest("ProductSort_ByName(A-Z)", "Sort products by Name (A to Z)");
        productPage.sortProducts(ProductSortOptions.NAME_ASC)
                    .verifySortByAZName();
    }

    @Test(description = "Sort products by Name (Z to A)")
    public void sortProductByDescName() {
        startTest("ProductSort_ByName(Z-A)", "Sort products by Name (Z to A)");
        productPage.sortProducts(ProductSortOptions.NAME_DESC)
                    .verifySortByZAName();
    }

    @Test(description = "Sort products by Price (low to high)")
    public void sortProductByAscPrice() {
        startTest("ProductSort_ByPrice(Low-High)", "Sort products by Price (low to high)");
        productPage.sortProducts(ProductSortOptions.PRICE_ASC)
                    .verifySortByAscPrice();
    }

    @Test(description = "Sort products by Price (high to low)")
    public void sortProductByDescPrice() {
        startTest("ProductSort_ByPrice(High-Low)", "Sort products by Price (high to low)");
        productPage.sortProducts(ProductSortOptions.PRICE_DESC)
                    .verifySortByDescPrice();
    }
}