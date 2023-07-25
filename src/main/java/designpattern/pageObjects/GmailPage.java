package designpattern.pageObjects;

import designpattern.pom.BasePage;
import designpattern.pom.DefaultPage;
import org.openqa.selenium.By;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GmailPage extends BasePage implements DefaultPage {

    //region selectors
    final By nameInput = By.xpath("//input[@name='wpforms[fields][0]']");
    final By loginNextButton = By.xpath("(//span[@jsname='V67aGc'])[2]");
    final By pwdNextButton = By.xpath("(//span[@jsname='V67aGc'])[2]");
    final By sendButton = By.xpath("//button[@name='wpforms[submit]']");
    final By userField = By.id("identifierId");
    final By pwdField = By.xpath("//div[@class='Xb9hP']/input[@name='Passwd']");
    public final By searchMailInput = By.xpath("//input[@placeholder='Search mail']");
    final By signInButton = By.xpath("//a[@data-action='sign in']");


    //endregion

    //region load
    @SafeVarargs // is like @SuppressWarnings("what you want to suppress" e.g. "deprecation", "unchecked", "unused", etc)
    @Override
    public final <T> String getUrl(T... values) {
        return "https://www.google.com/gmail/about/";
    }

    @Override
    public <T> void load(T... values) {
        visit(getUrl(values));
    }


    @Override
    public boolean validateField(String element, String attribute, String value) {
        return false;
    }

    //endregion

    //region methods
    public String getButtonText() {
        waitForElementVisible(nameInput);
        return getText(sendButton);
    }

    public String getPwdNextButtonText() {
        waitForElementVisible(pwdNextButton);
        return getText(pwdNextButton);
    }

    public String getSearchMailInputAttribute() {
        waitForElementVisible(searchMailInput);
        return getAttribute(searchMailInput, "aria-label"); // same as placeholder
    }

    public boolean isSearchVisible(){
        waitForElementVisible(searchMailInput);
        return isDisplayed(searchMailInput);
    }


    public void writeForm() throws InterruptedException {
        waitForElementVisible(nameInput);
        sendKeys("Fede", nameInput);
        //click(sendButton);
    }

    public void signInButtonClick() throws InterruptedException {
        click(signInButton);
    }

    public void enterMailAndClickNext(String userData) throws InterruptedException {
        waitForElementVisible(loginNextButton);
        sendKeys(userData, userField);
        click(loginNextButton);
    }

    public void enterPwdAndClickNext(String pwdData) throws InterruptedException {
        waitForElementVisible(pwdNextButton);
        sendKeys(pwdData, pwdField);
        click(pwdNextButton);
    }

    protected static void getEmailContent(Message email, StringBuilder emailBodyBuilder) throws IOException, MessagingException {
        Object content = email.getContent();
        if (content instanceof String) {
            emailBodyBuilder.append((String) content);
        } else if (content instanceof Multipart) {
            // Handle multipart emails (if the email has attachments or is in HTML format, etc.)
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContent() instanceof String) {
                    emailBodyBuilder.append((String) bodyPart.getContent());
                }
            }
        }
    }

    protected static String getEmailContent(Message email) throws IOException, MessagingException {
        Object content = email.getContent();
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof Multipart) {
            // Handle multipart emails (if the email has attachments or is in HTML format, etc.)
            Multipart multipart = (Multipart) content;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getContent() instanceof String) {
                    sb.append(bodyPart.getContent());
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    protected static void saveEmailBodyToFile(String emailContent) throws IOException {
        String folderPath = System.getProperty("user.dir") + File.separator + "test-data-files" + File.separator;
        String filePath = "email_body.txt";

        try (FileWriter fileWriter = new FileWriter(folderPath+filePath)) {
            fileWriter.write(emailContent);
        }
    }

    protected static String extractLastValueFromEmailContent(String emailContent) {
        // Perform any necessary parsing or string manipulation to extract the desired value
        // For example, if the email content is "Your code is 12345", you can use regex to extract the numeric part.
        // For simplicity, let's assume the value is always the last space-separated word in the email content.
        String[] words = emailContent.split("\\s+");
        if (words.length > 0) {
            return words[words.length - 1];
        }
        return null;
    }

    protected static String extractDigitsFromEmailContent(String emailContent) {
        // Use a regular expression to find a sequence of digits after the string "Your code is"
        Pattern pattern = Pattern.compile("Your code is (\\d+)");
        Matcher matcher = pattern.matcher(emailContent);
        if (matcher.find()) {
            return matcher.group(1); // Group 1 contains the matched digits
        } else {
            return null;
        }
    }

    protected static String extractAlphaValueFromEmailContent(String emailContent) {
        // Use a regular expression to find the pattern "Your code is " followed by any combination of letters and numbers
        Pattern pattern = Pattern.compile("Your code is (\\w+)"); // works for all alpha patterns
        Matcher matcher = pattern.matcher(emailContent);
        if (matcher.find()) {
            return matcher.group(1); // Group 1 contains the matched value
        } else {
            return null;
        }
    }

    protected static String extractPatternValueFromEmailContent(String emailContent) {
        String searchPattern = "Your code is ";
        int startIndex = emailContent.indexOf(searchPattern);
        if (startIndex != -1) {
            int endIndex = startIndex + searchPattern.length();
            char[] chars = emailContent.toCharArray();
            while (endIndex < chars.length) {
                char c = chars[endIndex];
                if (!Character.isWhitespace(c)) {
                    endIndex++;
                } else {
                    break; // Stop extracting once we encounter a whitespace character
                }
            }
            return emailContent.substring(startIndex + searchPattern.length(), endIndex).trim();
        }
        return null; // Return null if the pattern is not found in the email content
    }

    //endregion
}
