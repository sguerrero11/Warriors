package FrontEnd.Gmail;

import designpattern.pageObjects.GmailPage;

import helpers.AssertionsList;
import org.apache.commons.mail.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ProjectListener;

import javax.mail.*;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.io.*;
import java.net.URL;
import java.util.Properties;

import static org.testng.Assert.assertEquals;

@Listeners({ProjectListener.class})
public class loginWithValidCredentials extends GmailPage {


    // region VARIABLES

    private GmailPage index = new GmailPage();
    private String folderPath = System.getProperty("user.dir") + File.separator + "properties-files" + File.separator;
    private String fileName = "data.properties";
    Properties prop = new Properties();
    InputStream inputStream;


    // endregion

    @Test(description= "Test case #1: Verify user can login using a valid credential")
    public void verifyUserCanLoginValid() throws InterruptedException, IOException {

        //arrange
        AssertionsList asserts = new AssertionsList();

        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("gmailPwd");

        //act

        index.load(); // loads the page
        index.signInButtonClick(); // clicks on Sign In button
        index.enterMailAndClickNext(username); // completes user and clicks on next
        index.enterPwdAndClickNext(password); // completes pwd and clicks on next

        //assert

        asserts.equals(index.getSearchMailInputAttribute(), "Search mail") // we verify we're in the right page comparing text against search input
                .isPresent(searchMailInput); // we verify we're in the right page by checking if the search input field is present

    }

