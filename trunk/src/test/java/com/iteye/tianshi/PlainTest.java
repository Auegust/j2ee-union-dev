package com.iteye.tianshi;

/**
 * 普通单元测试类
 * 
 * @author jiangzx@yaohoo.com
 * @Date 2012-1-11
 */

import java.util.HashMap;
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
//		People a = new People();
//		a.setAge("21");
//		a.setName("zhangsan");
//		a.setSex("female");
//		People b = new People();
//		b.setAge("22");
//		b.setName("zhangsan2");
//		b.setSex("male");
//		Map map = new HashMap();
//		map.put("tt", a);
//		map.put("bb", b);
//		People temp= null;
//		for(int i=0;i<2;i++){
//			if(i==0){
//				temp = (People) map.get("tt");
//				temp.setAge("33");
//			}else{
//				temp = (People) map.get("bb");
//				temp.setAge("44");
//			}
//		}
//		temp = null;
//		System.out.println("-----"+((People)map.get("tt")).getAge()+"------"+((People)map.get("bb")).getAge());
		People p = new People();
		System.out.println(p.getNumber());
		
		
    }
	
	@Test public void test2() {
	       
    }
}
