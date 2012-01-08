package com.iteye.tianshi.web.dao.base.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iteye.tianshi.core.web.dao.GenericDaoImpl;
import com.iteye.tianshi.web.dao.base.TBounsConfDao;
import com.iteye.tianshi.web.model.base.TBounsConf;

/**
 *
 * @datetime 2010-8-8 下午04:42:05
 * @author jiangzx@yahoo.com
 */
@Repository
public class TBounsConfDaoImpl extends GenericDaoImpl<TBounsConf, Long> implements TBounsConfDao {
	//~ Instance fields ================================================================================================
	
	//~ Constructors ===================================================================================================
	@Autowired
    public TBounsConfDaoImpl(SessionFactory sessionFactory){
        this.setSessionFactory(sessionFactory);
    }
	
	//~ Methods ========================================================================================================
}
