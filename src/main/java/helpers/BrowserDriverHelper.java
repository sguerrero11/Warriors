package helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

/***
 * Helper class to handle Selenium WebDriver.
 */
public abstract class BrowserDriverHelper extends LoggerHelper {
    private static RemoteWebDriver remoteDriver;

    /***
     * Load the current driver with one of the different available options
     */
    public static void loadDriver() {
        closeBrowser();

        int option = 1; // Change this to switch browser

        switch (option) {
            case 1: loadChromeDriver();
                break;
            case 2: loadFFDriver();
                break;
            case 3: loadEdgeDriver();
                break;
        }


    }

    /***
     * Load and Instantiate Chrome driver to the current one.
     */
    private static void loadChromeDriver() {
        try {
            ChromeOptions options = new ChromeOptions();

            WebDriverManager.chromedriver().setup();


            String host = "localhost";
            String port = "4444";
            LoggerHelper.logInfo("[BrowserDriver/loadChromeDriver] Connecting to: " + "http://" + host + ":" + port + "/wd/hub");
            setDriver(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), options));
        } catch(Exception ex) {
            LoggerHelper.logError("[BrowserDriver/loadChromeDriver] Error loading Selenium Driver: " + ex.getMessage());
        }
    }

    /***
     * Load and Instantiate FF driver to the current one.
     */
    private static void loadFFDriver() {
        try {
            FirefoxOptions options = new FirefoxOptions();

            WebDriverManager.firefoxdriver().setup();


            String host = "localhost";
            String port = "4444";
            LoggerHelper.logInfo("[BrowserDriver/loadChromeDriver] Connecting to: " + "http://" + host + ":" + port + "/wd/hub");
            setDriver(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), options));
        } catch(Exception ex) {
            LoggerHelper.logError("[BrowserDriver/loadChromeDriver] Error loading Selenium Driver: " + ex.getMessage());
        }
    }

    private static void loadEdgeDriver() {
        try {
            EdgeOptions options = new EdgeOptions();

            WebDriverManager.edgedriver().setup();


            String host = "localhost";
            String port = "4444";
            LoggerHelper.logInfo("[BrowserDriver/loadChromeDriver] Connecting to: " + "http://" + host + ":" + port + "/wd/hub");
            setDriver(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), options));
        } catch(Exception ex) {
            LoggerHelper.logError("[BrowserDriver/loadChromeDriver] Error loading Selenium Driver: " + ex.getMessage());
        }
    }

    /***
     * Set new remote driver to the current one.
     * @param newRemoteDriver [RemoteWebDriver]
     */
    public static void setDriver(RemoteWebDriver newRemoteDriver){
        remoteDriver = newRemoteDriver;
        remoteDriver.setFileDetector(new LocalFileDetector());
        remoteDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        remoteDriver.manage().window().setSize(new Dimension(1300,1024));
    }

    /***
     * Get the current driver
     * @return [WebDriver]
     */
    public static RemoteWebDriver getDriver() {
        return remoteDriver;
    }

    /***
     * Close the current tab and goes to the first
     */
    public static void closeActiveTab() {
        ArrayList<String> tabs = new ArrayList<>(remoteDriver.getWindowHandles());
        remoteDriver.switchTo().window(tabs.get(1));
        remoteDriver.close();
        remoteDriver.switchTo().window(tabs.get(0));
    }

    /***
     * Close the Browser Driver
     */
    public static void closeBrowser() {
        if (remoteDriver != null) {
            remoteDriver.quit();
        }
    }
}
