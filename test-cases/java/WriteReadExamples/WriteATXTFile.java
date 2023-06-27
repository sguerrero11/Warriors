package WriteReadExamples;

import helpers.TXTHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.IOException;

@Listeners({ProjectListener.class})
public class WriteATXTFile extends TXTHelper {

    String folderPath = "test-data-files/";
    String filePath = "newTXTfile.txt";

    @Test(description = "Write a TXT file with a title and paragraph")
    public void testWriter() throws IOException {

        writeTxtWithTitle(folderPath + filePath,"New title for you","New paragraph fsdafasdf"); // path can be anything, new or existing file

    }
}
