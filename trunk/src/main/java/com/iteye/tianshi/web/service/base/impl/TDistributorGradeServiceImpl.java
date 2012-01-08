package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.model.base.TDistributorGrade;
import com.iteye.tianshi.web.service.base.TDistributorGradeService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorGradeServiceImpl extends BaseServiceImpl<TDistributorGrade, Long> implements TDistributorGradeService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorGradeDao distributorGradeDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributorGrade, Long> getGenericDao() {
		return this.distributorGradeDao;
	}
	
}
