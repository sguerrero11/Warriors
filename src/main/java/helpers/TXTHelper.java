package helpers;

import java.io.*;


public class TXTHelper extends LoggerHelper {

    public void writeTxtWithTitle(String path, String title, String paragraph) throws IOException {

        File file = new File(path);

        logInfo("Writing the following file " + path);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(title);
        writer.write(System.getProperty( "line.separator" ));
        writer.write(System.getProperty( "line.separator" ));
        writer.write(paragraph);
        writer.close();
    }

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
