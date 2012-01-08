package com.iteye.tianshi.web.dao.base.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iteye.tianshi.core.web.dao.GenericDaoImpl;
import com.iteye.tianshi.web.dao.base.TDictionaryTypeDao;
import com.iteye.tianshi.web.model.base.TDictionaryType;

/**
 *
 * @datetime 2010-8-8 下午04:42:05
 * @author jiangzx@yahoo.com
 */
@Repository
public class TDictionaryTypeDaoImpl extends GenericDaoImpl<TDictionaryType, Long> implements TDictionaryTypeDao {
	//~ Instance fields ================================================================================================
	
	//~ Constructors ===================================================================================================
	@Autowired
    public TDictionaryTypeDaoImpl(SessionFactory sessionFactory){
        this.setSessionFactory(sessionFactory);
    }
	
	//~ Methods ========================================================================================================
}
