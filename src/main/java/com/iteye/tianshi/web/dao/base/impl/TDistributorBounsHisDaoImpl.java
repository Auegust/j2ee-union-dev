package com.iteye.tianshi.web.dao.base.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iteye.tianshi.core.web.dao.GenericDaoImpl;
import com.iteye.tianshi.web.dao.base.TDistributorBounsHisDao;
import com.iteye.tianshi.web.model.base.TDistributorBounsHis;

/**
 *
 * @datetime 2010-8-8 下午04:42:05
 * @author jiangzx@yahoo.com
 */
@Repository
public class TDistributorBounsHisDaoImpl extends GenericDaoImpl<TDistributorBounsHis, Long> implements TDistributorBounsHisDao {
	//~ Instance fields ================================================================================================
	
	//~ Constructors ===================================================================================================
	@Autowired
    public TDistributorBounsHisDaoImpl(SessionFactory sessionFactory){
        this.setSessionFactory(sessionFactory);
    }
	
	//~ Methods ========================================================================================================
}
