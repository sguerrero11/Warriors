package helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.PrintWriter;


public abstract class CSVOrExcelHelper extends LoggerHelper {

    public static void readExcelFile(ExcelUtil excel, int rows, int cols) throws IOException {

        excel.readingFileMessage();

        for (int i = 0; i < rows; i++) { // We start with 0 because 0 is the title
            for (int j = 0; j < cols; j++) {

                String cellData = excel.getCellData(i, j);
                System.out.printf("%-30s", cellData);
            }
            System.out.println("");
        }
    }


    public static void readCSVFile(File file) {
        logInfo("Reading file: " + file);
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // use this if values already contain commas, otherwise just (",")

                for (String index : row) {
                    System.out.printf("%-30s", index);
                }
                System.out.println();
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logError("File not found.");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Method to write data to a CSV file (replacing current data)
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

            // Remove the last empty line
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long length = file.length();
            if (length > 0) {
                file.setLength(length - 1);
            }
            file.close();
        }
    }

    /**
     * @param filePath
     * @param newData
     */
    public static void appendDataToCSV(String filePath, List<String[]> newData) {

        try {
            // Count the number of rows in the original file
            long originalRowCount = Files.lines(Paths.get(filePath)).count();
            // Append new data to the file
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(writer);
            // Append a new line before writing the new data
            printWriter.println();
            for (String[] row : newData) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    rowBuilder.append(row[i]);
                    if (i < row.length - 1) {
                        rowBuilder.append(",");
                    }
                }
                printWriter.println(rowBuilder.toString()); // Write the row data
            }
            printWriter.flush();
            printWriter.close();
            // Truncate the file to remove any extra empty line if present
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            long length = raf.length();
            if (length > 0) {
                raf.setLength(length - System.lineSeparator().length());
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param filePath    --> path where the txt is saved
     * @param columnIndex --> where to insert the new column
     * @param newData     --> string that contains the values for each row for the new column
     */
    public static void insertColumnInCSV(String filePath, int columnIndex, List<String> newData) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<List<String>> rows = new ArrayList<>();
            // Read existing rows from the CSV file
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1); // -1 to preserve empty values
                rows.add(new ArrayList<>(Arrays.asList(values)));
            }
            reader.close();
            int numRowsToAdd = Math.max(newData.size() - rows.size(), 0);
            int originalNumColumns = rows.get(0).size(); // Assume the first row represents the column headers
            // Append additional rows with blank values
            for (int i = 0; i < numRowsToAdd; i++) {
                List<String> newRow = new ArrayList<>();
                for (int j = 0; j < columnIndex; j++) {
                    newRow.add("");
                }
                for (int j = columnIndex; j < originalNumColumns; j++) {
                    newRow.add("");
                }
                newRow.add(""); // Blank value for new column
                rows.add(newRow);
            }
            // Insert new column into existing rows
            for (int i = 0; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                if (i < newData.size()) {
                    row.add(columnIndex, newData.get(i));
                } else {
                    row.add(columnIndex, ""); // Empty value for additional rows
                }
            }
            // Write the modified rows back to the CSV file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (List<String> row : rows) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int i = 0; i < row.size(); i++) {
                    rowBuilder.append(row.get(i));
                    if (i < row.size() - 1) {
                        rowBuilder.append(",");
                    }
                }
                // Remove trailing comma if present
                if (rowBuilder.charAt(rowBuilder.length() - 1) == ',') {
                    rowBuilder.setLength(rowBuilder.length() - 1);
                }
                writer.write(rowBuilder.toString());
                writer.newLine();
            }
            writer.close();

            // Truncate the file to remove the extra empty line
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            long length = raf.length();
            if (length > 0) {
                raf.setLength(length - System.lineSeparator().length());
            }
            raf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write data to an Excel file (replacing current data)
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
            workbook.close();
        }
    }

    // Method to append data to an existing Excel file
    public static void appendDataToExcel(String filePath, String sheetName, List<String[]> data) throws IOException {
        Workbook wb = new XSSFWorkbook(new FileInputStream(filePath));
        Sheet sheet = wb.getSheet(sheetName);
        int lastRowNum = sheet.getLastRowNum() + 1;
        for (String[] row : data) {
            Row excelRow = sheet.createRow(lastRowNum++);  // Create a new row in the sheet
            int colNum = 0;
            for (String cellData : row) {
                Cell cell = excelRow.createCell(colNum++); // Create a new cell in the row
                cell.setCellValue(cellData);  // Set the cell value to the corresponding data value
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            wb.write(outputStream);  // Write the Workbook to the output stream
            wb.close();
        }
    }

    public static void insertColumnInExcel(String filePath, String sheetName, int columnIndex, List<String> newData) {
        logInfo("Inserting new data into column " + columnIndex + " in file: " + filePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum() + 1;
            int newRowCount = newData.size();
            int maxRowCount = Math.max(rowCount, newRowCount);
            for (int i = 0; i < maxRowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }
                // Shift existing data to the right
                for (int j = row.getLastCellNum() - 1; j >= columnIndex; j--) {
                    Cell oldCell = row.getCell(j);
                    Cell newCell = row.createCell(j + 1);
                    if (oldCell != null) {
                        newCell.setCellValue(oldCell.getStringCellValue());
                        CellStyle style = workbook.createCellStyle();
                        style.cloneStyleFrom(oldCell.getCellStyle());
                        newCell.setCellStyle(style);
                    }
                }
                // Insert new data in the specified column
                if (i < newRowCount) {
                    Cell cell = row.createCell(columnIndex);
                    cell.setCellValue(newData.get(i));
                } else {
                    Cell cell = row.createCell(columnIndex);
                    cell.setCellValue(""); // Insert a blank for missing rows
                }
            }
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDataToCellInExcel(String filePath, String sheetName, int rowNum, int colNum, String data) {
        Row excelRow = null;
        Cell cell = null;

        try {

            Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet.getRow(rowNum) != null) {
                excelRow = sheet.getRow(rowNum);
                cell = excelRow.getCell(colNum);
            } else {
                excelRow = sheet.createRow((rowNum));
                cell = excelRow.createCell(colNum);
            }

            cell.setCellValue(data);


            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);  // Write the Workbook to the output stream

                outputStream.close();
                workbook.close();
            }

            logInfo("\"" + data + "\"" + " was added to row " + rowNum + " and col " + colNum);

        } catch (Exception e) {
            logError(e.getMessage());
            logError(e.getCause());
            e.printStackTrace();
        }
    }
}
