package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import helpers.BrowserDriverHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import helpers.LoggerHelper;
import org.testng.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static helpers.BrowserDriverHelper.remoteDriver;

public class ProjectListener extends LoggerHelper implements ITestListener {

    // private final Logger log = LoggerFactory.getLogger(ProjectListener.class); // if you don't use extends
    public String screenshotName;
    public String currentTestName;
    ExtentReports extent = LoggerHelper.getReporterObject();
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {

        currentTestName = result.getMethod().getMethodName();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = currentTestName + "_" + timestamp + "_report.txt";
        String path = System.getProperty("user.dir") + File.separator + "reportsForManualQA" + File.separator + fileName; // folder needs to exist

        logInfo("@Test: {}", result.getName());
        logInfo("Description: {}", result.getMethod().getDescription());
        logSeparator();

        createReportForQAs(path);
        logStep("Test case name: " + result.getName());
        logStep("Test case description: " + result.getMethod().getDescription());
        logBreak();
        logStep("Steps:");

        //result.setAttribute("WebDriver", this.driver1);

        //Creating Extent Report
        test = extent.createTest(currentTestName)
                .assignAuthor("Santiago Guerrero");

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String timestamp = new SimpleDateFormat("MMMM d_ yyyy HH-mm-ss").format(new Date());
        screenshotName =  timestamp + "_" + result.getName() + "_PASSED";

        logInfo("@Test: {}", result.getName() + " has PASSED");
        logSeparator();
        finalizeTest("Test Passed");
        takeSS(remoteDriver,screenshotName);
        test.log(Status.PASS, "Test successfully passed")
                .addScreenCaptureFromPath(System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + screenshotName + "_screenshot.png");


    }

    @Override
    public void onTestFailure(ITestResult result) {

        String timestamp = new SimpleDateFormat("MMMM d_ yyyy HH-mm-ss").format(new Date());
        screenshotName =  timestamp + "_" + result.getName() + "_FAILED";

        logInfo("@Test: {}", result.getName() + " has FAILED");
        logSeparator();
        finalizeTest("Test Failed. The following error was found: \n\n" + result.getThrowable());
        takeSS(remoteDriver,screenshotName);
        test.log(Status.FAIL, "Test failed: " + result.getThrowable())
                .addScreenCaptureFromPath(System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + screenshotName + "_screenshot.png")
                .fail(MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + screenshotName + "_screenshot.png").build());

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

        //Do tier down operations for ExtentReports reporting
        test.assignCategory(context.getName());
        extent.flush();

        // Selenium Configuration
        BrowserDriverHelper.closeBrowser();

    }
}
