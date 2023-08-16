package helpers.functions;

import com.github.javafaker.Faker;

import helpers.EnvHelper;
import helpers.Minify;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import us.codecraft.xsoup.Xsoup;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static helpers.LoggerHelper.logError;
import static helpers.LoggerHelper.logInfo;

/***
 * Helper class to do useful actions, like post new nif, or calculate random num, etc.
 */
@SuppressWarnings("null")
public abstract class FunctionsHelper {
    private static final SecureRandom rand = new SecureRandom();

    /***
     * Return random username
     * @return [String]
     */
    public static String getRandomUsername() {
        int randomNum = getRandomNum(10000);
        return "autUser" + randomNum;
    }

    /***
     * Return random email generated with default structure in config.properties file and random num.
     * @return [String]
     */
    public static String getRandomEmail() {
        LocalDateTime todayDate = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        String randomNum = dateFormat.format(todayDate);

        return EnvHelper.get("global.email.prefix") + randomNum + EnvHelper.get("global.email.suffix");
    }


    /***
     * Get random quantity of elements from specific list
     * @param list [List<String>]
     * @param quantity [Integer]
     * @return [List<String>]
     */
    private static List<String> getRandomElementsFromList(List<String> list, Integer quantity) {
        // Create a temporary list for storing selected element
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {

            // Take a random index between 0 to size of given List
            int randomIndex = rand.nextInt(list.size());

            // Add element in temporary list
            newList.add(list.get(randomIndex));

            // Remove selected element from original list
            list.remove(randomIndex);
        }

        return newList;
    }

    /***
     * Generate a random int number with range 0 - max
     * @param max [Integer] -> Max value to obtained from random
     * @return [Integer] -> Random number
     */
    public static Integer getRandomNum(int max) {
        return rand.nextInt(max);
    }

    /***
     * Return current date.
     * @return [Date] -> Formatted date
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /***
     * Return current date +/- specify days
     * @return [Date] -> Formatted date
     */
    public static Date getCurrentDate(int days) {
        LocalDate currentDate = LocalDate.now();
        if (days > 0) {
            currentDate = currentDate.plusDays(days);
        } else if (days < 0) {
            days = Math.abs(days);
            currentDate = currentDate.minusDays(days);
        }

        return java.sql.Date.valueOf(currentDate);
    }

