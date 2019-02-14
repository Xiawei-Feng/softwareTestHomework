package com.zju.next;

import java.io.IOException;
import java.util.Arrays;  
import java.util.Collection; 

import org.junit.Assert;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.junit.runners.Parameterized;  
import org.junit.runners.Parameterized.Parameters;

import jxl.read.biff.BiffException;


@RunWith(Parameterized.class) 
public class nextNdaysTest {
	
	private String year; 
    private String month;
    private String day;
    private String n;
    private String expected;
	
	@Parameters  
    @SuppressWarnings("unchecked")
    public static Collection prepareData() throws BiffException, IOException {
		Object [][] object = {
        		{"0", "1", "1", "10", "输入参数错误"},
        		{"2018", "13",    "1", "10", "输入参数错误"},
        		{"2018", "12", "-2", "10", "输入参数错误"},
        		{"2018", "12", "33", "10", "输入参数错误"},
        		{"2018", "2", "30", "10", "输入参数错误"},
        		{"2018", "12", "3", "-10", "输入参数错误"},
        		{"1500", "-2", "20", "1", "输入参数错误"},
        		{"2016", "2", "29", "1", "2016-3-1"},
        		{"2018", "12", "20", "12", "2019-1-1"},
        		{"1582", "10", "4", "1", "1582-10-15"},
        		{"1582", "10", "6", "16", "1582-11-1"},
        		{"1582", "9", "1", "40", "1582-10-21"},
        		{"1582", "10", "16", "0", "1582-10-16"},
        		{"1501", "9", "1", "20", "1501-9-21"},
        		{"2015", "2", "1", "30", "2015-3-3"},
        		{"9223372036854775808", "1", "2", "28", "超出范围"},
        		{"9223372036854775806", "1", "1", "9223372036854775808", "超出范围"},
        		{"9223372036854775807", "12", "31", "1", "超出范围"},
        		{"2000", "11", "29", "99999999", "275791-8-11"},
        		{"-1", "12", "31", "1", "1-1-1"},
        		{"400", "1", "1", "146100", "800-1-1"},
        		{"-401", "9", "1", "146100", "-1-9-1"},
        		{"1582", "12", "2", "30", "1583-1-1"},
        		{"1582", "9", "6", "1", "1582-9-7"},
        		{"1582", "10", "1", "0", "1582-10-1"},
        		{"1582", "8", "1", "36", "1582-9-6"}};
        return Arrays.asList(object);  
    }
	
	public nextNdaysTest(String year, String month, String day, String n, String expected){  
        this.year = year;
        this.month = month;
        this.day = day;
        this.n = n;
        this.expected = expected;
    }
	
    @Test
    public void testAdd() {
    	nextNdays next = new nextNdays();
        String result = next.nextndays(year, month, day, n);  
        Assert.assertEquals(expected,result);
    }
    
}