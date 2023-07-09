package WriteReadExamples;

import helpers.TXTHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.File;
import java.io.IOException;

@Listeners({ProjectListener.class})
public class WriteAndReadATXTFile extends TXTHelper {

    String folderPath = "test-data-files/";
    String filePath = "newTXTfile.txt";

    // We can create a File object and pass it in the methods, instead of the other 2 objects, it's the same
    //    File file = new File(folderPath + "DataSetUsers.txt");
    File file = new File(folderPath + filePath); // Define file to be read

    @Test(description = "Write a TXT file with a title and paragraph")
    public void testWriter() throws IOException {

        writeTxtWithTitle(folderPath + filePath,"New title for you","New paragraph fsdafasdf"); // path can be anything, new or existing file

    }

    @Test(description = "Append data to a TXT file")
    public void appendWriter() throws IOException {
     appendTxt(folderPath + filePath, "This is the new paragraph to append");
    }

    @Test(description = "Read from a TXT file")
    public void txtReader() {
        readTXTFile(file);
    }

}
