package com.iteye.tianshi.core.util;

/**
 * SQL 语句排序模式：降序和升序
 *
 * @datetime 2010-8-13 下午01:48:31
 * @author jiangzx@yahoo.com
 */
public enum SQLOrderMode {
	/**
	 * 升序
	 */
	ASC("ASC"),
	
	/**
	 * 降序
	 */
	DESC("DESC"),
	
	/**
	 * 
	 */
	NOSORT("NOSORT");
	
	private SQLOrderMode(String mode) {
		this.mode = mode;
	}
	
	private String mode;
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
