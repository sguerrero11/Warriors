package FrontEnd.WebDriverUniversity;

import designpattern.pageObjects.WebDriverUniversityPage;
import helpers.LoggerHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import static helpers.BrowserDriverHelper.remoteDriver;
import static org.testng.Assert.assertEquals;

@Listeners({ProjectListener.class})
public class LoadContactUsForm extends LoggerHelper {

    private WebDriverUniversityPage index;

    @BeforeClass (description= "Code to run before class starts", alwaysRun = true)
    public void setup() {

        index = new WebDriverUniversityPage();
    }

    @Test(enabled=true, description = "Open Contact Us form from WebDriverUniversity and verify if the page loads correctly", priority = 100, groups = {"Regression"})
    public void openContactUs() throws InterruptedException {

        // region ARRANGE

        // endregion

        // region ACT
        index.load();
        index.contactUsTitleClick();
        // endregion

        // region ASSERT

        assertEquals(index.getSubmitButton(), "SUBMIT");
        assertEquals(index.getContactUsFormTitleText(), "CONTACT US");

        // endregion
    }

    @Test(description = "Test 2 example", priority = 200, groups = {"Regression", "Smoke"})
    public void testTwo () {
        System.out.println("Test 2");

        assertEquals("one","two");
    }

    @Test(description = "Test 3 example", priority = 300, groups = {"Regression"})
    public void testThree () {
        System.out.println("Test 3");
    }

    @Test(description = "Test 4 example", priority = 400, groups = {"Regression", "Skipped", "Smoke"})
    public void testFour () {
        System.out.println("Test 4");
    }

    @Test(description = "Test fluent wait", priority = 500, groups = {"Smoke"})
    public void testFluentWait () throws InterruptedException {
        index.load();
        index.contactUsTitleClick();
        index.fillContactForm();



    }

}
