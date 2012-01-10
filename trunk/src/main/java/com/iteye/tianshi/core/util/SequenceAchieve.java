package com.iteye.tianshi.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


/**
 * 此类是模拟sequence表机制获取sequence的工具类
 * 本类采取单例模式，保证每个sequence取值的唯一性
 * @datetime 2012-01-10 09:14:12
 * @author chenfengming@163.com
 *
 */
public class SequenceAchieve {
	
	private static  SequenceAchieve SEQUENCE_ACHIEVE = null;
	
	
	/**
	 * 私有的默认构造方法
	 */
	private SequenceAchieve(){}
	
	/**
	 * 静态公用接口，提供获取此实例的接口方法
	 * @return
	 */
	synchronized public static SequenceAchieve getInstance(){
		if (SEQUENCE_ACHIEVE == null) {
			SEQUENCE_ACHIEVE = new SequenceAchieve();
		}
		return SEQUENCE_ACHIEVE;
	}
	
	/**
	 * 字符填充方法  源串长度大于或等于所需长度则返回原串 
     * 源串长度小于所需长度则按对齐方式填充 
	 * @param source - 源串
	 * @param len - 所需长度
     * @param align - 对齐方式（0-左，1-右）
     * @param cIn - 待填充的字符
	 * @throws Exception 
	 */
	public String fillStr(String source,int len,int align,char cIn) throws Exception{
		String res = "";
		char[] reschar = new char[len];//字符数组，后面和代填充字符重组字符串
		if(source.length()>=len){//原字符串长度大于len时返回原字符串
			res = source;
		}else {
			if (align==0) {//左对齐情况
				for (int i = 0; i < source.length(); i++) {
					reschar[i] = source.charAt(i);
				}
				
				for(int k=source.length();k<len;k++){
					reschar[k] = cIn;
				}
				res = new String(reschar);
			}else if(align==1){//右对齐情况
				for(int k=0;k<len-source.length();k++){
					reschar[k] = cIn;
				}
				int count = 0;//依次取出原字符串里的字符
				for (int i = len-source.length(); i <len; i++) {
					reschar[i] = source.charAt(count++);
				}
				res = new String(reschar);
			}else {
				throw new Exception("参数填充不正确，请确认");
			}
		}
		return res;
	}
	
	/**
	 * 获取经销商编号方法
	 * @return 经销商编号，不足左补0
	 * @throws Exception 
	 */
	synchronized public String getDistributorCode() throws Exception{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sequence.properties");
		OutputStream outputStream;
		Properties properties = new Properties();
		properties.load(inputStream);
   		String code = properties.getProperty("sequence");
   		String path = this.getClass().getClassLoader().getResource("sequence.properties").getPath().substring(1).replace("%20", " ");
   		outputStream = new FileOutputStream(new File(path));
   		String codes = "sequence="+String.valueOf(Integer.valueOf(code)+1);
		outputStream.write(codes.getBytes());
		outputStream.flush();
		return fillStr(code, 8, 1, '0');
	}
	
	/**
	 * 获取专卖店编号方法
	 * @return 经销商编号，不足左补0
	 * @throws Exception 
	 */
	synchronized public String getTShopInfoCode() throws Exception{
		String tShopCode = "";
		String prefix = "CG982";
		return tShopCode;
	}
	
	/**
	 * 获取产品编号方法
	 * @return 经销商编号，不足左补0
	 * @throws Exception 
	 */
	synchronized public String getTProductInfoCode() throws Exception{
		String tProductInfoCode = "";
		String prefix = "CG982";
		int mid = 3;
		int lst = 1;
		tProductInfoCode = prefix+mid+fillStr(lst+"", 2, 1, '0');
		lst++;
		return tProductInfoCode;
	}
}
