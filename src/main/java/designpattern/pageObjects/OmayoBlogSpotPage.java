package designpattern.pageObjects;

import designpattern.pom.BasePage;
import designpattern.pom.DefaultPage;
import org.openqa.selenium.By;

public class OmayoBlogSpotPage extends BasePage implements DefaultPage {

    // region SELECTORS

    final By uploadFileButton = By.id("uploadfile");


    // endregion

    // region LOAD

    @SafeVarargs // is like @SuppressWarnings("what you want to suppress" e.g. "deprecation", "unchecked", "unused", etc)

    @Override
    public final <T> String getUrl(T... values) {

        return "https://omayo.blogspot.com/";
    }

    @Override
    public <T> void load(T... values) {
        visit(getUrl(values));
    }

    @Override
    public boolean validateField(String element, String attribute, String value) {
        return false;
    }

    // endregion

    // region METHODS

    public void uploadFile(String filePath){
        sendKeys(filePath,uploadFileButton);
    }


    // endregion
}
