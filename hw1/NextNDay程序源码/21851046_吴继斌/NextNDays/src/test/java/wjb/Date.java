package wjb;

public class Date {
    private long year;
    private int month;
    private int date;
    private long n;
    private String expectedDate;

    public Date(long year, int month, int date, long n, String expected_date) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.n = n;
        this.expectedDate = expected_date;
    }

    public long getN() {
        return n;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public int getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public long getYear() {
        return year;
    }
}
