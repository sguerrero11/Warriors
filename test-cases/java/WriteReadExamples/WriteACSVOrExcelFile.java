package WriteReadExamples;

import helpers.CSVOrExcelHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.IOException;
import java.util.List;



@Listeners({ProjectListener.class})
public class WriteACSVOrExcelFile extends CSVOrExcelHelper {

    @Test(description = "Write to a CSV or Excel file")
    public void writeToCSVOrExcel() {

        String folderPath = "test-data-files/";

        // Example usage
        List<String[]> csvData = List.of(
                new String[]{"Name", "Age", "City"},
                new String[]{"John Doe", "25", "New York"},
                new String[]{"Jane Smith", "30", "London"}
        );
        try {
            writeDataToCSV(folderPath + "example.csv", csvData); // Write data to CSV file
            System.out.println("CSV file created successfully.");
            writeDataToExcel(folderPath + "example.xlsx", csvData);  // Write data to Excel file
            System.out.println("Excel file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
