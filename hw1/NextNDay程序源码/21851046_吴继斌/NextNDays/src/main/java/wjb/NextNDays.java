package wjb;

public class NextNDays {
    private static final int[][] DAYS = {{365, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
            {366, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}};
    private static final int DAY_LOOP = 146097;

    private long year;
    private int month;
    private int day;

    public int isLeapYear() {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public String nextNDays(long inYear, int inMonth, int inDay, long inN) {
        this.year = inYear;
        this.month = inMonth;
        this.day = inDay;
        if (year < 0) {
            year++;
        }
        if (inYear == 0 || inMonth < 1 || inMonth > 12 || inDay < 1 || inDay > DAYS[isLeapYear()][inMonth]) {
            throw new IllegalArgumentException("输入参数错误");
        }
        //400年为一个循环
        year += (inN / DAY_LOOP) * 400;
        long n = inN % DAY_LOOP;
        if (inN > 0) {
            for (int i = 0; i < n; i++) {
                nextDay();
            }
            //year正溢出
            if (inYear > 0 && year < 0) {
                throw new IllegalArgumentException("超出范围");
            }
        } else if (inN < 0) {
            for (int i = 0; i > n; i--) {
                lastDay();
            }
            //year负溢出
            if (inYear < 0 && year - 1 > 0) {
                throw new IllegalArgumentException("超出范围");
            }
        }
        if (year <= 0) {
            year--;
        }
        return year + "-" + month + "-" + day;
    }

    public void nextDay() {
        day++;
        if (day > DAYS[isLeapYear()][month]) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
    }

    public void lastDay() {
        day--;
        if (day < 1) {
            month--;
            if (month < 1) {
                month = 12;
                year--;
            }
            day = DAYS[isLeapYear()][month];
        }
    }
}