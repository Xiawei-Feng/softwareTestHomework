package qiyue.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  本类支持的日期范围下限为公元前292269054年1月1日（GregorianCalendar实现的范围），上限为9223372036854775807年12月31日（最大年份
 *  为Long所能表示的最大值）
 *  本类的闰年判定规则与GregorianCalendar保持一致
 */
public class NextNDaysCalculator {

    private static final int MONTH_LENGTH[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int LEAP_MONTH_LENGTH = 29;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("y-M-d");

    private static final int THRESHOLD_YEAR = 250000000;
    private static final int GREGORIAN_MAX_YEAR = 292269054;

    private static final long DAYS_OF_400_YEARS_AFTER_1582 = 146097;

    private static final String ARGS_ERROR = "输入参数错误";
    private static final String RANGE_ERROR = "超出范围";

    public static String nextNDaysFrom(long year, int month, int day, long n) {
        if (year < -GREGORIAN_MAX_YEAR) {
            return RANGE_ERROR;
        }

        // 1582年10月特殊情况
        if (year == 1582 && month == 10 && day > 4 && day < 15) {
            day += 10;
        }

        if (checkArgs(year, month, day, n)) {
            if (year < GREGORIAN_MAX_YEAR) {
                return calculateByGregorianCalendar((int)year, month, day, n);
            } else {
                return calculateByInfiniteCalendar(year, month, day, n);
            }
        } else {
            return ARGS_ERROR;
        }
    }

    private static boolean checkArgs(long year, int month, int day, long n) {
        if (year == 0 || month < 1 || month > 12 || n < 0) {
            return false;
        }
        boolean isLeapYear;
        if (year < GREGORIAN_MAX_YEAR) {
            if (year < 0) {
                isLeapYear = new GregorianCalendar().isLeapYear((int)year + 1);
            } else {
                isLeapYear = new GregorianCalendar().isLeapYear((int)year);
            }
        } else {
            isLeapYear = isLeapYear(year);
        }
        if (month == 2 && isLeapYear) {
            boolean dayError = day < 1 || day > LEAP_MONTH_LENGTH;
            return !dayError;
        } else {
            boolean dayError = day < 1 || day > MONTH_LENGTH[month - 1];
            return !dayError;
        }
    }

    private static String calculateByGregorianCalendar(int year, int month, int day, long n) {
        // 修正GregorianCalendar公元前年份表示
        if (year < 0) {
            year += 1;
        }
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        while (n > 0 && (calendar.get(Calendar.ERA) == 0 || calendar.get(Calendar.YEAR) <= THRESHOLD_YEAR)) {
            if (n > Integer.MAX_VALUE) {
                calendar.add(Calendar.DAY_OF_MONTH, Integer.MAX_VALUE);
                n -= Integer.MAX_VALUE;
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, (int) n);
                n = 0;
            }
        }
        if (n == 0) {
            if (calendar.get(Calendar.ERA) == 1) {
                return DATE_FORMAT.format(calendar.getTime());
            } else {
                return "-" + DATE_FORMAT.format(calendar.getTime());
            }
        } else {
            return calculateByInfiniteCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH), n);
        }
    }

    private static String calculateByInfiniteCalendar(long year, int month, int day, long n) {
        // 修正到1月1日
        if (n > 0 && (month != 1 || day != 1)) {
            for (int monthIndex = month; monthIndex <= 12; monthIndex++) {
                int monthLength;
                if (monthIndex == 2 && isLeapYear(year)) {
                    monthLength = LEAP_MONTH_LENGTH;
                } else {
                    monthLength = MONTH_LENGTH[monthIndex - 1];
                }
                if (n <= monthLength - day) {
                    day += n;
                    n = 0;
                    break;
                } else {
                    n -= monthLength - day + 1;
                    month += 1;
                    day = 1;
                }
            }
            if (month == 13) {
                month = 1;
                if (year < Long.MAX_VALUE) {
                    year += 1;
                } else {
                    return RANGE_ERROR;
                }
            }
        }
        // 累加剩余天数
        if (n > 0) {
            long multiFactor = n / DAYS_OF_400_YEARS_AFTER_1582;
            long toMaxYearFactor = (Long.MAX_VALUE - year) / 400;
            if (toMaxYearFactor >= multiFactor) {
                year += 400 * multiFactor;
                n = n % DAYS_OF_400_YEARS_AFTER_1582;
            } else {
                year += 400 *toMaxYearFactor;
                n -= DAYS_OF_400_YEARS_AFTER_1582 * toMaxYearFactor;
            }
            while (n > 0) {
                int monthLength;
                if (month == 2 && isLeapYear(year)) {
                    monthLength = LEAP_MONTH_LENGTH;
                } else {
                    monthLength = MONTH_LENGTH[month - 1];
                }
                if (n <= monthLength - day) {
                    day += n;
                    n = 0;
                } else {
                    n -= monthLength - day + 1;
                    month += 1;
                    day = 1;
                    if (month == 13) {
                        month = 1;
                        if (year < Long.MAX_VALUE) {
                            year += 1;
                        } else {
                            return RANGE_ERROR;
                        }
                    }
                }
            }
        }
        return year + "-" + month + "-" + day;
    }

    // 1582年后闰年判定法
    private static boolean isLeapYear(long year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
}
