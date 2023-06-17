import designpattern.pageObjects.WebDriverUniversityPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.BeforeAndAfterTestListener;

import static org.testng.Assert.assertEquals;

@Listeners({BeforeAndAfterTestListener.class})
public class loadContactUsForm {

    private WebDriverUniversityPage index;

    @BeforeClass(groups = {"Regression"})
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

   // @Test(enabled=true, description = "DevSkiller", priority = 100, groups = {"Regression"})

}
