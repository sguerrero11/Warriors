package FrontEnd.Beon;

import designpattern.pageObjects.BeonPage;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import static helpers.LoggerHelper.asserts;

@Listeners({ProjectListener.class})

public class UITestAssessment  {

    private BeonPage index = new BeonPage();

    @Test()
    public void searchAKeyword(){

        // region ARRANGE
        String kwForSearch = "messi";


        // endregion

        // region ACT

        // We load the page
        index.load();

        // We close the cookies acceptance window
        index.findElement(By.xpath("//*[contains(@id,'W0wltc')]")).click();

        // We search for a specific keyword defined above
        index.kwInput(kwForSearch);



        // endregion


        // region ASSERT

        asserts.equals("test","test");

        // endregion


    }
}
