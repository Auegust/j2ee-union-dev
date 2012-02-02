/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package com.iteye.tianshi;


import net.sf.ehcache.CacheManager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.iteye.tianshi.core.spring.SpringApplicationContextHolder;
import com.iteye.tianshi.web.controller.base.TDistributorGradeController;
import com.iteye.tianshi.web.dao.base.UserDao;
import com.iteye.tianshi.web.service.base.TDistributorGradeService;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.TProductDetailService;
import com.iteye.tianshi.web.service.base.UserService;

/**
 * Spring单元测试类 defaultRollback = true 回滚 = false 不回滚
 * 
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
	private TDistributorGradeService gradeService;
	
	@Autowired
	private TProductDetailService productDetailService;
	@Autowired    
	CacheManager cacheManager; 
	// 你可以在这个方法里测试注入的Service或者Dao
	@Test
	public void transactionTestApp() {
		SpringApplicationContextHolder.getBean(TDistributorGradeController.class).calcGradeAndBonus();		
//		controller.calcGradeAndBonus();
//		List<Map<String, Object>>  oo =userdao.getJdbcTemplate().queryForList("SELECT * FROM t_bouns_conf");
//		System.out.println(oo.size());
	}
//ehcache test
	
	public void testCaching_MessagesCache() {
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
