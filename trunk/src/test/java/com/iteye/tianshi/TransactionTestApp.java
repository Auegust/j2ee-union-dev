/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package com.iteye.tianshi;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.iteye.tianshi.core.spring.SpringApplicationContextHolder;
import com.iteye.tianshi.web.controller.base.TDistributorGradeController;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.service.base.TDistributorService;

/**
 * Spring单元测试类 defaultRollback = true 回滚 = false 不回滚
 * 
 */
@ContextConfiguration(locations = { "resource-core-context.xml" })
@TransactionConfiguration(defaultRollback = false)
public class TransactionTestApp extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired    
	TDistributorService tDistributorService;
//	CacheManager cacheManager; 
//	@Autowired		
//	UserDao userdao;
	@Test
	public void TestGradeAndBonus() {
		/****
		 * 测试业绩及奖金计算
		 */
		 SpringApplicationContextHolder.getBean(TDistributorGradeController.class).calcGradeAndBonus();		
		// List<TDistributor>  dirchildList = tDistributorService.findAllDirChildrenDistributors(0L, 2);
//		 for(TDistributor o :dirchildList){
//			 System.out.println("---"+o.getDistributorCode());
//		 }
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
