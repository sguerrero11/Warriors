package FrontEnd;

import designpattern.pageObjects.WebDriverUniversityPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import static org.testng.Assert.assertEquals;

@Listeners({ProjectListener.class})
public class LoadContactUsForm {

    private WebDriverUniversityPage index;

    @BeforeSuite
    public void beforeClass() {

        index = new WebDriverUniversityPage();
    }

    @Test(enabled=true, description = "Open Contact Us Form", priority = 100, groups = {"Regression"})
    public void openContactUs() throws InterruptedException {

        // region ARRANGE

        // endregion

        // region ACT
        index.load();
        index.contactUsTitleClick();
        // endregion

        // region ASSERT

        assertEquals(index.getSubmitButton(), "SUBMIT");
    //    assertEquals(index.getContactUsFormTitle(), "CONTACT US");
        // endregion
    }

    @Test(description = "Test 2 example", priority = 200, groups = {"Regression", "Smoke"})
    public void testTwo () {
        System.out.println("Test 2");
    }

    @Test(description = "Test 3 example", priority = 300, groups = {"Regression"})
    public void testThree () {
        System.out.println("Test 3");
    }
}
