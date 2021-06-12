package com.saucedemo.test;

import com.saucedemo.common.PageUrls;
import com.saucedemo.common.ProductSortOptions;
import com.saucedemo.pages.ProductPage;
import commonLibs.DriverFactory;
import commonLibs.utils.ConfigUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductTest extends BaseTestCase {
    ProductPage productPage;

    @BeforeClass
    public void initData() {
        DriverFactory.navigateToUrl(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        // Login if user doesn't login
        if (getDriver().getCurrentUrl().equalsIgnoreCase(ConfigUtils.getBaseUrl())) {
            productPage = login();
        } else {
            productPage = new ProductPage(getDriver());
        }
    }

    @Test(description = "Sort products by Name (A to Z)")
    public void sortProductByAscName() {
        productPage.sortProducts(ProductSortOptions.NAME_ASC)
                    .verifySortByAZName();
    }

    @Test(description = "Sort products by Name (Z to A)", dependsOnMethods = "sortProductByAscName")
    public void sortProductByDescName() {
        productPage.sortProducts(ProductSortOptions.NAME_DESC)
                    .verifySortByZAName();
    }

    @Test(description = "Sort products by Price (low to high)")
    public void sortProductByAscPrice() {
        productPage.sortProducts(ProductSortOptions.PRICE_ASC)
                    .verifySortByAscPrice();
    }

    @Test(description = "Sort products by Price (high to low)", dependsOnMethods = "sortProductByAscPrice")
    public void sortProductByDescPrice() {
        productPage.sortProducts(ProductSortOptions.PRICE_DESC)
                    .verifySortByDescPrice();
    }
}