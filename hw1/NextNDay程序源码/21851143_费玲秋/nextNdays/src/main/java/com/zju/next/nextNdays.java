package com.zju.next;

public class nextNdays {
	static int[] gl = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
//	public static void main(String[] args) {
//		Scanner in = new Scanner(System.in);
//		String y1 = in.next();
//		String m1 = in.next();
//		String d1 = in.next();
//		String n = in.next();
//		
//		System.out.println(nextndays(y1, m1, d1, n));
//	}

	public static void IsLeapYear(long y1) {
		if(y1 > 0 && y1 <= 1582)
			if(y1 % 4 == 0) gl[1] = 29;
			else gl[1] = 28;
		else if(y1 < 0) {
			y1 *= -1;
			if(y1 % 4 == 1) gl[1] = 29;
			else gl[1] = 28;
		}else {
			if((y1 % 4 == 0 && y1 % 100 != 0) || (y1 % 100 == 0 && y1 % 400 == 0))
				gl[1] = 29;
			else gl[1] = 28;
		}
	}
	
	public static int IsIllegal(String y, String m, String d, String n) {
		try {
			long y1 = Long.parseLong(y);
			int m1 = Integer.parseInt(m);
			long d1 = Long.parseLong(d);
			long n1 = Long.parseLong(n);
			
			IsLeapYear(y1);
			if(y1 == 0 || (m1 <= 0 || m1 > 12) || d1 <= 0 || n1 < 0) return 2;
			if(d1 > gl[m1-1]) return 2;
			return 0;
		}catch(Exception e) {
			return 1;
		}
	}
	
	public String nextndays(String y, String m, String d, String n1) {
		int f = IsIllegal(y, m, d, n1);
		if(f == 1) return "超出范围";
		else if(f == 2) return "输入参数错误";
		
		long y1 = Long.parseLong(y), d1 = Long.parseLong(d), n = Long.parseLong(n1);
		int m1 = Integer.parseInt(m);
		long y2 = y1, d2 = d1 + n;
		int m2 = m1;
		boolean flag = true;
		String res = "";
		if(y1 == 1582 && m1 == 10 && (d1 <= 14 && d1 >= 5)) d2 = d1 = d1 + 10;
		if(y1 < 1582 || (y1 == 1582 && m1 < 10) || (y1 == 1582 && m1 == 10 && d1 <= 4)) {
			IsLeapYear(y1);
			if(d2 <= gl[m1-1]) {
				d2 = d1 + n;
				if(y2 == 1582 && m2 == 10 && d2 > 4) {
					long tmp = d2 - 5;
					d2 = 15 + tmp;
				}
			}else {
				while(d2 > gl[m1-1]) {
					if(y2 == 1582 && m2 == 9 && d2 - gl[m1-1] > 4) {
						long tmp = d2 - (gl[8] + 5);
						d2 = 15 + tmp;
						m2 = 10;
						n = 0;
						
						res = glndays(y2, m2, d2, n);
						flag = false;
						break;
					}
					
					IsLeapYear(y2);
					d2 = d2 - gl[m1-1];
					n = n - gl[m1-1];
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
	
	public static String glndays(long y1, int m1, long d1, long n) {
		long y2 = y1, d2 = d1 + n; 
		int m2 = m1;
		IsLeapYear(y2);
		if(d2 <= gl[m1-1]) {
			d2 = d1 + n;
		}else {
			while(d2 > gl[m1-1]) {
				IsLeapYear(y2);
				d2 = d2 - gl[m1-1];
				m2 = m1 + 1;
				if(m2 > 12) {
					m2 = 1;
					y2++;
					if(y2 < 0)	return "超出范围";
				}
				m1++;
				if(m1 > 12) m1 = 1;
			}
		}
		return y2 + "-" + m2 + "-" + d2;
	}
}
