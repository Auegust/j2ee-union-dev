package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorGradeHisDao;
import com.iteye.tianshi.web.model.base.TDistributorGradeHis;
import com.iteye.tianshi.web.service.base.TDistributorGradeHisService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorGradeHisServiceImpl extends BaseServiceImpl<TDistributorGradeHis, Long> implements TDistributorGradeHisService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorGradeHisDao distributorGradeHisDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributorGradeHis, Long> getGenericDao() {
		return this.distributorGradeHisDao;
	}
	
}
