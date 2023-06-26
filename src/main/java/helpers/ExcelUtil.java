package helpers;

import helpers.LoggerHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil extends LoggerHelper {

    static String projectPath = System.getProperty("user.dir");
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;
    static Row row;
    static Cell cell;


    public ExcelUtil(String excelPath, String sheetName) {

        try {

            workbook = new XSSFWorkbook(projectPath + "/" + excelPath);
            sheet = workbook.getSheet(sheetName); // Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet is being used
//            row = sheet.getRow(0); // Assuming the first row is being used
//            cell = row.getCell(0); // Assuming the first cell is being used

        } catch (Exception e) {
            logError(e.getMessage());
            logError(e.getCause());
            e.printStackTrace();
        }
    }

    public static int getRowCount() {

        int rowCount = 0;
        try {
            rowCount = sheet.getPhysicalNumberOfRows();
            logInfo("Number of rows: " + rowCount);

        } catch (Exception e) {
            logError(e.getMessage());
            logError(e.getCause());
            e.printStackTrace();
        }
        return rowCount;

    }

    public static int getColCount() {

        int colCount = 0;
        try {
            colCount = sheet.getRow(0).getPhysicalNumberOfCells();
            logInfo("Number of columns: " + colCount);

        } catch (Exception e) {
            logError(e.getMessage());
            logError(e.getCause());
            e.printStackTrace();
        }
        return colCount;

    }

    public static String getCellData (int rowNum, int colNum) {

        String cellData = null;
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
//                    cellData = String.valueOf(sheet.getRow(rowNum).getCell(colNum).getNumericCellValue());
                    cellData = String.valueOf(cell.getNumericCellValue());
                    break;
                default:
//                    cellData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
                    cellData = cell.getStringCellValue();
            }
        }
        return  cellData; // we could put return cell... inside of every case as well and delete this line
    }

    public static String getCellDataString(int rowNum, int colNum) {

        String cellData = null;

        try {

            cellData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
            logInfo(cellData);

        } catch (Exception e) {
            logError(e.getMessage());
            logError(e.getCause());
            e.printStackTrace();
        }
        return cellData;
    }

    public static void getCellDataNumber(int rowNum, int colNum) {
        try {

            double cellData = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            logInfo(cellData);

        } catch (Exception e) {
            logError(e.getMessage());
            logError(e.getCause());
            e.printStackTrace();
        }
    }
}
