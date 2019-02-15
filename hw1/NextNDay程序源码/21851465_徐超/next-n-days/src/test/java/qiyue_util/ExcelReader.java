package qiyue_util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class ExcelReader {

    private ExcelReader() {
    }

    public static Workbook getWorkBook(File excelFile) throws BiffException, IOException {
        Workbook rwb = null;
        InputStream stream = new FileInputStream(excelFile);
        rwb = Workbook.getWorkbook(stream);
        return rwb;
    }

    public static Sheet getWorkBookSheet(Workbook book, int sheetIndex) {
        Sheet sheet = book.getSheet(sheetIndex);
        return sheet;
    }

    public static String[] readExcelRowOrCol(Workbook rwb, int sheetIndex, int starRow, int endRow, int starCol,
                                             int endCol) throws BiffException, IOException {
        String[] data = null;
        int row_distance = Math.abs(endRow - starRow);
        int col_distance = Math.abs(endCol - starCol);

        int need_read_row = -1;
        int need_read_col = -1;

        Sheet sheet = getWorkBookSheet(rwb, sheetIndex);
        if (row_distance == 0) {
            data = new String[col_distance + 1];
            need_read_row = starRow;
        } else if (col_distance == 0) {
            data = new String[row_distance + 1];
            need_read_col = starCol;
        }

        for (int i = 0; i < data.length; i++) {
            if (need_read_row != -1)
                data[i] = readExcelData(sheet, need_read_row, i + starCol);
            else if (need_read_col != -1)
                data[i] = readExcelData(sheet, i + starRow, need_read_col);
        }
        return data;
    }

    public static String[][] readExcelAreaData(Workbook rwb, int sheetIndex, int starRow, int endRow, int starCol,
                                               int endCol) throws BiffException, IOException {
        String[][] tempData = new String[endRow - starRow + 1][endCol - starCol + 1];
        Sheet sheet = getWorkBookSheet(rwb, sheetIndex);
        for (int i = starRow; i <= endRow; i++) {
            for (int j = starCol; j <= endCol; j++) {
                tempData[i - starRow][j - starCol] = sheet.getCell(j - 1, i - 1).getContents();
            }
        }
        return tempData;
    }

    public static String readExcelData(Workbook rwb, int row, int col, int sheetIndex)
            throws BiffException, IOException {
        Sheet sheet = getWorkBookSheet(rwb, sheetIndex);
        Cell cell = null;
        cell = sheet.getCell(col - 1, row - 1);
        String temp = cell.getContents();
        return temp;
    }

    public static String readExcelData(Sheet sheet, int row, int col) throws BiffException, IOException {
        Cell cell = null;
        cell = sheet.getCell(col - 1, row - 1);
        String temp = cell.getContents();
        return temp;
    }
}
