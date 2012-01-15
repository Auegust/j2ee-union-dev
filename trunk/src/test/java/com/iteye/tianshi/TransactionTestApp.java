/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package com.iteye.tianshi;


import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheManager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.iteye.tianshi.web.dao.base.UserDao;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.UserService;

/**
 * Spring单元测试类 defaultRollback = true 回滚 = false 不回滚
 * 
 * @author jiangzx@yaohoo.com
 * @Date 2012-1-11
 */
@ContextConfiguration(locations = { "resource-core-context.xml" })
@TransactionConfiguration(defaultRollback = false)
public class TransactionTestApp extends
		AbstractTransactionalJUnit4SpringContextTests {
	private final static String sqlCount = "select count(*) from tianshi.t_distributor";
	// 你可以在这里注入
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userdao;
	@Autowired
	private TDistributorService distributorService;
	@Autowired    
	CacheManager cacheManager; 
	// 你可以在这个方法里测试注入的Service或者Dao
	@Test
	public void transactionTestApp() {
		// //插入一个简单的对象
		// User user = new User();
		// user.setUsername("test1");
		// user.setPassword("test");
		// userService.insertEntity(user);
		// //System.out.println("新增成功,回滚测试"); //需要设置 defaultRollback=true
		// //jdbcTemplate 用法
		// List<Map<String,Object>> results =
		// userdao.getJdbcTemplate().queryForList(CommonSQL.getUser, "test");
		// if(!results.isEmpty()){
		// System.out.println("test用户的密码是"+results.get(0).get("PASSWORD"));
		// }
		// int count = userdao.getJdbcTemplate().queryForInt(sqlCount);
		// System.out.println(count);

	}
//ehcache test
	@Test
	public void testCaching_MessagesCache() {
		//UserService userServiceDelegate = Mockito.mock(UserService.class);
//		userService.findEntity(1L);
//		userService.findEntity(2L);
//		userService.findEntity(1L);
//		userService.findEntity(1L);
//		cacheManager.clearAll();
		List<TDistributor> indirectChildList = new ArrayList<TDistributor>();
		//待查询的id 比如这里查询1的间接节点
		Long id = 6L;
		//当前职级
//		//int level = distributorService.findEntity(id).getFloors();
//		List<TDistributor>  directChild = distributorService.findByProperty("sponsorId", id);
//		 if(!directChild.isEmpty()){
//			 //这里是直接节点，所以不加进列表
//			 for(TDistributor aDistributor : directChild){
//				 getChildList(aDistributor.getId(),indirectChildList);
//				}
//		 }
//		 System.out.println(indirectChildList.size());
		List<TDistributor> list = distributorService.findAllChildrenDistributors(1L, 3);
		System.out.println(list.size());
	}
	
//	/***
//	 * 迭代
//	 * @param id
//	 * @param indirectChildList
//	 */
//	private void getChildList(Long id ,List<TDistributor> indirectChildList){
//		List<TDistributor>  directChild = distributorService.findByProperty("sponsorId", id);
//		if(!directChild.isEmpty()){
//			//因为是开始算间接业绩，所以需要加进列表
//			indirectChildList.addAll(directChild);
//			 for(TDistributor aDistributor : directChild){
//				 getChildList(aDistributor.getId() , indirectChildList);
//				}
//		 }
//	}

}
