public class NextDayDemo {

	private static int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	private static void checkLeapYear(long month) {
		if(month > 0 && month <= 1582)
			if(month % 4 == 0) daysOfMonth[1] = 29;
			else daysOfMonth[1] = 28;
		else if(month < 0) {
			month *= -1;
			if(month % 4 == 1) daysOfMonth[1] = 29;
			else daysOfMonth[1] = 28;
		}else {
			if((month % 4 == 0 && month % 100 != 0) || (month % 100 == 0 && month % 400 == 0))
				daysOfMonth[1] = 29;
			else daysOfMonth[1] = 28;
		}
	}
	
	private static int cgeckDateIllegal(String y, String m, String d, String n) {
		try {
			long y1 = Long.parseLong(y);
			int m1 = Integer.parseInt(m);
			long d1 = Long.parseLong(d);
			long n1 = Long.parseLong(n);
			checkLeapYear(y1);
			if(y1 == 0 || (m1 <= 0 || m1 > 12) || d1 <= 0 || n1 < 0) return 2;
			if(d1 > daysOfMonth[m1-1]) return 2;
			return 0;
		}catch(Exception e) {
			return 1;
		}
	}
	
	public String nextndays(String year, String month, String day, String n1) {
		int f = cgeckDateIllegal(year, month, day, n1);
		if(f == 1) return "超出范围";
		else if(f == 2) return "输入参数错误";
		long y1 = Long.parseLong(year), d1 = Long.parseLong(day), n = Long.parseLong(n1);
		int m1 = Integer.parseInt(month);
		long y2 = y1, d2 = d1 + n;
		int m2 = m1;
		boolean flag = true;
		String res = "";
		if(y1 == 1582 && m1 == 10 && (d1 <= 14 && d1 >= 5)) d2 = d1 = d1 + 10;
		if(y1 < 1582 || (y1 == 1582 && m1 < 10) || (y1 == 1582 && m1 == 10 && d1 <= 4)) {
			checkLeapYear(y1);
			if(d2 <= daysOfMonth[m1-1]) {
				d2 = d1 + n;
				if(y2 == 1582 && m2 == 10 && d2 > 4) {
					long tmp = d2 - 5;
					d2 = 15 + tmp;
				}
			}else {
				while(d2 > daysOfMonth[m1-1]) {
					if(y2 == 1582 && m2 == 9 && d2 - daysOfMonth[m1-1] > 4) {
						long tmp = d2 - (daysOfMonth[8] + 5);
						d2 = 15 + tmp;
						m2 = 10;
						n = 0;
						res = glndays(y2, m2, d2, n);
						flag = false;
						break;
					}
					checkLeapYear(y2);
					d2 = d2 - daysOfMonth[m1-1];
					n = n - daysOfMonth[m1-1];
					m2 = m1 + 1;
					if(m2 > 12) {
						m2 = 1;
						y2++;
						if(y2 == 0) y2 += 1;
					}
					m1++;
					if(m1 > 12) m1 = 1;
				}
			}
			if(flag) res = y2 + "-" + m2 + "-" + d2;
		}else {
			res = glndays(y1, m1, d1, n);
		}
		return res;
	}
	
	private static String glndays(long year, int month, long day, long n) {
		long newYear = year;
        long newDay = day + n;
		int newMonth = month;
		checkLeapYear(newYear);
		if(newDay <= daysOfMonth[month-1]) {
			newDay = day + n;
		}else {
			while(newDay > daysOfMonth[month-1]) {
				checkLeapYear(newYear);
				newDay = newDay - daysOfMonth[month-1];
				newMonth = month + 1;
				if(newMonth > 12) {
					newMonth = 1;
					newYear++;
					if(newYear < 0)	return "超出范围";
				}
				month++;
				if(month > 12) month = 1;
			}
		}
		return newYear + "-" + newMonth + "-" + newDay;
	}
}
