package FrontEnd.SauceDemo;

import designpattern.pageObjects.SauceDemoPage;
import helpers.DataProviderHelper;
import org.testng.annotations.*;
import helpers.ExcelUtil;
import utils.ProjectListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Listeners({ProjectListener.class})
public class LoginWithDP extends DataProviderHelper {

    // region VARIABLES

    private SauceDemoPage index = new SauceDemoPage();

    String folderPath = "test-data-files/";
    String filePath = null;
    String sheetName = null;


    // endregion

    // region DATA PROVIDERS

    @DataProvider(name = "array")
    public Object[][] dataSet1() {
        return new Object[][]
                {
                        {"standard_user", "secret_sauce"},
                        {"locked_out_user", "secret_sauce"},
                        {"problem_user", "secret_sauce"},
                        {"performance_glitch_user", "secret_sauce"}
                };
    }

    @DataProvider(name = "excel")
    public Object[][] dataSet2() throws IOException {

        filePath = "UsersDataExcel.xlsx";
        sheetName = "Sheet1";
        ExcelUtil excel = new ExcelUtil(folderPath + filePath, sheetName); // instantiate excel file

        // Counting rows and cols
        int rows = excel.getRowCount();
        int cols = excel.getColCount();

        //Instantiate data object subject to excel
        Object data[][] = new Object[rows - 1][cols]; // We create an object that's going to be modified by the helper, passing -1 because we don't want to count the title

        excelGetData(excel, filePath, data, rows, cols);

        return data;
    }

    @DataProvider(name = "csv")
    public Iterator<Object[]> dataset3() throws IOException {

        filePath = folderPath + "DataSetUsers.txt"; // Specify the path to your CSV-like file
        List<Object[]> testData = new ArrayList<>(); // We create a list that's going to be modified by the helper

        csvGetData(filePath, testData);

        return testData.iterator();

    }

    //endregion

    // region BEFORE ANNOTATIONS
    @BeforeSuite
    public void setup() {

        // add code to run before suite
    }

    @BeforeClass
    public void beforeClass() {

        // add code to run before class
    }

    @BeforeTest
    public void beforeTest() {
        // add code to run before tests
    }

    // endregion

    // region TESTS

    @Test(description = "Trying to read data from an array object", dataProvider = "array", priority = 100, groups = {"Regression", "Smoke"})
    public void readFromArray(String username, String pwd) throws IOException {

        // region ARRANGE
        List<String> validationList = new ArrayList<>();

        // endregion

        // region ACT
        index.load();
        index.userInput(username);
        logInfo("Testing user: " + username);
        index.pwdInput(pwd);
        logInfo("Testing password: " + pwd);
        index.loginClick();

        // endregion

        // region ASSERT

        if (username!="locked_out_user")
        {
            assertEquals(index.getTitle(),"Swag Labs");
            assertEquals(index.getProductsTitle(),"Products");
            validationList.add("assertEquals(index.getTitle(),\"Swag Labs\")");
            validationList.add("assertEquals(index.getProductsTitle(),\"Products\")");
        }
        else{
            assertEquals(index.getErrorMessage(),"Epic sadface: Sorry, this user has been locked out.");
            validationList.add("assertEquals(index.getErrorMessage(),\"Epic sadface: Sorry, this user has been locked out.\")");
        }

        logAssertion(validationList);

        // endregion

    }

    @Test(description = "Trying to read data from an Excel file", dataProvider = "excel", priority = 300, groups = {"Regression", "Smoke"})
    public void readFromFileExcel(String username, String pwd) throws IOException {

        // region ARRANGE

        // endregion

        // region ACT
        index.load();
        index.userInput(username);
        logInfo("Testing user: " + username);
        index.pwdInput(pwd);
        logInfo("Testing password: " + pwd);
        index.loginClick();

        // endregion

        // region ASSERT


        // endregion

    }

    @Test(description = "Trying to read data from a CSV-like file", dataProvider = "csv", priority = 400, groups = {"Regression", "Smoke"})
    public void readFromFileCSVLike(String username, String pwd) throws IOException {

        // region ARRANGE

        // endregion

        // region ACT
        index.load();
        index.userInput(username);
        logInfo("Testing user: " + username);
        index.pwdInput(pwd);
        logInfo("Testing password: " + pwd);
        index.loginClick();

        // endregion

        // region ASSERT


        // endregion

    }

    // endregion
}
