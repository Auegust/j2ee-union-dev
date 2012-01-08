package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorRankDao;
import com.iteye.tianshi.web.model.base.TDistributorRank;
import com.iteye.tianshi.web.service.base.TDistributorRankService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorRankServiceImpl extends BaseServiceImpl<TDistributorRank, Long> implements TDistributorRankService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorRankDao distributorRankDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributorRank, Long> getGenericDao() {
		return this.distributorRankDao;
	}
	
}
