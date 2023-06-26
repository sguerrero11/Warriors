package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVFormatReaderHelper extends  LoggerHelper {

    public void readCSVFile(File file) {
        logInfo("Reading file: " + file);
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null){
                String[] row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // use this if values already contain commas, otherwise just (",")

                for (String index : row){
                    System.out.printf("%-30s",index);
                }
                System.out.println();
            }
        }
        catch(Exception e){
//            e.printStackTrace();
            logError("File not found.");
        }
        finally{
            try{
                reader.close();
            } catch(IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
