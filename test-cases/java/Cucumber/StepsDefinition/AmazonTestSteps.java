package Cucumber.StepsDefinition;

import helpers.LoggerHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class AmazonTestSteps {

    WebDriver driver;

    @Given("User navigates to url {string}")
    public void userNavigatesToUrl(String url){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }

    @When("User inputs text {string} on {string}")
    public void userInputsText(String text, String identifier){

        WebElement element = driver.findElement(By.xpath(identifier));
        element.sendKeys(text);
    }

    @When("User clicks on {string}")
    public void userClicks(String identifier){

        WebElement element = driver.findElement(By.xpath(identifier));
        element.click();
    }

    @Then("User verifies value of {string} is {string}")
    public void userVerifiesText(String identifier, String expectedVal){

        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identifier)));

        WebElement element = driver.findElement(By.xpath(identifier));
        assertEquals(element.getText(),expectedVal);
    }

    @Then("User closes the browser")
    public void userCloses(){
        LoggerHelper.logInfo("Test case has PASSED");
        driver.close();
    }



}

/*
The steps definition will be used by the runner, based on the features file
 */