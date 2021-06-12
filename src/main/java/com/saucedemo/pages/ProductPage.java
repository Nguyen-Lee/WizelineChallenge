package com.saucedemo.pages;

import com.saucedemo.common.ProductSortOptions;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPage extends BasePage {
    HeaderCom header;
    public HeaderCom getHeader() {
        return header;
    }

    @FindBy(xpath = "//select[@data-test='product_sort_container']")
    WebElement sortDropDownList;

    @CacheLookup
    @FindBy(className = "inventory_list")
    WebElement inventoryList;

    @FindBy(className = "inventory_item_name")
    List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    List<WebElement> productPrices;

    public ProductPage(WebDriver driver) {
        super(driver);
        header = new HeaderCom(driver);
    }

    public ProductPage sortProducts(ProductSortOptions sortOption) {
        Select sortSelect = new Select(sortDropDownList);
        sortSelect.selectByValue(sortOption.getValue());
        waitUtils.waitForPageLoad(ConfigUtils.getShortTimeoutSecond());
        return this;
    }

    private void verifySortByName(boolean isAZ) {
        List<String> originalList = productNames.stream()
                                    .map(s -> s.getText())
                                    .collect(Collectors.toList());
        logger.info("Original list: " + originalList);

        List<String> sortedList;
        sortedList = isAZ
                        ? originalList.stream().sorted().collect(Collectors.toList())
                        : originalList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        logger.info("Sorted list: " + sortedList);
        Assert.assertTrue(originalList.equals(sortedList), "Failed verification sort product by name");
    }

    public void verifySortByAZName() {
        logger.info("Verify sort by Name (A-Z)");
        verifySortByName(true);
    }

    public void verifySortByZAName() {
        logger.info("Verify sort by Name (Z-A)");
        verifySortByName(false);
    }

    private void verifySortByPrice(boolean isAsc) {
        List<Double> originalList = productPrices.stream()
                                    .map(s -> Double.parseDouble(s.getText().replace("$", "")))
                                    .collect(Collectors.toList());
        logger.info("Original list: " + originalList);
        List<Double> sortedList;
        sortedList = isAsc
                        ? originalList.stream().sorted().collect(Collectors.toList())
                        : originalList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        logger.info("Sorted list: " + sortedList);
        Assert.assertTrue(originalList.equals(sortedList), "Failed verification sort product by price");
    }

    public void verifySortByAscPrice() {
        logger.info("Verify sort by Price (Low to High)");
        verifySortByPrice(true);
    }

    public void verifySortByDescPrice() {
        logger.info("Verify sort by Price (High to Low)");
        verifySortByPrice(false);
    }
}
