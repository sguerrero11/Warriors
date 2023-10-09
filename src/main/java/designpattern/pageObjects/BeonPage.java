package designpattern.pageObjects;

import designpattern.pom.BasePage;
import designpattern.pom.DefaultPage;
import org.openqa.selenium.By;

public class BeonPage extends BasePage implements DefaultPage {

    // region SELECTORS


    private final By searchField = By.className("gLFyf");


    // endregion

    // region LOAD

    @SafeVarargs // is like @SuppressWarnings("what you want to suppress" e.g. "deprecation", "unchecked", "unused", etc)
    @Override
    public final <T> String getUrl(T... values) {
        return "http:/www.google.com";
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



    public void kwInput (String text){
        waitForElementVisible(searchField);
        waitUntilFieldIsPopulated(searchField);
        sendEnterKey(text,searchField);
    }


    // endregion

}
