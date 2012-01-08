package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TBounsLeaderConfDao;
import com.iteye.tianshi.web.model.base.TBounsLeaderConf;
import com.iteye.tianshi.web.service.base.TBounsLeaderConfService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TBounsLeaderConfServiceImpl extends BaseServiceImpl<TBounsLeaderConf, Long> implements TBounsLeaderConfService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TBounsLeaderConfDao bounsLeaderConfDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TBounsLeaderConf, Long> getGenericDao() {
		return this.bounsLeaderConfDao;
	}
	
}
