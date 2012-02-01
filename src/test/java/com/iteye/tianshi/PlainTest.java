package com.iteye.tianshi;

/**
 * 普通单元测试类
 * 
 * @author jiangzx@yaohoo.com
 * @Date 2012-1-11
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PlainTest {
	String curDate;
	boolean a;
	@Before
	public void initialize() throws Exception {
		//curDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
	@Test public void test1() {
		//System.out.println(curDate);
//		Integer a= new Integer(4);
//		System.out.println(a==4);
		List<String> strList = new ArrayList<String>();
		Map<String,String> map = new HashMap<String, String>();
		strList.add("tom");
		strList.add("jCk");
		int i = 0;
		for(String str : strList){
			map.put(new Integer(i+1).toString(), str);
			str = null;
		}
		strList = null;
		System.out.println(map.get("1"));
    }
	
	@Test public void test2() {
	       
    }
}
