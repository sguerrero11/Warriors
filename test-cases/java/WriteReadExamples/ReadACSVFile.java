package WriteReadExamples;

import helpers.CSVFormatReaderHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.File;
import java.io.IOException;

@Listeners({ProjectListener.class})
public class ReadACSVFile extends CSVFormatReaderHelper {

    String folderPath = "test-data-files/";
    //    File file = new File("DataSetUsers.txt");
    File file = new File(folderPath + "CSVRandomFile.csv"); // Define CSV file to be read
    File file2 = new File(folderPath + "DataSetUsers.csv"); // Define CSV file to be read
    File file3 = new File(folderPath + "DataSetUsers.txt"); // Define CSV file to be read

    @Test(description = "Read from a file that has CSV format")
    public void csvReader() throws IOException {
        readCSVFile(file);
        readCSVFile(file2);
        readCSVFile(file3);
    }
}
