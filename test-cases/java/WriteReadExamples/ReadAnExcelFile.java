package WriteReadExamples;

import helpers.CSVOrExcelHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import helpers.ExcelUtil;
import utils.ProjectListener;

import java.io.IOException;

@Listeners({ProjectListener.class})

public class ReadAnExcelFile extends CSVOrExcelHelper {

    String folderPath = "test-data-files/";
    String filePath="UsersDataExcel.xlsx";
    String sheetName="Sheet1";

    ExcelUtil excel = new ExcelUtil(folderPath + filePath, sheetName); // Define the excel file path that you want to work with

    @Test (description= "Get row/col count from an Excel file")
    public void getNumberOfRowsAndCols(){

        excel.getRowCount();
        excel.getColCount();
    }

    @Test (description= "Get strings cell data from a specific cell in an Excel file")
    public void getStringDataFromACell(){

        excel.getCellDataString(0,0);
    }

    @Test (description= "Get numbers cell data from a specific cell in an Excel file")
    public void getNumberDataFromACell(){

        excel.getCellDataNumber(3,1);
    }

    @Test (description= "Get all data from an Excel file regardless of type")
    public void readAnEntireExcel() throws IOException {

        // Counting rows and cols
        int rows = excel.getRowCount();
        int cols = excel.getColCount();


        readExcelFile(excel,rows,cols);
    }
}
