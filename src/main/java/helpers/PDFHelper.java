package helpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.github.javafaker.Faker;
import org.apache.pdfbox.text.PDFTextStripper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Calendar;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class PDFHelper extends LoggerHelper {

    Faker faker = new Faker(new Locale("en"));
    PDDocument document = new PDDocument();
    PDPage blankPage = new PDPage();
    PDDocumentInformation pdd = document.getDocumentInformation();
    PDPageContentStream contentStream;

    {
        try {
            contentStream = new PDPageContentStream(document, blankPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This needs to be modified to write more than one line
    public void writePDFWithTitle(String filePath, String title, String text) throws IOException {
        document.addPage(blankPage);
        pdd.setTitle(title);
        /*
        pdd.setAuthor();
        pdd.setCreationDate();
        pdd.setCreator();
        pdd.setSubject();
         */

        pdd.setModificationDate(Calendar.getInstance());
        //Begin the Content stream
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 500);


        //Adding text in the form of string
        contentStream.showText(text);

        //Ending the content stream
        contentStream.endText();
        contentStream.close();
        document.save(filePath);
        document.close();
    }

    public void readFromAPDF(String filePath) throws IOException {
        //Loading an existing document
        File file = new File(filePath);
        PDDocument document = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        System.out.println(text);

        //Closing the document
        document.close();

    }

    // Add method to insert a picture in the pdf
    // Add method to encrypt a pdf

    /*
    The AccessPermission class is used to protect the PDF Document by assigning access permissions to it. Using this class, you can restrict users from performing the following operations.

Print the document
Modify the content of the document
Copy or extract content of the document
Add or modify annotations
Fill in interactive form fields
Extract text and graphics for accessibility to visually impaired people
Assemble the document
Print in degraded quality

The StandardProtectionPolicy class is used to add a password based protection to a document.


     */


    // Add method to add Javascript code to a pdf (popup)

    // Add method to split a pdf into multiple PDFs based on the amount of pages

    // Add method to merge multiple pdfs into one

    // Add method to convert PDF to image
}

/*
    Refer to https://www.tutorialspoint.com/pdfbox/pdfbox_merging_multiple_pdf_documents.htm
*/
