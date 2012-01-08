package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TShopInfoDao;
import com.iteye.tianshi.web.model.base.TShopInfo;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TShopInfoServiceImpl extends BaseServiceImpl<TShopInfo, Long> implements TShopInfoService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TShopInfoDao shopInfoDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TShopInfo, Long> getGenericDao() {
		return this.shopInfoDao;
	}
	
}
