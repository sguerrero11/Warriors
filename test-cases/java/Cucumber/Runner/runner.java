package Cucumber.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions (
        features = {"test-cases/java/resources/features"},
        glue = {"Cucumber.StepsDefinition"}) // you need to show all the path to the package
public class runner extends AbstractTestNGCucumberTests {
/*
    @BeforeClass
    public void beforeClass(){

    }
    @AfterClass
    public void afterClass(){

    }

 */
}