    @Test(description= "Test case #2: send an automated email using Apache Commons Email")
    public void sendEmail() throws IOException {
        // Load properties from data.properties file
        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Get email credentials from properties
        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");
        // Email details
        String recipientEmail = username;
        String subject = "App auth alpha code";
//        String body = "Testing automated email using Apache Commons";
        String body = "Your code is A1B2C3. Save this code for later!";
        // Sending the email
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);
        email.setStartTLSEnabled(true);
        try {
            email.setFrom(username);
            email.addTo(recipientEmail);
            email.setSubject(subject);
            email.setMsg(body);
            email.send();
            System.out.println("Email sent successfully!");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    @Test(description= "Test case #3: send an automated email with attachment using Apache Commons Email")
    public void sendEmailWithAttachment() throws IOException {

        // Load properties from data.properties file
        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Get email credentials from properties
        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");

        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("images/slack.png");
//        attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif")); // to attach files from a URL
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Picture of Santiago");
        attachment.setName("Santiago");

        // Email details
        String recipientEmail = username;
        String subject = "Automated email with attachment";
        String body = "Testing automated email with attachments using Apache Commons";
        // Sending the email
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);
        email.setStartTLSEnabled(true);
        try {
            email.setFrom(username);
            email.addTo(recipientEmail);
            email.setSubject(subject);
            email.setMsg(body);
            // add the attachment
            email.attach(attachment);
            email.send();
            System.out.println("Email sent successfully!");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    @Test(description= "Test case #4: send an automated email with an embedded image using Apache Commons Email")
    public void sendEmailWithEmbeddedImage() throws IOException {

        // Load properties from data.properties file
        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Get email credentials from properties
        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");


        // Email details
        String recipientEmail = username;
        String subject = "Automated email with embedded image";
        String body = "Testing automated email with embedded images using Apache Commons";
        // Sending the email
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);
        email.setStartTLSEnabled(true);
        try {
            email.setFrom(username);
            email.addTo(recipientEmail);
            email.setSubject(subject);
            // embed the image and get the content id
            URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
            String cid = email.embed(url, "Apache logo");
            // set the html message
            email.setHtmlMsg("<html>The apache logo - <img src=\"cid:"+cid+"\"></html>");
            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");
            email.send();
            System.out.println("Email sent successfully!");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    @Test (description = "Check if an email is present in the inbox")
    public void checkIfEmailIsPresent() throws IOException {

        //arrange example

        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");
        String host = "imap.gmail.com";
        String subjectToFind = "Automated email with embedded image"; // The subject of the email you want to find
//        String subjectToFind = "fadsfs"; // The subject of the email you want to find
        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            SearchTerm subjectSearchTerm = new SubjectTerm(subjectToFind);
            Message[] messages = inbox.search(subjectSearchTerm);
            if (messages.length > 0) {
                System.out.println("Email with subject '" + subjectToFind + "' found in the inbox.");
            } else {
                System.out.println("Email with subject '" + subjectToFind + "' not found in the inbox.");
            }
            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test (description = "Check if an email is present in the inbox and save its body to a string")
    public void saveBodyToString() throws IOException {

        //arrange example

        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");
        String host = "imap.gmail.com";
        String subjectToFind = "Automated email"; // The subject of the email you want to find
        StringBuilder emailBodyBuilder = new StringBuilder();
        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            SearchTerm subjectSearchTerm = new SubjectTerm(subjectToFind);
            Message[] messages = inbox.search(subjectSearchTerm);
            if (messages.length > 0) {
                Message email = messages[0]; // Assuming you want to process the first email with the specified subject
                // Get the email content (body) and store it in the StringBuilder
                getEmailContent(email, emailBodyBuilder);
                // Print the email body to the console
                System.out.println("Email Body:");
                System.out.println(emailBodyBuilder.toString());
            } else {
                System.out.println("Email with subject '" + subjectToFind + "' not found in the inbox.");
            }
            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test (description = "Check if an email is present in the inbox and save its body to a file")
    public void saveBodyToFile() throws IOException {

        //arrange example

        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");
        String host = "imap.gmail.com";
        String subjectToFind = "Automated email"; // The subject of the email you want to find

        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            SearchTerm subjectSearchTerm = new SubjectTerm(subjectToFind);
            Message[] messages = inbox.search(subjectSearchTerm);
            if (messages.length > 0) {
                Message email = messages[0]; // Assuming you want to process the first email with the specified subject
                // Get the email content (body)
                String emailContent = getEmailContent(email);
                // Save the email body to a file
                saveEmailBodyToFile(emailContent);
                // Print the email body to the console
                System.out.println("Email Body:");
                System.out.println(emailContent);
            } else {
                System.out.println("Email with subject '" + subjectToFind + "' not found in the inbox.");
            }
            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test (description = "Check if an email is present in the inbox and save a specific digits string from its body if it's the last-separated word")
    public void saveLastStringFromBody() throws IOException {

        //arrange example

        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");
        String host = "imap.gmail.com";
        String subjectToFind = "App auth code"; // The subject of the email you want to find
        String valueToStore = null;
        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            SearchTerm subjectSearchTerm = new SubjectTerm(subjectToFind);
            Message[] messages = inbox.search(subjectSearchTerm);
            if (messages.length > 0) {
                Message email = messages[0]; // Assuming you want to process the first email with the specified subject
                // Get the email content (body)
                String emailContent = getEmailContent(email);
                // Extract the value you want to store (e.g., extracting "12345" from "Your code is 12345")
                valueToStore = extractLastValueFromEmailContent(emailContent);
                System.out.println("Value extracted from the email: " + valueToStore);
            } else {
                System.out.println("Email with subject '" + subjectToFind + "' not found in the inbox.");
            }
            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test (description = "Check if an email is present in the inbox and save a specific digit string from its body when it's not the last-separated word")
    public void saveDigitsStringFromBody() throws IOException {

        //arrange example

        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");
        String host = "imap.gmail.com";
        String subjectToFind = "App auth code"; // The subject of the email you want to find
        String valueToStore = null;
        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            SearchTerm subjectSearchTerm = new SubjectTerm(subjectToFind);
            Message[] messages = inbox.search(subjectSearchTerm);
            if (messages.length > 0) {
                Message email = messages[0]; // Assuming you want to process the first email with the specified subject
                // Get the email content (body)
                String emailContent = getEmailContent(email);
                // Extract the value using a regular expression
                valueToStore = extractDigitsFromEmailContent(emailContent);
                System.out.println("Value extracted from the email: " + valueToStore);
            } else {
                System.out.println("Email with subject '" + subjectToFind + "' not found in the inbox.");
            }
            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test (description = "Check if an email is present in the inbox and save a specific alphanumeric string from its body")
    public void saveAlphaNumStringFromBody() throws IOException {

        //arrange example

        {
            try {
                inputStream = new FileInputStream(folderPath+ fileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String username = prop.getProperty("gmailUser");
        String password = prop.getProperty("appPwd");
        String host = "imap.gmail.com";
        String subjectToFind = "App auth alpha code"; // The subject of the email you want to find
        String valueToStore = null;
        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);
            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            SearchTerm subjectSearchTerm = new SubjectTerm(subjectToFind);
            Message[] messages = inbox.search(subjectSearchTerm);
            if (messages.length > 0) {
                // Iterate through matched emails in reverse order (from latest to oldest)
                for (int i = messages.length - 1; i >= 0; i--) {
                    Message email = messages[i];
                    if (email.getSubject().equals(subjectToFind)) {
                        // Get the email content (body)
                        String emailContent = getEmailContent(email);
                        // Extract the value from the email content
//                        valueToStore = extractPatternValueFromEmailContent(emailContent); // this one works too
                        valueToStore = extractAlphaValueFromEmailContent(emailContent); // this one works too
                        break; // Stop processing once we find the desired email
                    }
                }
            } else {
                System.out.println("Email with subject '" + subjectToFind + "' not found in the inbox.");
            }
            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        if (valueToStore != null) {
            // Do something with the extracted value (e.g., store it or print it)
            System.out.println("Extracted Value: " + valueToStore);
        } else {
            System.out.println("No email with subject '" + subjectToFind + "' found or value extraction failed.");
        }
    }
}

/*
Make sure this setting is ON:

Step 1: Check that IMAP is turned on
On your computer, open Gmail.
In the top right, click Settings Settings and then See all  settings.
Click the Forwarding and POP/IMAP tab.
In the "IMAP access" section, select Enable IMAP.
Click Save Changes.
-----------------

saveAlphaNumStringFromBody() test was verified that when there are multiple emails with the same subject, it gets the last one sent. Use this in
 the future and modify the extraction method.
 */
