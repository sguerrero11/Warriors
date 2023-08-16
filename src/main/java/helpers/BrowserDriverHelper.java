package helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/***
 * Helper class to handle Selenium WebDriver.
 */
public abstract class BrowserDriverHelper extends LoggerHelper {
    public static WebDriver remoteDriver;

    /***
     * Load the current driver with one of the different available options
     */
    public static void loadDriver() {
        closeBrowser();

        // We initialize the Environment properties based on env_jason, when using option C
//        EnvHelper.setProperties();

        // Option A: Using the YML Helper

        try {
            // Load the YML reader object
            YMLHelper.setProperties();

            // Access the values directly from the nested LinkedHashMap using the helper method
            String browserDefault = YMLHelper.getYMLValue(YMLHelper.yamlData, "browsers.default");

/*
You can define mobile mode here and pass the variable when loading the driver,
or you can analyze it inside each driver as with the Env helper
 */
            boolean isMobileEmulation = YMLHelper.getYMLValue(YMLHelper.yamlData, "browsers.mobile"); // this can be removed if using the simplified way


            if (browserDefault.equals("chrome")) {
                loadChromeDriver(Objects.equals(YMLHelper.getYMLValue(YMLHelper.yamlData, "browsers.mobile"), true)); // this simplifies the if and else inside each driver
            } else if (browserDefault.equals("firefox")) {
                if (isMobileEmulation) {
                    loadFFDriver(true); //mobile emulation ON
                } else {
                    loadFFDriver(false); //mobile emulation OFF
                }
            } else if (browserDefault.equals("edge")) {
                if (isMobileEmulation) {
                    loadEdgeDriver(true); //mobile emulation ON
                } else {
                    loadEdgeDriver(false); //mobile emulation OFF
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Option B: Using a local variable to choose the browser and mobile mode
//
//        int option = 1; // Change this to switch browser
//
//        switch (option) {
//            case 1:
//                loadChromeDriver(false); //mobile emulation OFF
//                break;
//            case 2:
//                loadFFDriver(false);
//                break;
//            case 3:
//                loadEdgeDriver(false);
//                break;
//            case 4:
//                loadChromeDriver(true); //mobile emulation ON
//                break;
//            case 5:
//                loadFFDriver(true);
//                break;
//            case 6:
//                loadEdgeDriver(true);
//                break;
//        }

        // Option C: Using the Env helper to choose the browser

//        if (Objects.equals(EnvHelper.get("global.browser"),"chrome")) {
//            loadChromeDriver(Objects.equals(EnvHelper.get("global.mobile"),"true")); // another way of passing isMobileEmulation as true or false
//        }
//        else if (Objects.equals(EnvHelper.get("global.browser"),"firefox")) {
//            loadFFDriver(Objects.equals(EnvHelper.get("global.mobile"),"true")); // another way of passing isMobileEmulation as true or false
//        }
//
//        else if (Objects.equals(EnvHelper.get("global.browser"),"edge")) {
//            loadEdgeDriver(Objects.equals(EnvHelper.get("global.mobile"),"true")); // another way of passing isMobileEmulation as true or false
//        }

    }

    /**
     * Load and Instantiate Chrome driver to the current one.
     *
     * @param isMobileEmulation can be removed entirely and define its value inside with both helpers
     */
    private static void loadChromeDriver(boolean isMobileEmulation) {

        try {

            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();

//            // Option A: Using the Env helper to check the mobile mode
//
//            if (Objects.equals(EnvHelper.get("global.mobile"),"true")) {
//                Map<String,String> mobileEmulation = new HashMap<>();
//                mobileEmulation.put("deviceName","Nexus 5");
//                options.setExperimentalOption("mobileEmulation",mobileEmulation);
//            }
//
//            // Option B: Using the Env helper to check the mobile mode
//
//            if (Objects.equals(EnvHelper.get("global.runHeadless"),"true")) {
//                options.addArguments("--headless"); // Enable headless mode
//                options.addArguments("--disable-gpu"); // Disable GPU usage in headless mode
//            }


            // Option C: Using the argument isMobileEmulation that's being passed with options A and B from loadDriver

            if (isMobileEmulation) {
                Map<String, Object> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone X"); // Set the desired device name for mobile emulation.
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
            }

            String host = "localhost";
            String port = "4444";
            LoggerHelper.logInfo("[BrowserDriver/loadChromeDriver] Connecting to: " + "http://" + host + ":" + port + "/wd/hub");
            setDriver(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), options));
        } catch (Exception ex) {
            LoggerHelper.logError("[BrowserDriver/loadChromeDriver] Error loading Selenium Driver: " + ex.getMessage());
        }
    }

    /***
     * Load and Instantiate FF driver to the current one.
     * @param isMobileEmulation can be removed entirely and define its value inside
     */
    private static void loadFFDriver(boolean isMobileEmulation) {
        try {

            FirefoxOptions options = new FirefoxOptions();
            WebDriverManager.firefoxdriver().setup();

            if (isMobileEmulation) {
                Map<String, Object> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone X"); // Set the desired device name for mobile emulation.
                options.addPreference("devtools.responsiveUI.presets", "{'iPhone X': {'width': 375, 'height': 812, 'deviceScaleFactor': 3}}"); // Set specific device properties.
            }

            String host = "localhost";
            String port = "4444";

            LoggerHelper.logInfo("[BrowserDriver/loadChromeDriver] Connecting to: " + "http://" + host + ":" + port + "/wd/hub");
            setDriver(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), options));
        } catch (Exception ex) {
            LoggerHelper.logError("[BrowserDriver/loadChromeDriver] Error loading Selenium Driver: " + ex.getMessage());
        }
    }

    /**
     * Load and Instantiate Edge driver to the current one.
     *
     * @param isMobileEmulation can be removed entirely and define its value inside
     */
    private static void loadEdgeDriver(boolean isMobileEmulation) {
        try {

            EdgeOptions options = new EdgeOptions();
            WebDriverManager.edgedriver().setup();

            if (isMobileEmulation) {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone X"); // Set the desired device name for mobile emulation.
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
            }

            String host = "localhost";
            String port = "4444";
            LoggerHelper.logInfo("[BrowserDriver/loadChromeDriver] Connecting to: " + "http://" + host + ":" + port + "/wd/hub");
            setDriver(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), options));
        } catch (Exception ex) {
            LoggerHelper.logError("[BrowserDriver/loadChromeDriver] Error loading Selenium Driver: " + ex.getMessage());
        }
    }

    /***
     * Set new remote driver to the current one.
     * @param newRemoteDriver [RemoteWebDriver]
     */
    public static void setDriver(RemoteWebDriver newRemoteDriver) {
        remoteDriver = newRemoteDriver;
        ((RemoteWebDriver) remoteDriver).setFileDetector(new LocalFileDetector());
//        remoteDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // uncomment if necessary only
//        remoteDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5)); // uncomment if necessary only
//        remoteDriver.manage().deleteAllCookies(); // uncomment if necessary only
        remoteDriver.manage().window().setSize(new Dimension(1300, 1024));

    }

    /***
     * Get the current driver
     * @return [WebDriver]
     */
    public static RemoteWebDriver getDriver() {
        return (RemoteWebDriver) remoteDriver;
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
