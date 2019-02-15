package qiyue_util;

import jxl.Workbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import qiyue.util.NextNDaysCalculator;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class NextNDaysCalculatorTest {

    private static final int DATA_ROWS = 26;

    @Parameters
    public static Collection<Object[]> receiveParams() {
        File file = new File("src/main/resources/testcase.xls");
        Object[][] objects = new Object[DATA_ROWS][5];
        try {
            Workbook wb = ExcelReader.getWorkBook(file);
            String[][] area_data = ExcelReader.readExcelAreaData(wb, 0, 2, DATA_ROWS + 1, 1, 5);
            for (int i = 0; i < DATA_ROWS; i++) {
                objects[i][0] = Long.parseLong(area_data[i][0].trim());
                objects[i][1] = Integer.parseInt(area_data[i][1].trim());
                objects[i][2] = Integer.parseInt(area_data[i][2].trim());
                objects[i][3] = Long.parseLong(area_data[i][3].trim());
                objects[i][4] = area_data[i][4].trim();
            }
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(objects);
    }

    private Long inYear;
    private int inMonth;
    private int inDay;
    private long inN;
    private String excepted;

    public NextNDaysCalculatorTest(Long inYear, int inMonth, int inDay, long inN, String excepted) {
        this.inYear = inYear;
        this.inMonth = inMonth;
        this.inDay = inDay;
        this.inN = inN;
        this.excepted = excepted;
    }

    @Test
    public void testNextNDaysFrom() {
        Assert.assertEquals(excepted, NextNDaysCalculator.nextNDaysFrom(inYear, inMonth, inDay, inN));
    }
}
