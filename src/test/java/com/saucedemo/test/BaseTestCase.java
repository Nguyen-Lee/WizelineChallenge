package com.saucedemo.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.saucedemo.common.PageUrls;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductPage;
import commonLibs.DriverFactory;
import commonLibs.utils.ConfigUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTestCase {
    private static ExtentReports extentReports;
    protected static ExtentTest test;
    private static WebDriver webDriver;
    public static WebDriver getWebDriver() {
        return webDriver;
    }

    @BeforeTest
    public void setup() throws Exception {
        webDriver = DriverFactory.startBrowser(ConfigUtils.getTestingBrowser());
        DriverFactory.navigateToUrl(ConfigUtils.getBaseUrl(), ConfigUtils.getLongTimeoutSecond());
        webDriver.manage().deleteAllCookies();
    }

    @BeforeSuite
    public void initExtentReport() {
        extentReports = new ExtentReports();
        SimpleDateFormat format = new SimpleDateFormat("MMMdd_yyyy_HH_mm_ss");
        String dateString = format.format(new Date());
        String reportFile = System.getProperty("user.dir")
                            .concat(File.separator + ConfigUtils.getSparkReportPath())
                            .concat(File.separator + dateString + ".html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFile);
        sparkReporter.config().setDocumentTitle(ConfigUtils.getReportDocumentTitle());
        sparkReporter.config().setReportName(ConfigUtils.getReportName());
        extentReports.attachReporter(sparkReporter);
    }

    public ProductPage goToProductPage() {
        DriverFactory.navigateToUrl(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        ProductPage productPage;
        // Login if user doesn't login
        if (getWebDriver().getCurrentUrl().equalsIgnoreCase(ConfigUtils.getBaseUrl())) {
            LoginPage loginPage = new LoginPage(webDriver);
            productPage = loginPage.login(ConfigUtils.getLoginName(), ConfigUtils.getPassword())
                    .verifySuccessfulAuthentication(PageUrls.PRODUCT_PAGE, ConfigUtils.getDefaultTimeoutSecond());
        } else {
            productPage = new ProductPage(getWebDriver());
        }
        return productPage;
    }

    public void startTest(String testName, String desc) {
        test = extentReports.createTest(testName, desc);
    }

    @AfterMethod
    public void reportResult(ITestResult result) {
        test.info(String.format("Method: %s.%s", result.getTestClass().getName(), result.getName()));
        test.info(String.format("Run on: %s, Execution time: %s ms", ConfigUtils.getTestingBrowser(), result.getEndMillis()-result.getStartMillis()));
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                test.pass(MarkupHelper.createLabel("PASSED", ExtentColor.GREEN));
                break;

            case ITestResult.FAILURE:
                test.fail(MarkupHelper.createLabel("FAILED", ExtentColor.RED));
                test.fail(result.getThrowable());
                String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
                test.addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0);
                break;

            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
                test.info(MarkupHelper.createLabel("PARTIAL PASSED", ExtentColor.BLUE));
                test.info(result.getThrowable());
                break;

            case ITestResult.SKIP:
                test.skip(MarkupHelper.createLabel("SKIPPED", ExtentColor.YELLOW));
                test.skip(result.getThrowable());
                break;

            default:
                test.log(test.getStatus(), MarkupHelper.createLabel(String.format("%s", test.getStatus()), ExtentColor.PURPLE));
        }


    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        extentReports.flush();
        DriverFactory.closeBrowser();
    }
}
