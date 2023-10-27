package helpers;


import designpattern.pom.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AssertionsList extends BasePage {
    public static List<String> assertions = new ArrayList<>();;
    public static SoftAssert softAssert = new SoftAssert();

    public AssertionsList equals(String actual, String expected) {
        boolean result = expected.equals(actual);
        String description = String.format("Assert if argument \"%s\" is equal to argument \"%s\"", expected, actual);
        addAssertion(description, result);

        assertEquals(actual, expected);
        return this;
    }
    public AssertionsList equals(Boolean actual, Boolean expected) {
        boolean result = expected.equals(actual);
        String description = String.format("Assert if argument \"%s\" is equal to argument \"%s\"", expected, actual);
        addAssertion(description, result);

        assertEquals(actual, expected);
        return this;
    }

    public AssertionsList equalsSoft(String actual, String expected) {
        boolean result = expected.equals(actual);
        String description = String.format("Softly assert if argument \"%s\" is equal to argument \"%s\"", expected, actual);
        addAssertion(description, result);

        softAssert.assertEquals(actual, expected);

        return this;
    }

    public AssertionsList assertAll() {
        softAssert.assertAll(); // we call this method to throw the error, otherwise it won't fail

        return this;
    }

    public AssertionsList isPresent(By locator) {
        boolean result = isDisplayed(locator);
        String description = String.format("Assert if locator \"%s\" is present", locator);
        addAssertion(description, result);

        assertTrue(BrowserDriverHelper.getDriver().findElement(locator).isDisplayed());
        return this;
    }

    public AssertionsList checkboxStatus(By locator) {
        boolean result = BrowserDriverHelper.getDriver().findElement(locator).isSelected();

        String description = String.format("Assert if checkbox \"%s\" is selected", locator);
        addAssertion(description, result);

        assertTrue(BrowserDriverHelper.getDriver().findElement(locator).isSelected());

        return this;
    }

    public AssertionsList elementState(By locator) {
        boolean result = BrowserDriverHelper.getDriver().findElement(locator).isEnabled();

        String description = String.format("Assert if element \"%s\" is enabled", locator);
        addAssertion(description, result);

        assertTrue(BrowserDriverHelper.getDriver().findElement(locator).isEnabled());

        return this;
    }

    public AssertionsList dropdownOptionPresent(By locator, String expectedOption) {

        Select dropdown = new Select(BrowserDriverHelper.getDriver().findElement(locator));

        boolean isOptionPresent = false;
        for (WebElement option : dropdown.getOptions()) {
            if (option.getText().equals(expectedOption)) {
                isOptionPresent = true;
                break;
            }
        }

        boolean result = isOptionPresent;

        String description = String.format("Assert if option \"%s\" is present in dropdown list", expectedOption);
        addAssertion(description, result);

        assertTrue(isOptionPresent);

        return this;
    }


    private void addAssertion(String description, boolean result) {
        String resultText = result ? "PASSED" : "FAILED";
        assertions.add(description + " - " + resultText);
    }

    public void printAssertions() {
        for (String assertion : assertions) {
            System.out.println(assertion);
        }
    }
}
