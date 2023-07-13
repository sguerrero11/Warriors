package WriteReadExamples;

import helpers.CSVOrExcelHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.IOException;
import java.util.List;


@Listeners({ProjectListener.class})
public class WriteACSVOrExcelFile {

    String folderPath = "test-data-files/";
    String filePathXLS = "example.xlsx";
    String filePathCSV = "example.csv";
    String sheetName = "Sheet1";


    @Test(description = "Write to a CSV or Excel file")
    public void writeToCSVOrExcel() {


        // Example usage
        List<String[]> fileData = List.of(
                new String[]{"Name", "Age", "City"},
                new String[]{"John Martin", "25", "New York"},
                new String[]{"Jane Kelly", "30", "London"}
        );
        try {
            CSVOrExcelHelper.writeDataToCSV(folderPath + filePathCSV, fileData); // Write data to CSV file
            System.out.println("CSV file created successfully.");

            CSVOrExcelHelper.writeDataToExcel(folderPath + filePathXLS, fileData);  // Write data to Excel file
            System.out.println("Excel file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Append to a CSV or Excel file")
    public void appendToCSVOrExcel() {

        // Example usage
        List<String[]> fileData = List.of(
                new String[]{"Mike Tyson", "41", "Denver"},
                new String[]{"John Doe", "12", "Miami"},
                new String[]{"Jane Jamison", "15", "Buenos Aires"}
        );
        try {
            CSVOrExcelHelper.appendDataToCSV(folderPath + filePathCSV, fileData); // Append data to CSV file
            System.out.println("CSV file has been updated successfully.");

            CSVOrExcelHelper.appendDataToExcel(folderPath + filePathXLS, sheetName, fileData);  // Append data to Excel file
            System.out.println("Excel file has been updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Add a column to a CSV file")
    public void addAColumnToCSVOrExcel() {
//        String[] newDataCol = {"Gender", "Male", "Male", "Female"};

        List<String> newDataCol = List.of(
                "Gender",
                "Male",
                "Female"
        ); // first one is the title of the column (if applicable)

        CSVOrExcelHelper.insertColumnInCSV(folderPath + filePathCSV, 1, newDataCol); // this means it will insert the column between column 0 and column 1

        CSVOrExcelHelper.insertColumnInExcel(folderPath + filePathXLS, sheetName, 1, newDataCol); // this means it will insert the column between column 0 and column 1
    }

    @Test(description = "Add data to a specific cell in Excel file")
    public void setDataToSpecificCell() {

        CSVOrExcelHelper.insertDataToCellInExcel(folderPath + filePathXLS, sheetName,7,7,"testing");
    }


}


//            writeDataToCSV(folderPath + filePath, fileData); // To use it like this, we need to extend from the Helper
//            writeDataToExcel(folderPath + filePath, fileData);  // To use it like this, we need to extend from the Helper
//            appendDataToCSV(folderPath + filePath, fileData); // To use it like this, we need to extend from the Helper
//            appendDataToExcel(folderPath + filePath, sheetName, fileData);  // To use it like this, we need to extend from the Helper
//            Same with add column or insert column, etc