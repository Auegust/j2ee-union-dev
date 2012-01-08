package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TBounsConfDao;
import com.iteye.tianshi.web.model.base.TBounsConf;
import com.iteye.tianshi.web.service.base.TBounsConfService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TBounsConfServiceImpl extends BaseServiceImpl<TBounsConf, Long> implements TBounsConfService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TBounsConfDao bounsConfDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TBounsConf, Long> getGenericDao() {
		return this.bounsConfDao;
	}
	
}
