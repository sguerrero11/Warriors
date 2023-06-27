package helpers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class CSVOrExcelHelper extends LoggerHelper {

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

    // Method to write data to a CSV file
    public static void writeDataToCSV(String filePath, List<String[]> data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    writer.append(row[i]);  // Append cell value to the writer
                    if (i < row.length - 1) {
                        writer.append(",");  // Append comma as a delimiter between cells
                    }
                }
                writer.append("\n"); // Append a new line character to move to the next row
            }
            writer.flush(); // Flush and write remaining data to the file
        }
    }

    // Method to write data to an Excel file
    public static void writeDataToExcel(String filePath, List<String[]> data) throws IOException {
        Workbook workbook = new XSSFWorkbook();  // Create a new Workbook (Excel file)
        Sheet sheet = workbook.createSheet("Sheet1");  // Create a new sheet in the Workbook
        int rowNum = 0;
        for (String[] row : data) {
            Row excelRow = sheet.createRow(rowNum++);  // Create a new row in the sheet
            int colNum = 0;
            for (String cellData : row) {
                Cell cell = excelRow.createCell(colNum++); // Create a new cell in the row
                cell.setCellValue(cellData);  // Set the cell value to the corresponding data value
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);  // Write the Workbook to the output stream
        }
    }
}
