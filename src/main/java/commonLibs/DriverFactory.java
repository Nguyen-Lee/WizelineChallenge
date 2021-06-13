package commonLibs;

import commonLibs.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static org.openqa.selenium.remote.BrowserType.*;

public class DriverFactory {
    private static WebDriver webDriver;
    private static WaitUtils waitUtils;
    private static String currentWorkingDirectory = System.getProperty("user.dir");

    protected static WebDriver startChromeDriver() {
        if (webDriver == null) {
            System.setProperty("webdriver.chrome.driver", currentWorkingDirectory + "/drivers/chromedriver.exe");
            webDriver = new ChromeDriver();
        }
        return webDriver;
    }

    protected static WebDriver startFireFoxDriver() {
        if (webDriver == null) {
            System.setProperty("webdriver.firefox.driver", currentWorkingDirectory + "/drivers/geckodriver.exe");
            webDriver = new FirefoxDriver();
        }
        return webDriver;
    }

    protected static WebDriver startIEDriver() {
        if (webDriver == null) {
            webDriver = new InternetExplorerDriver();
        }
        return webDriver;
    }

    protected static WebDriver startEdgeDriver() {
        if (webDriver == null) {
            System.setProperty("webdriver.edge.driver", currentWorkingDirectory + "/drivers/msedgedriver.exe");
            webDriver = new EdgeDriver();
        }
        return webDriver;
    }

    public static WebDriver startBrowser(String browser) throws Exception {
        switch (browser) {
            case CHROME:
                webDriver = startChromeDriver();
                break;

            case FIREFOX:
                webDriver = startFireFoxDriver();
                break;

            case IE:
                webDriver = startIEDriver();
                break;

            case EDGE:
                webDriver = startEdgeDriver();
                break;

            default:
                throw new Exception("Invalid browser type");
        }
        waitUtils = new WaitUtils(webDriver);
        return webDriver;
    }

    public static void navigateToUrl(String url, long timeout) {
        webDriver.get(url);
        webDriver.manage().window().maximize();
        waitUtils.waitForPageLoad(timeout);
    }

    public static void closeBrowser() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
