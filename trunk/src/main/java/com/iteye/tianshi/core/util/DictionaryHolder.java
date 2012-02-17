package com.iteye.tianshi.core.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.StringUtils;

import com.iteye.tianshi.core.spring.SpringApplicationContextHolder;
import com.iteye.tianshi.web.model.base.TDictionary;
import com.iteye.tianshi.web.service.base.TDictionaryService;

/**
 * 	数据字典工具类
 * @author  jiangzx@yahoo.com
 * @date    2012-1-8 上午11:03:34
 * @version 
 */
public class DictionaryHolder {
	/** 缓存业务字典项  */
	private static ConcurrentMap<Long, List<TDictionary>> cacheMap = new ConcurrentHashMap<Long, List<TDictionary>>();
	
	//private static Logger logger = LoggerFactory.getLogger(DictionaryHolder.class);
	
	private static TDictionaryService dictionaryService;
	
	public static void putDictionaries(Long dictTypeId, List<TDictionary> dictionaries) {
		if(dictionaries != null)
			cacheMap.put(dictTypeId, dictionaries);
	}
	
	public static List<TDictionary> getDictionaries(Long dictTypeId) {
		return cacheMap.get(dictTypeId);
	}
	
	public static void cleanDictionaries(Long dictTypeId) {
		cacheMap.remove(dictTypeId);
	}
	
	/**
	 * 转换业务字典项编码对应业务字典项值，设置给field_Name属性。
	 * 
	 * @param <T> 实体
	 * @param list 待装换实体list集合
	 * @param dictTypeId 业务字典项
	 * @param getMethod 字段get方法
	 */
	public static <T> void transfercoder(List<T> list, Long dictTypeId, String getMethod) {
		if(dictionaryService == null)
			dictionaryService = SpringApplicationContextHolder.getBean(TDictionaryService.class);
		
		List<TDictionary> dictionaries = cacheMap.get(dictTypeId);
		if(dictionaries == null) {
			//logger.info("relead {} data", dictTypeId);
			dictionaries = dictionaryService.queryDictionarys(dictTypeId);
			cacheMap.put(dictTypeId, dictionaries);
		}
		
		String[] args = getMethod.split("\\.");
		int len = args.length;
		getMethod = args[(len-1)];
		for(T t : list) {
			try {
				Object entity = t;
				//匹配objA.objB.objC.getMethodC的情况
				for(int i=0; i<(len-1); i++) {
					String name = org.apache.commons.lang.StringUtils.capitalize(args[i]);
					Method method = t.getClass().getMethod("get" + name);
					entity = method.invoke(t);
				}
				Method method = entity.getClass().getMethod(getMethod);
				String obj = String.valueOf(method.invoke(entity));
				String value = "";
				for(TDictionary dic : dictionaries) {
					if(dic.getBusiStatus().equals(obj)) {
						value = dic.getBusiName();
						break;
					}
				}
				if(StringUtils.hasText(value)) {
					method = entity.getClass().getMethod(getMethod.replace("get", "set")+"_Name", String.class);
					method.invoke(entity, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
