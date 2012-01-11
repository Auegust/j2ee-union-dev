package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.jdbc.CustomSQLUtil;
import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorDao;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.service.base.TDistributorService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorServiceImpl extends BaseServiceImpl<TDistributor, Long> implements TDistributorService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorDao distributorDao;
	//~ Constructors ===================================================================================================

	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributor, Long> getGenericDao() {
		return this.distributorDao;
	}

	@Override
	public boolean hasZeroRecord() {
		String sql = CustomSQLUtil.get(SQL_COUNT);
		int count = distributorDao.getJdbcTemplate().queryForInt(sql);
		if(count == 0){
			return true;
		}
		return false;
	}
	
}
