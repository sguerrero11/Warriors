package designpattern.pageObjects;

import org.openqa.selenium.By;
import designpattern.pom.DefaultPage;
import designpattern.pom.BasePage;

public class WebDriverUniversityPage extends BasePage implements DefaultPage {

    // region SELECTORS

    final By contactUsTitle = By.xpath("//h1[text()='CONTACT US']"); // text() es igual a .
    final By submitButton = By.xpath("//*[@type='submit']");
    final By contactUsFormTitle = By.xpath("//*[@name='contactme']"); // same as "//*[@name=\"contactme\"]"
    final By fieldFirstName = By.xpath("//*[@name='first_name']");
    final By fieldLastName = By.xpath("//*[@name='last_name']");
    final By fieldEmail = By.xpath("//*[@name='email']");
    final By fieldMessage = By.xpath("//*[@name='message']");


    // endregion

    // region LOAD

    @Override
    public final <T> String getUrl(T... values) {

        return "http://webdriveruniversity.com/index.html";
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

    public void contactUsTitleClick() throws InterruptedException {
        click(contactUsTitle);
        switchToTabWithTitle("Contact Us");
    }

    public String getSubmitButton() {
        waitForElementVisible(submitButton);
        return getValue(submitButton);
    }

    public String getContactUsFormTitleText() {
        waitForElementVisible(contactUsFormTitle);
        return getText(contactUsFormTitle);
    }

    public void changeToControlledTab() {

    }

    public void fillContactForm() {
        sendKeys("John", fieldFirstName);
        waitUntilFieldIsPopulated(fieldFirstName);
        sendKeys("Doe", fieldLastName);
        waitUntilFieldIsPopulated(fieldLastName);
        sendKeys("johndoe@gmail.com", fieldEmail);
        waitUntilFieldIsPopulated(fieldEmail);
        sendKeys("Hey man", fieldMessage);
        waitUntilFieldIsPopulated(fieldMessage);
    }

    // endregion
}