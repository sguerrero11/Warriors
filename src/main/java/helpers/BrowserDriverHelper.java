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

        // We initialize the Environment properties based on env_jason
        Env.setProperties();

        // Option A: Using the YML Helper

        /* We need to make a distinction between the Logger inputStreams to use the below code
        // Define config file
        String folderPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "config" + File.separator;
        String filePath = folderPath + "config.yaml";

        try {
            // Load the YML reader object
            YMLHelper yml = new YMLHelper(filePath);

            // Access the values directly from the nested LinkedHashMap using the helper method
            String browserDefault = YMLHelper.getYMLValue(yml.yamlData, "browsers.default");
            boolean isMobileEmulation = YMLHelper.getYMLValue(yml.yamlData, "browsers.mobile");

            if (browserDefault == "chrome") {
                if (isMobileEmulation) {
                    loadChromeDriver(true); //mobile emulation ON
                } else {
                    loadChromeDriver(false); //mobile emulation OFF
                }
            } else if (browserDefault == "firefox") {
                if (isMobileEmulation) {
                    loadFFDriver(true); //mobile emulation ON
                } else {
                    loadFFDriver(false); //mobile emulation OFF
                }
            } else if (browserDefault == "edge") {
                if (isMobileEmulation) {
                    loadEdgeDriver(true); //mobile emulation ON
                } else {
                    loadEdgeDriver(false); //mobile emulation OFF
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

 */

        // Option B: Using a local variable to choose the browser and mobile mode

        int option = 1; // Change this to switch browser

        switch (option) {
            case 1:
                loadChromeDriver(false); //mobile emulation OFF
                break;
            case 2:
                loadFFDriver(false);
                break;
            case 3:
                loadEdgeDriver(false);
                break;
            case 4:
                loadChromeDriver(true); //mobile emulation ON
                break;
            case 5:
                loadFFDriver(true);
                break;
            case 6:
                loadEdgeDriver(true);
                break;
        }

        // Option C: Using the Enum helper to choose the browser

    }

    /***
     * Load and Instantiate Chrome driver to the current one.
     */
    private static void loadChromeDriver(boolean isMobileEmulation) {

        try {

            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();

            // Using the Env helper to check the mobile mode

            if (Objects.equals(Env.get("global.mobile"),"true")) {
                Map<String,String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName","Nexus 5");
                options.setExperimentalOption("mobileEmulation",mobileEmulation);
            }

            // Using the Env helper to check the mobile mode

            if (Objects.equals(Env.get("global.runHeadless"),"true")) {
                options.addArguments("--headless"); // Enable headless mode
                options.addArguments("--disable-gpu"); // Disable GPU usage in headless mode
            }


            // Using the argument isMobileEmulation that's being passed with options A and B

//            if (isMobileEmulation) {
//                Map<String, Object> mobileEmulation = new HashMap<>();
//                mobileEmulation.put("deviceName", "iPhone X"); // Set the desired device name for mobile emulation.
//                options.setExperimentalOption("mobileEmulation", mobileEmulation);
//            }

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
