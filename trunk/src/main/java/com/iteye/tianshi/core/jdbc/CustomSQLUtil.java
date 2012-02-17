/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package com.iteye.tianshi.core.jdbc;

import java.util.Map;

/**
 *
 * @author   jiangzx@yahoo.com
 * @Date	 2011-6-14 下午01:41:33
 */
public class CustomSQLUtil {
	//private static Logger logger = LoggerFactory.getLogger(CustomSQLUtil.class);

	private static CustomSQLUtil _instance = new CustomSQLUtil();

	private CustomSQL _customSQL;
	
	private CustomSQLUtil() {
		try {
			_customSQL = new CustomSQL();
		}
		catch (Exception e) {
			//logger.error("", e);
			e.printStackTrace();
		}
	}

	public static String appendCriteria(String sql, String criteria) {
		return _instance._customSQL.appendCriteria(sql, criteria);
	}

	public static String get(String id) {
		return _instance._customSQL.get(id);
	}
	
	public static String get(String id, Map<String, Object> model) {
		return _instance._customSQL.get(id, model);
	}
}