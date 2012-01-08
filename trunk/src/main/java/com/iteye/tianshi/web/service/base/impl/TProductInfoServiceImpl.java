package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TProductInfoDao;
import com.iteye.tianshi.web.model.base.TProductInfo;
import com.iteye.tianshi.web.service.base.TProductInfoService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TProductInfoServiceImpl extends BaseServiceImpl<TProductInfo, Long> implements TProductInfoService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TProductInfoDao productInfoDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TProductInfo, Long> getGenericDao() {
		return this.productInfoDao;
	}
	
}
