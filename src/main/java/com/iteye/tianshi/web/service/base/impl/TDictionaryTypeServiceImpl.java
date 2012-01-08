package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDictionaryTypeDao;
import com.iteye.tianshi.web.model.base.TDictionaryType;
import com.iteye.tianshi.web.service.base.TDictionaryTypeService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDictionaryTypeServiceImpl extends BaseServiceImpl<TDictionaryType, Long> implements TDictionaryTypeService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDictionaryTypeDao dictionaryTypeDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDictionaryType, Long> getGenericDao() {
		return this.dictionaryTypeDao;
	}
	
}
