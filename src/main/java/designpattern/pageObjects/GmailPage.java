package designpattern.pageObjects;

import designpattern.pom.BasePage;
import designpattern.pom.DefaultPage;
import org.openqa.selenium.By;

public class GmailPage extends BasePage implements DefaultPage {

    //region selectors
    final By nameInput = By.xpath("//input[@name='wpforms[fields][0]']");
    final By loginNextButton = By.xpath("(//span[@jsname='V67aGc'])[2]");
    final By pwdNextButton = By.xpath("(//span[@jsname='V67aGc'])[2]");
    final By sendButton = By.xpath("//button[@name='wpforms[submit]']");
    final By userField = By.id("identifierId");
    final By pwdField = By.xpath("//div[@class='Xb9hP']/input[@name='Passwd']");
    final By searchMailInput = By.xpath("//input[@placeholder='Search mail']");
    final By signInButton = By.xpath("//a[@data-action='sign in']");


    //endregion

    //region load
    @SafeVarargs // is like @SuppressWarnings("what you want to suppress" e.g. "deprecation", "unchecked", "unused", etc)
    @Override
    public final <T> String getUrl(T... values) {
        return "https://www.google.com/gmail/about/";
    }

    @Override
    public <T> void load(T... values) {
        visit(getUrl(values));
    }


    @Override
    public boolean validateField(String element, String attribute, String value) {
        return false;
    }

    //endregion

    //region methods
    public String getButtonText() {
        waitForElementVisible(nameInput);
        return getText(sendButton);
    }

    public String getPwdNextButtonText() {
        waitForElementVisible(pwdNextButton);
        return getText(pwdNextButton);
    }

    public String getSearchMailInputAttribute() {
        waitForElementVisible(searchMailInput);
        return getAttribute(searchMailInput, "aria-label");
    }


    public void writeForm() throws InterruptedException {
        waitForElementVisible(nameInput);
        sendKeys("Fede", nameInput);
        //click(sendButton);
    }

    public void signInButtonClick() throws InterruptedException {
        click(signInButton);
    }

    public void enterMailAndClickNext(String userData) throws InterruptedException {
        waitForElementVisible(loginNextButton);
        sendKeys(userData, userField);
        click(loginNextButton);
    }

    public void enterPwdAndClickNext(String pwdData) throws InterruptedException {
        waitForElementVisible(pwdNextButton);
        sendKeys(pwdData, pwdField);
        click(pwdNextButton);
    }

    //endregion
}
