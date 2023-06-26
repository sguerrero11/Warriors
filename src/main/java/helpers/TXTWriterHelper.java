package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TXTWriterHelper extends LoggerHelper {

    public void writeTxt (String path, String title, String paragraph) throws IOException {

        File file = new File(path);

        logInfo("Writing the following file " + path);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(title);
        writer.write(System.getProperty( "line.separator" ));
        writer.write(System.getProperty( "line.separator" ));
        writer.write(paragraph);
        writer.close();
    }
}
