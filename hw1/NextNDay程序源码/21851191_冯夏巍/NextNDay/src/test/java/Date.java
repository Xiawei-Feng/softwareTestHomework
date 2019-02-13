import java.math.BigInteger;

public class Date {
    private BigInteger year;
    private int month;
    private int date;
    private BigInteger n;
    private String expectedDate;

    public Date(BigInteger year, int month, int date, BigInteger n, String expected_date) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.n = n;
        this.expectedDate = expected_date;
    }

    public BigInteger getN() {
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

    public BigInteger getYear() {
        return year;
    }
}
