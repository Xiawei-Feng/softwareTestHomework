import java.math.BigInteger;

public class NextNDay {
    private static final int[][] DAYS = {{365, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
            {366, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}};
    private static final BigInteger DAY_LOOP = new BigInteger("146097");//400年
    private static final BigInteger JULIA_DAY_LOOP = new BigInteger("146010");//儒略历400年
    private static final BigInteger zero = new BigInteger("0");
    private static final BigInteger one = new BigInteger("1");
    private static final BigInteger four = new BigInteger("4");
    private static final BigInteger onehundred = new BigInteger("100");
    private static final BigInteger fourhundred = new BigInteger("400");
    private static final BigInteger boundary = new BigInteger("1582");//格历高历与儒略历分界线
    private static final BigInteger leapYearBoundary=new BigInteger("1182");
    private static final BigInteger boundaryYearDays = new BigInteger("355");//1582年只有355天
    private static final BigInteger leapYearDays = new BigInteger("366");
    private static final BigInteger commonYearDays = new BigInteger("365");

    private BigInteger year;
    private int month;
    private int day;



    public int isLeapYear() {
        //1582年及之前采用儒略历
        if(year.compareTo(boundary) < 0 ){
            if(year.remainder(four).equals(zero) && year.compareTo(zero)!=0) {
                return 1;
            }else {
                return 0;
            }
        }
        //1582年之后采用格里高历
        if (( (year.remainder(four)).equals(zero)  && (year.remainder(onehundred)).compareTo(zero) != 0 ) || (year.remainder(fourhundred)).equals(zero)) {
            return 1;
        } else {
            return 0;
        }
    }

    public void formatDay(){
        if(year.compareTo(boundary) == 0 && month ==10 && day>4 && day<15){
            day+=10;
        }
    }

    public int addYear(BigInteger n){
        while(n.compareTo(leapYearDays)>0){
            if(year.compareTo(boundary)==0){
                n=n.subtract(boundaryYearDays);
                year=year.add(one);
            }
            else if(isLeapYear()==1){
                n=n.subtract(leapYearDays);
                year=year.add(one);
            }else {
                n=n.subtract(commonYearDays);
                year=year.add(one);
            }
        }
        return n.intValue();
    }

    public String nextNDays(BigInteger y, int m, int d, BigInteger n) {
        this.year = y;
        this.month = m;
        this.day = d;
        if (year.compareTo(zero) == -1) {
            year = year.add(one);
        }
        if (y.equals(zero) || m < 1 || m > 12 || d < 1 || d > DAYS[isLeapYear()][m]) {
            throw new IllegalArgumentException("输入参数错误");
        }
        formatDay();

        //BigInteger i = new BigInteger("1");
        int next = addYear(n);
        for(int i=1;i<=next;++i){
            nextDay();
        }

        if(year.compareTo(zero) != 1){
            year = year.subtract(one);
        }
        System.out.println(year + "-" + month + "-" + day);
        return year + "-" + month + "-" + day;
    }

    public void nextDay() {
        day++;
        if(year.compareTo(boundary) == 0 && month == 10 && day == 5){
            day =15;
            return;
        }
        if (day > DAYS[isLeapYear()][month]) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year = year.add(one);
            }
        }
    }

}