package utils;

import helpers.BrowserDriverHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import helpers.LoggerHelper;
import org.testng.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static helpers.BrowserDriverHelper.remoteDriver;

public class ProjectListener extends LoggerHelper implements ITestListener {

    // private final Logger log = LoggerFactory.getLogger(ProjectListener.class); // if you don't use extends
    public String screenshotName;

    @Override
    public void onTestStart(ITestResult result) {

        currentTestName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = currentTestName + "_" + timestamp + "_report.txt";
        String path = "Reports/" + fileName;

        logInfo("@Test: {}", result.getName());
        logInfo("Description: {}", result.getMethod().getDescription());
        logSeparator();

        createReport(path);
        logStep("Test case name: " + result.getName());
        logStep("Test case description: " + result.getMethod().getDescription());
        logBreak();
        logStep("Steps:");

        //result.setAttribute("WebDriver", this.driver1);

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String timestamp = new SimpleDateFormat("MMMM d_ yyyy HH-mm-ss").format(new Date());
        screenshotName =  timestamp + "_" + result.getName() + "_PASSED";

        logInfo("@Test: {}", result.getName() + " has PASSED");
        logSeparator();
        finalizeTest("Test Passed");
        takeASS(remoteDriver,screenshotName);

    }

    @Override
    public void onTestFailure(ITestResult result) {

        String timestamp = new SimpleDateFormat("MMMM d_ yyyy HH-mm-ss").format(new Date());
        screenshotName =  timestamp + "_" + result.getName() + "_FAILED";

        logInfo("@Test: {}", result.getName() + " has FAILED");
        logSeparator();
        finalizeTest("Test Failed");
        takeASS(remoteDriver,screenshotName);

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
        finalizeTest("Test Skipped");

        //ExtentReports log operation for skipped tests.
        //getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logInfo("@Test: {}", result.getName() + " has FAILED but it is within defined success ratio");
        logSeparator();
        finalizeTest("@Test: {}", result.getName() + " has FAILED but it is within defined success ratio");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logInfo("@Test: {}", result.getName() + " has FAILED due to timeout");
        logSeparator();
        finalizeTest("@Test: {}", result.getName() + " has FAILED due to timeout");

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
