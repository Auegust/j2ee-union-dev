package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TProductDetailDao;
import com.iteye.tianshi.web.model.base.TProductDetail;
import com.iteye.tianshi.web.service.base.TProductDetailService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TProductDetailServiceImpl extends BaseServiceImpl<TProductDetail, Long> implements TProductDetailService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TProductDetailDao productListDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TProductDetail, Long> getGenericDao() {
		return this.productListDao;
	}
	
}
