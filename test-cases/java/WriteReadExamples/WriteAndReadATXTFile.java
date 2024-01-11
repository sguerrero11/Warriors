package WriteReadExamples;

import helpers.TXTHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

//@Listeners({ProjectListener.class})
public class WriteAndReadATXTFile extends TXTHelper {

    String folderPath = "test-data-files/";
    String filePath = "newTXTfile.txt";

    String path = folderPath + filePath;

    Path pathP = Path.of(path);

    // We can create a File object and pass it in the methods, instead of the other 2 objects, it's the same
    //    File file = new File(folderPath + "DataSetUsers.txt");
    File file = new File(path); // Define file to be read

    @Test(description = "Write a TXT file with a title and paragraph")
    public void testWriter() throws IOException {
        writeTxtWithTitle(path,"New title for you","New paragraph fsdafasdf"); // path can be anything, new or existing file
    }

    @Test(description = "Append data to a TXT file")
    public void appendWriter() throws IOException {
     appendTxt(path, "This is the new paragraph to append");
    }

    @Test(description = "Read from a TXT file")
    public void txtReader() {
        readTXTFile(file);
    }

    @Test(description = "Appending a string into a file without the helper")
    public void txtWriteAString() throws IOException {
        Files.writeString(pathP, "This is a string", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
