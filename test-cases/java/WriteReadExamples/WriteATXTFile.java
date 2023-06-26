package WriteReadExamples;

import helpers.TXTWriterHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.IOException;

@Listeners({ProjectListener.class})
public class WriteATXTFile extends TXTWriterHelper {

    String folderPath = "test-data-files/";

    @Test(description = "Write a TXT file with a title and paragraph")
    public void testWriter() throws IOException {

        writeTxt(folderPath + "newTXTfile.txt","New title","New paragraph fsdafasdf"); // path can be anything, new or existing file

    }
}
