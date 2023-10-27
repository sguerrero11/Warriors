package FrontEnd.SauceDemo;

import designpattern.pageObjects.SauceDemoPage;
import designpattern.pom.BasePage;
import helpers.AssertionsList;
import org.testng.annotations.*;
import utils.DataProviders;
import utils.ProjectListener;

import java.io.IOException;


@Listeners({ProjectListener.class})
public class LoginWithDP extends BasePage {

    // region VARIABLES

    private SauceDemoPage index = new SauceDemoPage();


    // endregion


    // region BEFORE ANNOTATIONS
    @BeforeSuite
    public void setup() {

        // add code to run before suite
    }

    @BeforeClass
    public void beforeClass() {

        // add code to run before class
    }

    @BeforeTest
    public void beforeTest() {
        // add code to run before tests
    }

    // endregion

    // region TESTS

    @Test(description = "Trying to read data from an array object", dataProviderClass = DataProviders.class, dataProvider = "array", priority = 100, groups = {"Regression", "Smoke"})
    public void readFromArray(String username, String pwd) throws IOException {

        // region ARRANGE


        // endregion

        // region ACT
        index.load();
        index.userInput(username);
        logInfo("Testing user: " + username);
        index.pwdInput(pwd);
        logInfo("Testing password: " + pwd);
        index.loginClick();

        // endregion

        // region ASSERT

        if (username != "locked_out_user") {
            asserts.equalsSoft(index.getTitle(),"fasdfads") // forcing a soft fail // since it's soft, code will continue to run
                    .equals(index.getTitle(), "Swag Labs")
                    .equals(index.getProductsTitle(), "Products");

        } else {
            asserts.equals(index.getErrorMessage(), "Epic sadface: Sorry, this user has been locked out.");
        }

        // we call the below method if we want to fail the whole iteration when there's at least one failed soft assertion,
        // unless we use successPercentage attribute to bypass it (it's a wrong usage but it serves the purpose)
        // and thus the status will be "FAILED within success ratio" (we do this to see this message)
//        asserts.assertAll(); // if we comment this, iteration will always pass even though there's a soft fail (assertion will be marked as failed in the report)
        // usually we wouldn't use it, so we can pass the test

        // endregion

    }

    @Test(description = "Trying to read data from an Excel file", dataProviderClass = DataProviders.class, dataProvider = "excel", priority = 300, groups = {"Regression", "Smoke"})
    public void readFromFileExcel(String username, String pwd) throws IOException {

        // region ARRANGE

        // endregion

        // region ACT
        index.load();
        index.userInput(username);
        logInfo("Testing user: " + username);
        index.pwdInput(pwd);
        logInfo("Testing password: " + pwd);
        index.loginClick();

        // endregion

        // region ASSERT


        // endregion

    }

    @Test(description = "Trying to read data from a CSV-like file", dataProviderClass = DataProviders.class, dataProvider = "csv", priority = 400, groups = {"Regression", "Smoke"})
    public void readFromFileCSVLike(String username, String pwd) throws IOException {

        // region ARRANGE

        // endregion

        // region ACT
        index.load();
        index.userInput(username);
        logInfo("Testing user: " + username);
        index.pwdInput(pwd);
        logInfo("Testing password: " + pwd);
        index.loginClick();

        // endregion

        // region ASSERT


        // endregion

    }

    // endregion

}
