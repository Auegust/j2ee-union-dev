package com.iteye.tianshi.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UtilTool {
	
  public static long getLong(){
	  return Long.parseLong(getString());
  }
  
  public static String getString(){
   	  // 声明一个种子

      int seed[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

      // 存放生成后的数字

      StringBuffer destArray = new StringBuffer(seed.length);

      // 声明一个Random实例

      Random random = new Random();

      // 循环种子

      for (int i = 0; i < seed.length; i++) {

          // 随机得到种子中的一个位置

          int j = random.nextInt(seed.length - i);

          // 把该位置上的种子输出

          destArray.append(seed[j]);

          // 把种子中末尾的种子替换得到的种子

          seed[j] = seed[seed.length - 1 - i];

      }
      return destArray.toString();
  }
  
  /***
   * 获取第一分钟之后的时间
   * @param curDate
   * @return
   */
  public static Date getNextDate(Date curDate){
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(curDate.getTime()+ 60*1000/**24*60*60*1000*/);
      return cal.getTime();
  }
  
  /**
   * 初始化的时候定时器默认开启
   */
  public static boolean clock =false;
}
