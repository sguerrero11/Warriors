package FrontEnd;

import designpattern.pageObjects.SauceDemoPage;
import helpers.DataProviderHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import helpers.ExcelUtil;
import utils.ProjectListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Listeners({ProjectListener.class})
public class LoginWithDP extends DataProviderHelper {

    private SauceDemoPage index;
    String folderPath = "test-data-files/";
    String filePath=null;
    String sheetName=null;


    @DataProvider(name="data set from array")
    public Object[][] dataSet1 (){
        return new Object[][]
                {
                        {"standard_user","secret_sauce"},
                        {"locked_out_user","secret_sauce"},
                        {"problem_user","secret_sauce"},
                        {"performance_glitch_user","secret_sauce"}
                };
    }

    @DataProvider(name="data set from an Excel file")
    public Object[][] dataSet2 () throws IOException {

        filePath = "UsersDataExcel.xlsx";
        sheetName = "Sheet1";
        ExcelUtil excel = new ExcelUtil(folderPath + filePath, sheetName);

        int rows = excel.getRowCount();
        int cols = excel.getColCount();

        Object data[][] = new Object[rows-1][cols]; // We pass -1 because we don't want to count the title

        excelGetData(excel,filePath, data,rows,cols);

        return data;
    }

    @DataProvider(name="data set from a CSV/TXT file")
    public Iterator<Object[]> dataset3 () throws IOException {

        filePath = folderPath + "DataSetUsers.txt"; // Specify the path to your CSV-like file
        List<Object[]> testData = new ArrayList<>(); // We create a list that's going to be modified by the helper

        csvGetData(filePath,testData);

        return testData.iterator();

    }

    @BeforeClass(groups = {"Regression"})
    public void beforeClass() {

        index = new SauceDemoPage();
    }

    @Test(description = "Trying a data set from an object array", dataProvider = "data set from array", priority = 100, groups = {"Regression", "Smoke"})
    public void readFromArray (String username, String pwd) throws IOException {

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

    @Test(description = "Trying a data set from an Excel file", dataProvider = "data set from an Excel file", priority = 300, groups = {"Regression", "Smoke"})
    public void readFromFileExcel (String username, String pwd) throws IOException {

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

    @Test(description = "Trying a data set from a CSV/TXT file", dataProvider = "data set from a CSV/TXT file", priority = 400, groups = {"Regression", "Smoke"})
    public void readFromFileCSVLike (String username, String pwd) throws IOException {

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
}
