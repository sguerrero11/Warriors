package designpattern.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import designpattern.pom.DefaultPage;
import designpattern.pom.BasePage;

public class WebDriverUniversityPage extends BasePage implements DefaultPage {

    // region SELECTORS

    final By contactUsTitle = By.xpath("//div[@class='section-title']/h1[text()='CONTACT US']"); // text() es igual a .
    final By submitButton = By.xpath("//*[@type='submit']");
    final By contactUsFormTitle = By.xpath("//*[@id=\"contact_me\"]/div/div[1]/div/h2");



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

    public void contactUsTitleClick() throws  InterruptedException{
        click(contactUsTitle);
    }

    public String getSubmitButton() {
        switchToTabWithTitle("Contact Us");
        waitForElementVisible(submitButton);
        return getValue(submitButton);
    }

    public String getContactUsFormTitle() {
        waitForElementVisible(contactUsFormTitle);
        return getText(contactUsFormTitle);
    }

    public void changeToControlledTab (){

    }

    // endregion
}
