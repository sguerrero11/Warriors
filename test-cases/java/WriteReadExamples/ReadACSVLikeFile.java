package WriteReadExamples;

import helpers.CSVOrExcelHelper;
import helpers.LoggerHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.File;

@Listeners({ProjectListener.class})
public class ReadACSVLikeFile extends CSVOrExcelHelper {

    String folderPath = "test-data-files/";
    File file = new File(folderPath + "CSVRandomFile.csv"); // Define CSV file to be read
    File file2 = new File(folderPath + "DataSetUsers.csv"); // Define CSV file to be read
    File file3 = new File(folderPath + "DataSetUsers.txt"); // Define CSV file to be read

    @Test(description = "Read from a file that has CSV format")
    public void csvReader(){
        readCSVFile(file);
        readCSVFile(file2);
        readCSVFile(file3);
    }
}
