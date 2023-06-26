package WriteReadExamples;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import helpers.ExcelUtil;
import utils.ProjectListener;

@Listeners({ProjectListener.class})

public class workWithExcel {

    String folderPath = "test-data-files/";
    ExcelUtil excel = new ExcelUtil(folderPath + "UsersDataExcel.xlsx", "Sheet1"); // Define the excel file path that you want to work with

    @Test (description= "Work with an Excel file")
    public void getNumberOfRows(){

        excel.getRowCount();
    }

    @Test (description= "Get strings cell data from an Excel file")
    public void getStringDataFromACell(){

        excel.getCellDataString(0,0);
    }

    @Test (description= "Get numbers cell data from an Excel file")
    public void getNumberDataFromACell(){

        excel.getCellDataNumber(3,1);
    }
}
