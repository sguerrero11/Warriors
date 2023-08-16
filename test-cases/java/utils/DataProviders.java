package utils;

import helpers.DataProviderHelper;
import helpers.ExcelUtil;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviders extends DataProviderHelper {

    // region VARIABLES
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
}
