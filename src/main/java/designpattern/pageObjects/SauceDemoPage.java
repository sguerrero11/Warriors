package designpattern.pageObjects;

import designpattern.pom.BasePage;
import designpattern.pom.DefaultPage;
import org.openqa.selenium.By;

public class SauceDemoPage extends BasePage implements DefaultPage {

    // region SELECTORS

    final By userField = By.id("user-name");
    final By pwdField = By.id("password");
    final By loginButton = By.id("login-button");
    final By swagLabsLogo = By.className("app_logo");
    final By productsTitle = By.className("title");
    final By errorButton = By.xpath("//*[contains(text(),\"sadface\")]");

    // endregion

    // region LOAD

    @SafeVarargs // is like @SuppressWarnings("what you want to suppress" e.g. "deprecation", "unchecked", "unused", etc)
    @Override
    public final <T> String getUrl(T... values) {

        return "http:/www.saucedemo.com";
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

    public void userInput(String text){
        waitForElementVisible(userField);
        sendKeys(text,userField);
    }

    public void pwdInput(String text){
        waitForElementVisible(pwdField);
        sendKeys(text,pwdField);
    }

    public void loginClick(){
        waitForElementVisible(loginButton);
        click(loginButton);

    }

    public String getTitle(){
        return getText(swagLabsLogo);
    }

    public String getProductsTitle(){
        return getText(productsTitle);
    }

    public String getErrorMessage(){
        waitForElementVisible(errorButton);
        return getText(errorButton);
    }

    // endregion
}

