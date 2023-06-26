package helpers;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TXTReaderHelper extends LoggerHelper {

    public void readTXTFile(File file) {
        logInfo("Reading TXT file: ");
        try {
//            FileReader fileReader = new FileReader(file);  // Create a FileReader object to read the file. This can be done directly as shown below
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));  // Wrap FileReader in a BufferedReader for efficient reading
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Read each line of the file until the end
                System.out.println(line);  // Print the current line to the console
            }
            bufferedReader.close();  // Close the BufferedReader to release resources
        } catch (IOException e) {
//            e.printStackTrace();  // Handle any potential IOException and print the stack trace
            logError("File not found");
        }
    }
}
