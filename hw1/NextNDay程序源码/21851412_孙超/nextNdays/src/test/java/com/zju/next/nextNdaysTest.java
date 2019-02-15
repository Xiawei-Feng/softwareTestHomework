package com.zju.next;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class nextNdaysTest {
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	//日期查询结果参见网站http://wannianli.fkcha.com/
	//1582年后，往后测

	@Test
	public void test1_1() {
		assertEquals("2019-2-15", NextNDay.nextNDay(2019,2,14,1).toString());
	}
	//下一月
	@Test
	public void test1_2() {
		assertEquals("2018-12-1", NextNDay.nextNDay(2018,11,30,1).toString());
	}
	//下一年
	@Test
	public void test1_3() {
		assertEquals("2019-1-1", NextNDay.nextNDay(2018,12,31,1).toString());
	}
	//非闰年2月
	@Test
	public void test1_4() {
		assertEquals("2018-3-1", NextNDay.nextNDay(2018,2,28,1).toString());
	}
	//n为若干天，本月
	@Test
	public void test1_5() {
		assertEquals("2018-3-11", NextNDay.nextNDay(2018,3,1,10).toString());
	}
	//n为若干天，下一月
	@Test
	public void test1_6() {
		assertEquals("2018-4-1", NextNDay.nextNDay(2018,3,1,31).toString());
	}
	//n为若干天，下一年
	@Test
	public void test1_7() {
		assertEquals("2018-3-1", NextNDay.nextNDay(2017,3,1,365).toString());
	}
	//n为未来很远的某一天
	@Test
	public void test1_8() {
		assertEquals("9999-2-5", NextNDay.nextNDay(2017,12,4,2915063).toString());
	}

	//1582年10月
	//4日后一天
	@Test
	public void test2_1() {
		assertEquals("1582-10-15", NextNDay.nextNDay(1582,10,4,1).toString());
	}

	@Test
	public void test2_2() {
		assertEquals("1582-11-1", NextNDay.nextNDay(1582,10,1,21).toString());
	}


	@Test
	public void test2_3() {
		assertEquals("1582-10-16", NextNDay.nextNDay(1582,10,15,1).toString());
	}
	@Test
	public void test2_4() {
		assertEquals("2016-2-29", NextNDay.nextNDay(2016,2,28,1).toString());
	}

	@Test
	public void test2_5() {
		assertEquals("4237-12-7", NextNDay.nextNDay(1500,1,1,1000000).toString());
	}
	//1年1月1日到1582年10月4日后某一天
	@Test
	public void test3_1() {
		assertEquals("2017-12-5", NextNDay.nextNDay(1,1,1,736669).toString());
	}


	//公元前到公元1582年，4年一次闰年
	//1500年是闰年
	@Test
	public void test3_2() {
		assertEquals("1500-2-29", NextNDay.nextNDay(1500,2,28,1).toString());
	}

	@Test
	public void test3_3() {
		assertEquals("1900-3-1", NextNDay.nextNDay(1900,2,28,1).toString());
	}


	@Test
	public void test3_4() {
		assertEquals("2000-2-29", NextNDay.nextNDay(2000,2,28,1).toString());
	}

	@Test
	public void test3_5() {
		assertEquals("-1-2-29", NextNDay.nextNDay(-1,2,28,1).toString());
	}


	//非法输入
	//0年不存在
	@Test(expected = IllegalArgumentException.class)
	public void test4_1() {
		assertEquals("输入参数错误", NextNDay.nextNDay(0,1,1,1).toString());	
	}
	//13月不存在
	@Test(expected = IllegalArgumentException.class)
	public void test4_2() {
		assertEquals("输入参数错误", NextNDay.nextNDay(1,13,1,1).toString());	
	}
	//日期超出范围
	@Test(expected = IllegalArgumentException.class)
	public void test4_3() {
		assertEquals("输入参数错误", NextNDay.nextNDay(1,4,31,1).toString());	
	}
	//非闰年
	@Test(expected = IllegalArgumentException.class)
	public void test4_4() {
		assertEquals("输入参数错误", NextNDay.nextNDay(1700,2,29,1).toString());	
	}
	//非法输入
	//0年不存在
	@Test(expected = IllegalArgumentException.class)
	public void test5_1() {
		assertEquals("输入参数错误", NextNDay.nextNDay(0,1,1,1).toString());
	}
	//13月不存在
	@Test(expected = IllegalArgumentException.class)
	public void test5_2() {
		assertEquals("输入参数错误", NextNDay.nextNDay(1,13,1,1).toString());
	}
	//日期超出范围
	@Test(expected = IllegalArgumentException.class)
	public void test5_3() {
		assertEquals("输入参数错误", NextNDay.nextNDay(1,4,31,1).toString());
	}
	//非闰年
	@Test(expected = IllegalArgumentException.class)
	public void test5_4() {
		assertEquals("输入参数错误", NextNDay.nextNDay(1700,2,29,1).toString());
	}


}
