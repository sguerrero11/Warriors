package helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/***
 * Helper Class to Handle logs in execution time.
 */
public abstract class LoggerHelper {

    private final static Logger _logger = LoggerFactory.getLogger(LoggerHelper.class);
    public static BufferedWriter writer;

    /***
     * Write a warning message in stdout console.
     * @param message [String]
     */
    public static void logWarning(String message) {
        _logger.warn(message);
    }

    /**
     * @param message [String]
     */
    public static void logTrace(String message) {
        _logger.trace(message);
    }


    /***
     * Write an error message in stdout console.
     * @param message [String]
     */
    public static void logError(String message) {
        _logger.error(message);
    }

    public static void logError(Throwable errorMessage) {
        _logger.error(String.valueOf(errorMessage));
    }

    /***
     * Write an info message in stdout console.
     * @param message [String]
     */
    public static void logInfo(String message) {
        _logger.info(message);
    }

    public static void logInfo(Integer number) {
        _logger.info(String.valueOf(number));
    }

    public static void logInfo(Double number) {
        _logger.info(String.valueOf(number));
    }

    public static void logInfo(String message, String extendedMessage) {
        _logger.info(message, extendedMessage);
    }

    public static void logSeparator() {
        logInfo("######################################################################################");
    }

    public static void logSeparatorSpaced() {
        logInfo("# # # # # # # # # # # # # # # # # # # # # # # # # # # #");
    }


    // region Reports for QAs

    public void createReportForQAs(String path) {

        try {
            writer = new BufferedWriter(new FileWriter(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logBreak() {
        try {
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void logStep(String step) {
        String timestamp = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss").format(new Date());
        String formattedStep = "[" + timestamp + "] " + step;
//        logInfo(formattedStep); // optional if you want to see it in the console as well
        try {
            writer.write(formattedStep + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logAssertion(List<String> validation) {
        logBreak();
        logStep("Assertions:");
        for (String s : validation) {
            logStep(s);
        }

    }

    public void logStep(String step, String extendedMessage) {
        String timestamp = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss").format(new Date());
        String formattedStep = "[" + timestamp + "] " + step;
//        logInfo(formattedStep); // optional if you want to see it in the console as well
        try {
            writer.write(formattedStep + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void takeASS(RemoteWebDriver driver, String fileName) {
        // Take screenshot and save to a file
        File screenshotSourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + File.separator + "screenshots/" + fileName + "_screenshot.png";
        try {
            FileUtils.copyFile(screenshotSourceFile, new File(destinationFile));
//            System.out.println("Screenshot saved successfully.");
            logInfo("Screenshot saved successfully.");

        } catch (IOException e) {
//            System.out.println("Failed to save screenshot: " + e.getMessage());
            logInfo("Failed to save screenshot: " + e.getMessage());
        }
    }

    public void finalizeTest(String outcome) {
        try {
            logBreak();
            logStep("Outcome:");
            logStep(outcome);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalizeTest(String outcome, String extendedMessage) {
        try {
            logBreak();
            logStep("Outcome:");
            logStep(outcome);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // endregion


    // region Reports for squad (Extent Reports)


    public static ExtentReports getReporterObject() {

        // ExtentReports, ExtentSparkReporter
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = System.getProperty("user.dir") + File.separator + "reportsForTeam" + File.separator + timestamp + "_team_report.html";
        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);


        reporter.config().setReportName("Automation results");
        reporter.config().setDocumentTitle("Automation test results");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setEncoding("utf-8");

        extent.attachReporter(reporter);
        extent.setSystemInfo("Operating System", "Windows 11");
        return extent;

    }


    // endregion

}
