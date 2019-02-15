package com.zju.next;
import java.math.BigInteger;

/**
 * 输入某个日期如2017,3,10，可以得到指定n天之后（n可以为负数）的日期和星期
 * @author sc
 *
 */
public final class NextNDay{
	private static final int[][]  DaysOfMonth = 
		{new int[]{31,28,31,30,31,30,31,31,30,31,30,31},
		new int[]{31,29,31,30,31,30,31,31,30,31,30,31}};
	
	
	/**
	 * 静态内部类，是nextNDay方法的返回值类型。Date是不可变类，因此线程安全。
	 * @author sc
	 *
	 */
	public static class Date{
		final int year;
		final int month;
		final int day;
		final BigInteger days;
		
		/**
		 * 构造函数
		 * @param year	年份
		 * @param month 月份
		 * @param day   日期
		 * @throws IllegalArgumentException 如果输入不合法则抛出该异常，比如2010，22，10是不合法的
		 */
		public Date(int year,int month,int day) throws IllegalArgumentException{
			if(!isIllegalDate(year, month, day))throw new IllegalArgumentException();
			this.year = year;
			this.month = month;
			this.day = day;
			days = getDays();
			
			
		}
		
		/*
		 * 获得指定日期到公元1-1-1的天数，例如1-1-2距离1-1-1的天数是1.而-1-12-31到1-1-1的天数是-1.
		 */
		private  BigInteger getDays() {
			int isLeap = isLeapYear(year) ? 1 : 0;
			BigInteger result = BigInteger.valueOf(0);
			if(year > 0) {
				for(int y = 1;y<year;++y)
					result = result.add(BigInteger.valueOf(365 + (isLeapYear(y) ? 1 : 0)));
				for(int m = 1;m<month;++m)
					//m-1是因为，数组index是从零开始的
					result = result.add(BigInteger.valueOf(DaysOfMonth[isLeap][m-1]));
				for(int d = 1;d < day;++d)
					result = result.add(BigInteger.valueOf(1));
				if(year > 1582 
						||(year == 1582 && month > 10)
						||(year == 1582 && month == 10 && day > 14))
						result = result.subtract(BigInteger.valueOf(10));
			}else if(year < 0) {
				for(int y = year+1;y < 0;++y)
					result = result.add(BigInteger.valueOf(365 + (isLeapYear(y) ? 1 : 0)));
		//		for(int m = month;m<12;++m) {
				for(int m = 12;m>month;--m) {
					//m-1是因为，数组index是从零开始的
					result = result.add(BigInteger.valueOf(DaysOfMonth[isLeap][m-1]));
			//		System.out.println(result);
				}
				for(int d = day;d <= DaysOfMonth[isLeap][month-1];++d) {
					result = result.add(BigInteger.valueOf(1));
			//		System.out.println(result);
				}
			}
			return result;
		}
		
		/*
		 * 判断是否是合法的日期格式，例如2017-2-29,2001-33-1都是不合法的
		 */
		private static boolean isIllegalDate(int year,int month,int day) {
			if(year == 0) return false;
			int isLeap = isLeapYear(year) ? 1 : 0;
			if(month <= 0 || month > 12) return false;
			//减一是因为index从0开始
			if(day <= 0 || day > DaysOfMonth[isLeap][month-1] )return false;
			if(year==1582 && month ==10 && day>4 &&day<15) return false;
			return true;
		}
		/**
		 * 得到这一天的之后一天的Date对象
		 * @return Date 返回这一天的之后一天的Date对象
		 */
		public Date nextN(int n) {
			assert n>=0; 
			int year = this.year;
			int month = this.month;
			int day = this.day;
			for(int i = 0 ; i< n;i++) {
				int isLeap = isLeapYear(year) ? 1 : 0;
				if(day == DaysOfMonth[isLeap][month -1]){
					day = 1;
					if(month == 12) {
						year += (year==-1)? 2 : 1;
						month = 1;
					}else month++;
				}else {
					if(year == 1582 && month == 10 && day == 4)
						day+=11;
					else
						day++;
				}
			}
			return new Date(year, month, day);
		}
		/**
		 * 得到只一天之前一天的Date对象
		 * @return Date 返回只一天之前一天的Date对象
		 */
		public Date prevN(int n) {
			assert n>0;
			int year = this.year;
			int month = this.month;
			int day = this.day;
			for(int i = 0 ; i< n;i++) {
				int isLeap = isLeapYear(year) ? 1 : 0;
				if(day == 1){
					if(month == 1) {
						year -= (year==1)? 2 : 1;
						month = 12;
						day = 31;
					}else{
						month--;
						day = DaysOfMonth[isLeap][month-1];
					}
				}else {
					if(year == 1582 && month == 10 && day == 15)
						day-=11;
					else
						day--;
				}
			}
			return new Date(year, month, day);
		}
		
		@Override
		public String toString() {
			StringBuilder temp = new StringBuilder(30);
			if(year<0)temp.append("-");
			temp.append(year>0 ? year : -year);
			temp.append('-');
			temp.append(month);
			temp.append('-');
			temp.append(day);
			return temp.toString();
		}
	}
	
	/**
	 * 测试函数main
	 * @param args
	 */
	public static void main(String[] args) {
	//	System.out.println(nextNDay(-1,2,28,1));
	} 
	
	/**
	 * nextNDay方法是供外界调用的静态方法
	 * @param year	输入的年份
	 * @param month 月份
	 * @param day	日期
	 * @param n		指定计算n天之后（n可以为负数）
	 * @throws IllegalArgumentException 当输入的日期不可能存在时抛出该异常
	 */
	public static Date nextNDay(int year,int month,int day,int n) throws IllegalArgumentException{
		Date aDate = new Date(year, month, day);
		if(n>=0) {
			return aDate.nextN(n);
		}else {
			return aDate.prevN(-n);
		}
	}
	
	/*
	 * 判断指定年份是否是闰年
	 * 如果year==0则抛出异常
	 */
	private static boolean isLeapYear(int year) throws IllegalArgumentException {
		if(year > 1500)
		return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0 );
		if(year >0 && year<=1500)
			return year%4 == 0;
		if(year == 0) throw new IllegalArgumentException();
		year = -year;
		return year % 4 == 1 ;
	}
}