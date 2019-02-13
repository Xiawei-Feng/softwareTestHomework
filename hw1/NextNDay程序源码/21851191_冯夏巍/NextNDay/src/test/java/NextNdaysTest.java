import jxl.Workbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class NextNdaysTest {

    private int mCount = 0;

    BigInteger year = new BigInteger("0");
    int month = 0;
    int day = 0;
    BigInteger n = new BigInteger("0");
    String expected = null;


    @Parameterized.Parameters
    public static Collection<Object[]> test(){

        File file = null;
        file = new File("D:/testcase.xls");
        List<Date> dates = new ArrayList<Date>();

        try {
            Workbook wb = ReadExcel.getWorkBook(file);
            String[][] area_data = ReadExcel.readExcelAreaData(wb, 0, 2, 19, 1, 5);
            for (int i = 0; i < area_data.length; i++) {
                for (int j = 0; j < area_data[i].length; j++) {
                    System.out.print(area_data[i][j] + ",");
                }
                System.out.println();
            }
            for (int i = 1; i < area_data.length; i++) {
                Date date = new Date(new BigInteger(area_data[i][0].trim()),
                        Integer.parseInt(area_data[i][1].trim()), Integer.parseInt(area_data[i][2].trim())
                        , new BigInteger(area_data[i][3].trim()), area_data[i][4]);
                dates.add(date);
            }

            wb.close();//关闭工作流
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] objects = new Object[dates.size()][5];
        Date date;
        for (int i = 0; i < dates.size(); i++) {
            date = dates.get(i);
            objects[i][0] = date.getExpectedDate();
            objects[i][1] = date.getYear();
            objects[i][2] = date.getMonth();
            objects[i][3] = date.getDate();
            objects[i][4] = date.getN();
        }
        return Arrays.asList(objects);
    }

    public NextNdaysTest(String expected, BigInteger year, int month, int day, BigInteger n){
        this.expected=expected;
        this.year= year;
        this.month= month;
        this.day = day;
        this.n = n;
    }


    @Test (expected = IllegalArgumentException.class)
    public void test1() throws Exception {
        try {
            Assert.assertEquals(expected , new NextNDay().nextNDays(year, month, day, n));
        } catch (Exception e) {
            Assert.assertEquals(expected , e.getMessage().toString());
        }
        throw new IllegalArgumentException();
    }
}
