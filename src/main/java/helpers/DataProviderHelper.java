package helpers;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class DataProviderHelper extends LoggerHelper {

    public void csvGetData(String filePath, List<Object[]> testData) throws IOException {

        String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

        if ((extension.equals("csv")) || (extension.equals("txt"))) {

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                testData.add(data);
            }
        } else {
            logError("You need to pass a CSV or CSV-like (e.g. txt) file");
        }
    }

    public void excelGetData(ExcelUtil excel, String filePath, Object[][] data, int rows, int cols) throws IOException {

        String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

        if ((extension.equals("xlsx")) || (extension.equals("xls"))) {

            for (int i = 1; i < rows; i++) { // We start with 1 because 0 is the title
                for (int j = 0; j < cols; j++) {

                    String cellData = excel.getCellData(i, j);
                    data[i - 1][j] = cellData;
                }
            }
        }
        else {
            logError("You need to pass an Excel file");
        }

    }

}

// This DP Helper was designed to help with CSV or Excel files that are used as Data Providers