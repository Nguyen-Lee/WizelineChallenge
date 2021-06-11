package commonLibs.utils;

import commonLibs.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public WaitUtils(WebDriver driver) {
        WaitUtils.driver = driver;
    }

    public WebDriverWait setWebDriverWait(long timeout) {
        wait = new WebDriverWait(driver, timeout);
        return wait;
    }

    public void waitForElement(WebElement element, long timeout) {
        logger.info(String.format("Wait element '%s' in %s seconds", element.getTagName(), timeout));
        setWebDriverWait(timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElement(By locator, long timeout) {
        logger.info(String.format("Wait element has locator '%s' in %s seconds", locator, timeout));
        setWebDriverWait(timeout);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }

    public void waitForElementEnabled(WebElement element, long timeout) {
        logger.info(String.format("Wait element '%s' in %s seconds", element.getTagName(), timeout));
        setWebDriverWait(timeout);
        wait.until(ExpectedConditions.elementToBeSelected(element));
    }

    public void waitForElementInvisible(WebElement element, long timeout) {
        logger.info(String.format("Wait element '%s' invisible in %s seconds", element.getTagName(), timeout));
        setWebDriverWait(timeout);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void clickElementAsReady(WebElement element, long timeout) {
        logger.info(String.format("Wait element has locator '%s' in %s seconds", element.getTagName(), timeout));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void waitForPageLoad(long timeout) {
        logger.info(String.format("Wait for page %s load", driver.getCurrentUrl()));
        setWebDriverWait(timeout);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' ? true : false"));
    }

    public Alert waitForAlert(long timeout) {
        setWebDriverWait(timeout);
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert();
    }
}
