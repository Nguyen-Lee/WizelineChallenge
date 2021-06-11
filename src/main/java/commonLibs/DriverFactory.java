package commonLibs;

import commonLibs.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.remote.BrowserType.*;

public class DriverFactory {
    private static WebDriver driver;
    private static WaitUtils waitUtils;
    private static String currentWorkingDirectory = System.getProperty("user.dir");

    protected static WebDriver startChromeDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", currentWorkingDirectory + "/drivers/chromedriver.exe");
            driver = new ChromeDriver();
        }
        return driver;
    }

    protected static WebDriver startFireFoxDriver() {
        if (driver == null) {
            System.setProperty("webdriver.firefox.driver", currentWorkingDirectory + "/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        return driver;
    }

    protected static WebDriver startIEDriver() {
        if (driver == null) {
            driver = new InternetExplorerDriver();
        }
        return driver;
    }

    protected static WebDriver startEdgeDriver() {
        if (driver == null) {
            System.setProperty("webdriver.edge.driver", currentWorkingDirectory + "/drivers/msedgedriver.exe");
            driver = new EdgeDriver();
        }
        return driver;
    }

    public static WebDriver startBrowser(String browser) throws Exception {
        switch (browser) {
            case CHROME:
                driver = startChromeDriver();
                break;

            case FIREFOX:
                driver = startFireFoxDriver();
                break;

            case IE:
                driver = startIEDriver();
                break;

            case EDGE:
                driver = startEdgeDriver();
                break;

            default:
                throw new Exception("Invalid browser type");
        }
        waitUtils = new WaitUtils(driver);
        return driver;
    }

    public static void navigateToUrl(String url, long timeout) {
        driver.get(url);
        driver.manage().window().maximize();
        waitUtils.waitForPageLoad(timeout);
    }

    public static void closeBrowser() {
        driver.quit();
    }
}