    /***
     * Return current date with specific format (in DateTimeFormatter pattern). If format is empty
     * will be formatted with yyyy-MM-dd by default.
     * Format examples: "yyyy/MM/dd HH:mm:ss" | "dd/MM/yyyy"
     * @param format [String]
     * @return [String] -> Formatted date
     */
    public static String getFormattedCurrentDate(String format) {
        if (format.isEmpty()) {
            format = "yyyy-MM-dd";
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /***
     * Return current date with specific format (in DateTimeFormatter pattern). If format is empty
     * will be formatted with yyyy-MM-dd by default.
     * Format examples: "yyyy/MM/dd HH:mm:ss" | "dd/MM/yyyy"
     * @param format [String]
     * @param days [int] -> Days to move (positive or negative)
     * Example: getFormattedDate("dd-MM-yyyy", 7) -> Moves one week forward
     * @return [String] -> Formatted date
     */
    public static String getFormattedDate(String format, int days) {
        if (format.isEmpty()) {
            format = "yyyy-MM-dd";
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        if (days > 0) {
            now = now.plusDays(days);
        } else if (days < 0) {
            days = Math.abs(days);
            now = now.minusDays(days);
        }

        return dtf.format(now);
    }

    public static String getFormattedDatePlusMonths(String format, int months) {
        if (format.isEmpty()) {
            format = "yyyy-MM-dd";
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        if (months > 0) {
            now = now.plusMonths(months);
        } else if (months < 0) {
            now = now.minusMonths(months);
        }

        return dtf.format(now);
    }

    public static String getFormattedDate(String format, Date date) {
        if (format.isEmpty()) {
            format = "yyyy-MM-dd";
        }

        DateFormat df = new SimpleDateFormat(format);

        return df.format(date);
    }

    /***
     * Get current year from 1900-present
     * @return int
     */
    public static int getCurrentYear() { return Calendar.getInstance().get(Calendar.YEAR); }

    /***
     * Get current month from 1-12 value
     * @return int
     */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /***
     * Get current day of month from 1-31 value
     * @return [int]
     */
    public static int getCurrentDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /***
     * Return unix timestamp based on a date.
     * @param time [Date]
     * @return [long]
     */
    public static Long getUnixTime(Date time) {
        return time.toInstant().getEpochSecond();
    }

    /***
     * Get path of specific file in resource folder.
     * @param fileName [String]
     * @return [String]
     */
    public static String getAbsolutePathOfResource(String fileName) {
        String absolutePath = getAbsolutePathOfResourceFolder() + "/" + fileName;
        logInfo("[FunctionsHelper/getAbsolutePathOfResource]: File path obtained: " + absolutePath);
        return absolutePath;
    }

    public static void createFile(String fileName, String content, String fileBasePath) {
        String filePath;
        String basePath;
        try {
            if (!fileBasePath.isEmpty()) {
                basePath = FileSystems.getDefault().getPath(fileBasePath).toAbsolutePath() + "/";
                filePath = basePath + "/" + fileName;
            } else {
                basePath = getAbsolutePathOfResource(fileName);
                filePath = basePath;
            }

            try(FileWriter file = new FileWriter(filePath)) {
                file.write(content);
            }
        } catch (Exception ex) {
            logError("[FunctionsHelper/createFile]: Something goes wrong: " + ex.getMessage());
        }
    }

    public static boolean fileExists(String fileName) {
        String filePath;
        try {
            filePath = getAbsolutePathOfResource(fileName);
            return new File(filePath).exists();
        } catch (Exception ex) {
            logError("[FunctionsHelper/fileExists]: Something goes wrong trying to find file " + ex.getMessage());
            return false;
        }
    }

    /***
     * Get path of resource folder inside the project
     * @return [String]
     */
    public static String getAbsolutePathOfResourceFolder() {
        Path path = FileSystems.getDefault().getPath("./src/main/resources").toAbsolutePath();

        return path.toString();
    }

    /***
     * Get path of resource folder inside the browser docker container
     * @return [String]
     */
    public static String getDockerFilePath(String fileName) {
        return getAbsolutePathOfResourceFolder() + "/" + fileName;

    }

    /***
     * Delete file from resources folder
     * @param fileName [String]
     */
    public static void deleteResourceFile(String fileName) {
        Path path = FileSystems.getDefault().getPath("./src/main/resources/" + fileName);
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            logError(x.toString());
        }
    }

    /**
     * Get Random Name based on "es" Locale
     * @return [String]
     */
    public static String getRandomName(){
        Faker faker = new Faker(new Locale("en"));

        return faker.name().firstName();
    }

    /**
     * Get Random Lastname based on "es" Locale
     * @return String
     */
    public static String getRandomLastname(){
        Faker faker = new Faker(new Locale("en"));

        return faker.name().lastName();
    }

    public static String getFakePhone(){
        Faker faker = new Faker(new Locale("en"));

        return "5555111222";
        //return faker.phoneNumber().cellPhone().replace("-", "").replace(" ", "");
    }

    /***
     * Get password for common users (encrypted)
     * @return [String]
     */
    public static String getUserPassword() {
        if(Objects.equals(EnvHelper.get("project"), "cbw")) {
            return EnvHelper.get("password.cbw");
        }

        return EnvHelper.get("global.credentials.passwordEncrypted");
    }

    /**
     * Get Random Name based on "es" Locale
     * @return [String]
     */
    public static String getRandomCompanyName(){
        Faker faker = new Faker(new Locale("en"));

        return faker.company().name();
    }

    /***
     * Get Random Street Name
     * @return [String]
     */
    public static String getRandomStreetName(){
        Faker faker = new Faker(new Locale("en"));
        return faker.address().streetName();
    }

    /***
     * Get Random guid (uuid)
     * @return [String]
     */
    public static String getRandomGuid(){
        Faker faker = new Faker(new Locale("es"));
        return faker.internet().uuid();
    }

    /***
     * Get Random Uppercase String fixed in 12 characters
     * @return [String]
     */
    public static String getRandomString(){
        return getRandomString(10);
    }

    /***
     * Get Random Uppercase String with specific length
     * @return [String]
     */
    public static String getRandomString(int length) {
        var leftLimit = 97; // letter 'a'
        var rightLimit = 122; // letter 'z'

        String generatedString = rand.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString.toUpperCase();
    }


    /***
     * Get generated token using sha256 based on user id and username.
     * @param id [Integer]
     * @param username [String]
     * @return [String]
     */
    public static String generateToken(Integer id, String username) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest((id.toString() + username).getBytes(StandardCharsets.UTF_8));
            String result = bytesToHex(encodedHash);
            logInfo("[FunctionsHelper/generateToken]: Generated token: " + result);

            return result;
        }catch (Exception ex) {
            logError("[FunctionsHelper/generateToken]: Something goes wrong with parsing values: " + id + " and " + username + " ->" + ex.getMessage());
            return "";
        }
    }

    /***
     * Generate activation_code based on current time using MD5 hash.
     * @return [String]
     */
    public static String generateActivationCode() {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] encodedHash = digest.digest(FunctionsHelper.getUnixTime(FunctionsHelper.getCurrentDate()).toString().getBytes(StandardCharsets.UTF_8));
            String result = bytesToHex(encodedHash);
            logInfo("[FunctionsHelper/generateActivationCode]: Generated token: " + result);

            return result;
        }catch (Exception ex) {
            logError("[FunctionsHelper/generateToken]: Something goes wrong with parsing: " + ex.getMessage());
            return "";
        }
    }

    /***
     * Convert hash bytes into hexadecimal string
     * @param hash [byte[]]
     * @return [String]
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /***
     * Get jwt token and parse its payload into JSONObject.
     * @param token [String]
     * @return JSONObject
     * @throws ParseException Native from JSONParser
     */
    public static JSONObject decodeToken(String token) throws ParseException {
        String[] parts = token.split("\\.", 0);

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8));
    }

    public static String readOnlinePdf(String onlineUrl) {
        try {
            URL url = new URL(onlineUrl);

            try(InputStream is = url.openStream()) {
                BufferedInputStream fileParse = new BufferedInputStream(is);
                PDDocument document;

                document = PDDocument.load(fileParse);

                return new PDFTextStripper().getText(document);
            }
        } catch (Exception ex) {
            logError("FunctionsHelper/readOnlinePdf] Something goes wrong reading the pdf file.");
            return "";
        }
    }

    public static String readPdf(String filename) {
        try {
            String file = "src/main/resources/Downloads/" + filename;

            try(var fis = new FileInputStream(file)) {
                BufferedInputStream fileParse = new BufferedInputStream(fis);
                PDDocument document;

                document = PDDocument.load(fileParse);

                return new PDFTextStripper().getText(document);
            }
        } catch (Exception ex) {
            logError("FunctionsHelper/readPdf] Something goes wrong reading the pdf file.");
            return "";
        }
    }

