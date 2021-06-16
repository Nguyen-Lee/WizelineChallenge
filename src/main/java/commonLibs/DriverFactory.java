package commonLibs;

import commonLibs.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import java.io.File;

import static org.openqa.selenium.remote.BrowserType.*;

public class DriverFactory {
    private static WebDriver webDriver;
    private static WaitUtils waitUtils;
    private static String driverDirectory = System.getProperty("user.dir").concat(File.separator + "drivers" + File.separator);

    protected static WebDriver startChromeDriver() {
        if (webDriver == null) {
            System.setProperty("webdriver.chrome.driver", driverDirectory + "chromedriver.exe");
            webDriver = new ChromeDriver();
        }
        return webDriver;
    }

    protected static WebDriver startFireFoxDriver() {
        if (webDriver == null) {
            System.setProperty("webdriver.firefox.driver", driverDirectory + "geckodriver.exe");
            webDriver = new FirefoxDriver();
        }
        return webDriver;
    }

    protected static WebDriver startIEDriver() {
        if (webDriver == null) {
            System.setProperty("webdriver.ie.driver", driverDirectory + "IEDriverServer.exe");
            InternetExplorerOptions ieOption = new InternetExplorerOptions();
            ieOption.ignoreZoomSettings();
            ieOption.introduceFlakinessByIgnoringSecurityDomains();
            webDriver = new InternetExplorerDriver(ieOption);
        }
        return webDriver;
    }

    protected static WebDriver startEdgeDriver() {
        if (webDriver == null) {
            System.setProperty("webdriver.edge.driver", driverDirectory + "msedgedriver.exe");
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
