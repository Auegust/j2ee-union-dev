package com.iteye.tianshi;

/**
 * 普通单元测试类
 * 
 * @author jiangzx@yaohoo.com
 * @Date 2012-1-11
 */

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
		Integer a= new Integer(4);
		System.out.println(a==4);
    }
	
	@Test public void test2() {
	       
    }
}
