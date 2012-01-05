package com.iteye.tianshi.core.spring;

import org.springframework.expression.ParserContext;

/**
 *
 * @datetime 2010-8-25 上午11:23:09
 * @author jiangzx@yahoo.com
 */
public class TemplatedParserContext implements ParserContext {

	public String getExpressionPrefix() {
		return "${";
	}

	public String getExpressionSuffix() {
		return "}";
	}

	public boolean isTemplate() {
		return true;
	}
}
