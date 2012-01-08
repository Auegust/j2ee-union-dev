package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorBounDao;
import com.iteye.tianshi.web.model.base.TDistributorBoun;
import com.iteye.tianshi.web.service.base.TDistributorBounService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorBounServiceImpl extends BaseServiceImpl<TDistributorBoun, Long> implements TDistributorBounService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorBounDao distributorBounDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributorBoun, Long> getGenericDao() {
		return this.distributorBounDao;
	}
	
}
