package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorBounsHisDao;
import com.iteye.tianshi.web.model.base.TDistributorBounsHis;
import com.iteye.tianshi.web.service.base.TDistributorBounsHisService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorBounsHisServiceImpl extends BaseServiceImpl<TDistributorBounsHis, Long> implements TDistributorBounsHisService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorBounsHisDao distributorBounsHisDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributorBounsHis, Long> getGenericDao() {
		return this.distributorBounsHisDao;
	}
	
}
