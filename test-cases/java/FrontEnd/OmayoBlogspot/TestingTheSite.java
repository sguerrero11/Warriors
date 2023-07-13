package FrontEnd.OmayoBlogspot;

import designpattern.pageObjects.OmayoBlogSpotPage;
import designpattern.pageObjects.SauceDemoPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

@Listeners({ProjectListener.class})
public class TestingTheSite {

    private OmayoBlogSpotPage index = new OmayoBlogSpotPage();


    @Test(description="Testing uploading file without clicking on the upload file button ")
    public void uploadingAFile() throws InterruptedException {

        // region ARRANGE
        String folderPath = "test-data-files/";
        String filePath = "example.csv";
        // endregion

        // region ACT
        index.load();
        index.uploadFile(folderPath+filePath);
        // click on the submit button

        // endregion

        // region ASSERTS

        // once the file is uploaded, you can search for the file like this:

        //        Assert.assertNotNull(driver.findElement(By.xpath("//*[contains(text(), 'name_of_the_file')]")));
        //endregion




    }

}
