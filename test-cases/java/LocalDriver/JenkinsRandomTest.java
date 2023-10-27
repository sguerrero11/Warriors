package LocalDriver;

import designpattern.pom.BasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import static helpers.LoggerHelper.asserts;

@Listeners({ProjectListener.class})

public class JenkinsRandomTest extends BasePage {

    public static ChromeDriver driver1;
    private static boolean headless = true;

    private static boolean tearMode = false;

    // regionSAUCEDEMO LOCATORS

    private final By userField = By.id("user-name");
    private final By pwdField = By.id("password");
    private final By loginButton = By.id("login-button");

    //endregion

    @BeforeMethod
    public void setUpBrowser() throws InterruptedException {


        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions(); // adding this and below since I have 2 locations
        opt.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
//        opt.setCapability("version",114);
        // If we want to run headless
        if (headless) {
            opt.addArguments("--headless"); // Enable headless mode
//      options.addArguments("--disable-gpu"); // Disable GPU usage in headless mode
        }
        driver1 = new ChromeDriver(opt);
        String baseURL = "https://www.saucedemo.com";

        driver1.get(baseURL);
        driver1.manage().window().maximize();
    }

    @Test(description = "Log in successfully to SauceDemo") //
    public void loginToSD() {

        // We sign in

        driver1.findElement(userField).sendKeys("standard_user");
        driver1.findElement(pwdField).sendKeys("secret_sauce");
        driver1.findElement(loginButton).click();

        // We assert if Products menu is displayed

        asserts.equals(true, driver1.findElement(By.xpath("//*[contains(text(),'Products')]")).isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        if (tearMode) {
            driver1.quit();
        }
    }
}
