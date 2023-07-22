package FrontEnd.Gmail;

import designpattern.pageObjects.GmailPage;

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
public class loginWithValidCredentials {


    // region VARIABLES

    private GmailPage index = new GmailPage();
    private String folderPath = System.getProperty("user.dir") + File.separator + "properties-files" + File.separator;
    private String fileName = "data.properties";
    Properties prop = new Properties();
    InputStream inputStream;


    // endregion

    @Test(description= "Test case #1: Verify user can login using a valid credential")
    public void verifyUserCanLoginValid() throws InterruptedException, IOException {

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
        String password = prop.getProperty("gmailPwd");

        //act

        index.load(); // loads the page
        index.signInButtonClick(); // clicks on Sign In button
        index.enterMailAndClickNext(username); // completes user and clicks on next
        index.enterPwdAndClickNext(password); // completes pwd and clicks on next

        //assert

        assertEquals(index.getSearchMailInputAttribute(), "Search mail");
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
        String subject = "Automated email";
        String body = "Testing automated email using Apache Commons";
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
}

/*
Make sure this setting is ON:

Step 1: Check that IMAP is turned on
On your computer, open Gmail.
In the top right, click Settings Settings and then See all  settings.
Click the Forwarding and POP/IMAP tab.
In the "IMAP access" section, select Enable IMAP.
Click Save Changes.
 */
