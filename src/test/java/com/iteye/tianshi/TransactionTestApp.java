/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package com.iteye.tianshi;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.iteye.tianshi.web.dao.base.UserDao;
import com.iteye.tianshi.web.model.base.User;
import com.iteye.tianshi.web.service.base.UserService;

/**
 * Spring单元测试类
 * defaultRollback = true  回滚
 *                 = false 不回滚
 * @author   jiangzx@yaohoo.com
 * @Date	 2012-1-11 
 */
@ContextConfiguration(locations = { "resource-core-context.xml" })
@TransactionConfiguration(defaultRollback=false)
public class TransactionTestApp extends AbstractTransactionalJUnit4SpringContextTests{
	//你可以在这里注入
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userdao;
	//你可以在这个方法里测试注入的Service或者Dao
	@Test
	public void transactionTestApp() {
		//插入一个简单的对象
		User user = new User();
		user.setUsername("test1");
		user.setPassword("test");
		userService.insertEntity(user);
		//System.out.println("新增成功,回滚测试"); //需要设置  defaultRollback=true
		//jdbcTemplate 用法
		List<Map<String,Object>> results = userdao.getJdbcTemplate().queryForList(CommonSQL.getUser, "test");
		if(!results.isEmpty()){
			System.out.println("test用户的密码是"+results.get(0).get("PASSWORD"));
		}
		
	}
	
}

