package WriteReadExamples;

import org.testng.annotations.Listeners;
import utils.ProjectListener;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Listeners({ProjectListener.class})
public class AddColumnToCSVorTXT {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        String folderPath = "test-data-files/";
        String[] newDataCol = {"Test", "For", "You"}; // each position is a row
        AddColumn(newDataCol, folderPath + "CSVRandomFile.csv", ",", 1); // this means it will insert the column between column 0 and column 1
    }

    /**
     *
     * @param newCol --> string that contains the values for each row for the new column
     * @param filepath --> path where the txt is saved
     * @param delimiter --> how the columns are separated from one another (for a CSV, it is usually a comma)
     * @param colPos --> where to insert the new column
     */
    public static void AddColumn(String[] newCol, String filepath, String delimiter, int colPos) {
        try {
            List<String> data = Files.readAllLines(Paths.get(filepath));

            new PrintWriter(filepath);
            PrintWriter pw;

            FileWriter fw = new FileWriter(filepath, true);
            pw = new PrintWriter(fw);

            for (int i = 0; i < data.size(); i++){

                String[] line = data.get(i).split(delimiter);
                List <String> record = new ArrayList<String>(Arrays.asList(line));
                record.add(colPos,newCol[i]);
                pw.println(String.join(delimiter,record));
            }

            pw.close();

            System.out.println("Column added.");


        } catch (Exception e) {

            System.out.println(e);

        }
    }
}
