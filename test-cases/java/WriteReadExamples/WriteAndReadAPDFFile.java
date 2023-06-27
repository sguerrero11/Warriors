package WriteReadExamples;

import helpers.PDFHelper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import java.io.IOException;

@Listeners({ProjectListener.class})
public class WriteAndReadAPDFFile extends PDFHelper {

    String folderPath = "test-data-files/";
    String filePath = "newPDFfile.pdf";

    @Test(description = "Write a PDF file with a title and paragraph")
    public void pdfWriter() throws IOException {

        String title = "This is the best title";
        String text = "This is the sample document and now we are adding content to it.";

        writePDFWithTitle (folderPath+filePath,title,text);

    }

    @Test(description = "Read a PDF file")
    public void pdfReader() throws IOException {

        readFromAPDF (folderPath+filePath);

    }
}