//    public static boolean convertPdfToHtml(String pdfWithExtension, String htmlWithoutExtension) {
//        try {
//            String file = "src/main/resources/Downloads/" + pdfWithExtension;
//
//            try(var fis = new FileInputStream(file)) {
//                PdfDocument pdf = new PdfDocument();
//                pdf.loadFromFile(file);
//                pdf.saveToFile("src/main/resources/Downloads/" + htmlWithoutExtension + ".html", FileFormat.HTML);
//
//                return true;
//            }
//        } catch (Exception ex) {
//            LoggerHelper.logError("FunctionsHelper/convertPdfToHtml] Something goes wrong converting the pdf file.");
//            return false;
//        }
//    }

    public static String getTextFromXpathFromHtml(String xpath, String html){
        // Esto lo elimino just in case, porque puede causar error
        html = html.replace("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n", "");
        var document = Jsoup.parse(html);
        return Xsoup.compile(xpath + "/allText()").evaluate(document).get();
    }

    public static String getHtmlFromXpathFromHtml(String xpath, String html){
        // Esto lo elimino just in case, porque puede causar error
        html = html.replace("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n", "");
        var document = Jsoup.parse(html);
        return Xsoup.compile(xpath + "/outerHtml()").evaluate(document).get();
    }

    public static String readHtmlFile(String file){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            logError("FunctionsHelper/readHtmlFile] Error at reading html file.");
        }
        return contentBuilder.toString();
    }

    public static String getTextFromXpathFromDownloadedHtml(String xpath, String fileName){
        String fullPath = FunctionsHelper.getAbsolutePathOfResource("Downloads/" + fileName);
        String htmlContent = FunctionsHelper.readHtmlFile(fullPath);
        return getTextFromXpathFromHtml(xpath, htmlContent);
    }

    public static String getMuleAuthenticationKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        sha256_HMAC.init(new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256"));
        String minifiedJson = new Minify().minify(data);
        byte[] result = sha256_HMAC.doFinal(minifiedJson.getBytes("UTF-8"));
        return DatatypeConverter.printHexBinary(result).toLowerCase();
    }

    /***
     * Return random color using Faker
     * @return [String]
     */
    public static String getRandomColor() {
        Faker faker = new Faker(new Locale("en"));
        return faker.color().name();
    }
}
