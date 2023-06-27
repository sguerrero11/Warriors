package utils;


import designpattern.pom.BasePage;
import helpers.BrowserDriverHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.testng.*;

public class ProjectListener extends BasePage implements ITestListener {

    // private final Logger log = LoggerFactory.getLogger(projectListener.class);

    @Override
    public void onTestStart(ITestResult result) {

        logInfo("@Test: {}", result.getName());
        logInfo("Description: {}", result.getMethod().getDescription());
        logSeparator();
        //result.setAttribute("WebDriver", this.driver1);

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logInfo("@Test: {}", result.getName() + " has PASSED");
        logSeparator();

    }

    @Override
    public void onTestFailure(ITestResult result) {
        logInfo("@Test: {}", result.getName() + " has FAILED");
        logSeparator();

        /* TO BE REVIEWED
        //Get driver from BaseTest and assign to local webdriver variable.
//        Object testClass = result.getInstance();
        //WebDriver driver = ((BaseTest) testClass).getDriver();
        //Take base64Screenshot screenshot for extent reports
//        String base64Screenshot =
//                "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
        //ExtentReports log and screenshot operations for failed tests.
        //getTest().log(Status.FAIL, "Test Failed",
        //getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
        //Take the screenshot
        try {
            takeScreenshot(result.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logInfo();("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");

         */
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logInfo("@Test: {}", result.getName() + " was SKIPPED");
        logSeparator();

        //ExtentReports log operation for skipped tests.
        //getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logInfo("@Test: {}", result.getName() + " has FAILED but it is within defined success ratio");
        logSeparator();
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logInfo("@Test: {}", result.getName() + " has FAILED due to timeout");
        logSeparator();

    }

    @Override
    public void onStart(ITestContext context) {
        logInfo("Starting suite");
        logInfo("I am in onStart method from suite entitled " + context.getName());
        logSeparatorSpaced();
        //context.setAttribute("WebDriver", this.driver);

        // Selenium Configuration
        BrowserDriverHelper.loadDriver();
    }

    @Override
    public void onFinish(ITestContext context) {
        logInfo("Finishing suite");
        logInfo("I am in onFinish method from suite entitled " + context.getName());
        logSeparatorSpaced();
        //Do tier down operations for ExtentReports reporting!
        //getExtentReports().flush();

        // Selenium Configuration
        BrowserDriverHelper.closeBrowser();
    }
}
