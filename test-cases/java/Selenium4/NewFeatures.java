package Selenium4;

import com.google.common.net.MediaType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v113.emulation.Emulation;
import org.openqa.selenium.devtools.v113.log.Log;
import org.openqa.selenium.devtools.v113.performance.Performance;
import org.openqa.selenium.devtools.v113.performance.model.Metric;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public class NewFeatures {

    String host = "localhost";
    String port = "4443";

    @Test
    public void geoLocationTest() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), chromeOptions);
        driver = new Augmenter().augment(driver);

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        devTools.send(Emulation.setGeolocationOverride(Optional.of(52.5043),
                Optional.of(13.4501),
                Optional.of(1)));

        driver.get("https://my-location.org/");
//        driver.quit();
    }

    @Test
    public void overrideDevice(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
// iPhone 11 Pro dimensions
        devTools.send(Emulation.setDeviceMetricsOverride(375,
                812,
                50,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        driver.get("https://selenium.dev/");
        driver.quit();
    }

    @Test
    public void performanceMetrics(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> metricList = devTools.send(Performance.getMetrics());

        driver.get("https://google.com");
        driver.quit();

        for(Metric m : metricList) {
            System.out.println(m.getName() + " = " + m.getValue());
        }
    }

    @Test
    public void pauseAction(){ // When you want to pause in between actions
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebElement clickable = driver.findElement(By.id("clickable"));
        new Actions(driver)
                .moveToElement(clickable)
                .pause(Duration.ofSeconds(1))
                .clickAndHold()
                .pause(Duration.ofSeconds(1))
                .sendKeys("abc")
                .perform();

        ((ChromeDriver) driver).resetInputState();
    }

    @Test
    public void clickAndHold(){
        ChromeDriver driver = new ChromeDriver();
        WebElement clickable = driver.findElement(By.id("clickable"));
        new Actions(driver)
                .clickAndHold(clickable)
                .perform();
    }

    @Test
    public void clickAndRelease(){ // Left click
        ChromeDriver driver = new ChromeDriver();
        WebElement clickable = driver.findElement(By.id("click"));
        new Actions(driver)
                .click(clickable)
                .perform();
    }

    @Test
    public void rightClick(){ // Context click
        ChromeDriver driver = new ChromeDriver();
        WebElement clickable = driver.findElement(By.id("clickable"));
        new Actions(driver)
                .contextClick(clickable)
                .perform();
    }

    @Test
    public void doubleClick(){ // Context click
        ChromeDriver driver = new ChromeDriver();
        WebElement clickable = driver.findElement(By.id("clickable"));
        new Actions(driver)
                .doubleClick(clickable)
                .perform();
    }

    @Test
    public void moveToElement() { // Hover
        ChromeDriver driver = new ChromeDriver();
        WebElement hoverable = driver.findElement(By.id("hover"));
        new Actions(driver)
                .moveToElement(hoverable)
                .perform();
    }

    @Test
    public void dragAndDrop(){
        ChromeDriver driver = new ChromeDriver();
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));
        new Actions(driver)
                .dragAndDrop(draggable, droppable)
                .perform();
    }

    @Test
    public void scrollToElement(){
        ChromeDriver driver = new ChromeDriver();
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        new Actions(driver)
                .scrollToElement(iframe)
                .perform();
    }

    @Test
    public void scrollByGivenAmount(){
        ChromeDriver driver = new ChromeDriver();
        WebElement footer = driver.findElement(By.tagName("footer"));
        int deltaY = footer.getRect().y;
        new Actions(driver)
                .scrollByAmount(0, deltaY)
                .perform();
    }
    @Test
    public void scrollFromElementByGivenAmount(){
        ChromeDriver driver = new ChromeDriver();
        WebElement iframe = driver.findElement(By.tagName("iframe"));
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(iframe);
        new Actions(driver)
                .scrollFromOrigin(scrollOrigin, 0, 200)
                .perform();
    }

    @Test
    public void basicAuth(){
        ChromeDriver driver = new ChromeDriver();
        Predicate<URI> uriPredicate = uri -> uri.getHost().contains("the-internet.herokuapp.com"); // here goes the domain

        ((HasAuthentication) driver).register(uriPredicate, UsernameAndPassword.of("admin", "admin"));
        driver.get("https://the-internet.herokuapp.com/basic_auth");
    }

    @Test
    public void listenToConsoleLogs(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(),
                logEntry -> {
                    System.out.println("log: "+logEntry.getText());
                    System.out.println("level: "+logEntry.getLevel());
                });
        driver.get("http://the-internet.herokuapp.com/broken_images");
// Check the terminal output for the browser console messages.
        driver.quit();
    }

    @Test
    public void networkIntercept(){
        ChromeDriver driver = new ChromeDriver();
        NetworkInterceptor interceptor = new NetworkInterceptor(
                driver,
                Route.matching(req -> true)
                        .to(() -> req -> new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                                .setContent(utf8String("Creamy, delicious cheese!"))));

        driver.get("https://www.ole.com");

        String source = driver.getPageSource();

        assertThat(source).contains("delicious cheese!");
    }

}
