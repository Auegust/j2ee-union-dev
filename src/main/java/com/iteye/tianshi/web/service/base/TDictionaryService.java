package com.iteye.tianshi.web.service.base;

import java.util.List;

import com.iteye.tianshi.core.web.service.BaseService;
import com.iteye.tianshi.web.model.base.TDictionary;

/**
 * 字典表 ----接口
 * @datetime 2010-8-8 上午10:34:58
 * @author jiangzx@yahoo.com
 */
public interface TDictionaryService extends BaseService<TDictionary, Long> {
	public List<TDictionary> queryDictionarys(Long dictTypeId);
}
