package FrontEnd.WebDriverUniversity;

import com.deque.html.axecore.args.AxeRunOptions;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import designpattern.pageObjects.WebDriverUniversityPage;
import helpers.LoggerHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static helpers.BrowserDriverHelper.remoteDriver;
import static org.testng.Assert.assertEquals;

@Listeners({ProjectListener.class})
public class LoadContactUsForm extends LoggerHelper {

    private WebDriverUniversityPage index;

    @BeforeClass(description = "Code to run before class starts", alwaysRun = true)
    public void setup() {

        index = new WebDriverUniversityPage();
    }

    @Test(enabled = true, description = "Open Contact Us form from WebDriverUniversity and verify if the page loads correctly", priority = 100, groups = {"Regression"})
    public void openContactUs() throws InterruptedException {

        // region ARRANGE

        // endregion

        // region ACT
        index.load();
        index.contactUsTitleClick();
        // endregion

        // region ASSERT

        assertEquals(index.getSubmitButton(), "SUBMIT");
        assertEquals(index.getContactUsFormTitleText(), "CONTACT US");

        // endregion
    }

    @Test(description = "Test 2 example", priority = 200, groups = {"Regression", "Smoke"})
    public void testTwo() {
        System.out.println("Test 2");

        assertEquals("one", "two");
    }

    @Test(description = "Test 3 example", priority = 300, groups = {"Regression"})
    public void testThree() {
        System.out.println("Test 3");
    }

    @Test(description = "Test 4 example", priority = 400, groups = {"Regression", "Skipped", "Smoke"})
    public void testFour() {
        System.out.println("Test 4");
    }

    @Test(description = "Test fluent wait", priority = 500, groups = {"Smoke"})
    public void testFluentWait() throws InterruptedException {
        index.load();
        index.contactUsTitleClick();
        index.fillContactForm();
    }


//    /**
//     * Perform accessibility test on the website under Test.
//     * The test will display in logs if there are any accessibility rule violations.
//     */
//    @Test(description = "Accessibility test for web", priority = 600, groups = {"Smoke"})
//    public void checkAccessibility() {
//        AxeRunOptions axeRunOptions = new AxeRunOptions();
//        axeRunOptions.setXPath(true);
//
//        AxeBuilder axeBuilder = new AxeBuilder()
//                .withOptions(axeRunOptions)
//                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21aa"))
//                .disableRules(Collections.singletonList("color-contrast"))
//                .disableIframeTesting();
//
//        Results results = axeBuilder.analyze((WebDriver) index);
//        List<Rule> violations = results.getViolations();
//        logInfo("Violations: {}", violations);
//    }
//
//    /**
//     * Perform accessibility test on given a specific WebElement on the website under Test.
//     * The test will display in logs if there are any accessibility rule violations.
//     */
//
//    @Test(description = "Accessibility test for web element", priority = 700, groups = {"Smoke"})
//    public void checkWebElementAccessibility() {
//
//        AxeRunOptions axeRunOptions = new AxeRunOptions();
//        axeRunOptions.setXPath(true);
//
//        AxeBuilder axeBuilder = new AxeBuilder()
//                .withOptions(axeRunOptions)
//                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21aa"))
//                .disableRules(Collections.singletonList("color-contrast"))
//                .disableIframeTesting();
//        axeBuilder.analyze((WebDriver) index, index.findElement(By.id("common-home")));
//
//        Results results = axeBuilder.analyze((WebDriver) index);
//        List<Rule> violations = results.getViolations();
//        logInfo("Violations found: {}", violations);
//        logInfo("Rule Violation: {} \tId: {} \tHelp: {}", violations.get(0).getHelp(), violations.get(0).getId(), violations.get(0).getHelp());
//        for (String tag : violations.get(0).getTags()) {
//            logError("Accessibility Error Tag: {}", tag);
//        }
//
//    }

}
