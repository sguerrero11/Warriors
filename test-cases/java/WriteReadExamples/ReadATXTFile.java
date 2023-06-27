package WriteReadExamples;

import helpers.LoggerHelper;
import helpers.TXTHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.File;
import java.io.IOException;

@Listeners({ProjectListener.class})
public class ReadATXTFile extends TXTHelper {

    String folderPath = "test-data-files/";
    String filePath = "newTXTfile.txt";

    //    File file = new File("DataSetUsers.txt");
    File file = new File(folderPath + filePath); // Define file to be read

    @Test(description = "Read from a TXT file")
    public void txtReader() throws IOException {
        readTXTFile(file);
    }
}
