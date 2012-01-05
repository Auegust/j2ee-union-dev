package com.iteye.tianshi.web.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.UserDao;
import com.iteye.tianshi.web.model.base.User;
import com.iteye.tianshi.web.service.base.UserService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	//~ Instance fields ================================================================================================
	@Autowired
	private UserDao userDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<User, Long> getGenericDao() {
		return this.userDao;
	}
	
}
