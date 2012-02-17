/**
 * Copyright (c) 2011, SuZhou USTC Star Information Technology CO.LTD
 * All Rights Reserved.
 */

package com.iteye.tianshi;


import java.sql.Date;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.iteye.tianshi.core.spring.SpringApplicationContextHolder;
import com.iteye.tianshi.web.controller.base.TDistributorGradeController;
import com.iteye.tianshi.web.dao.base.TDistributorDao;
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
	
	@Autowired    
	TDistributorDao tDistributorDao;
//	CacheManager cacheManager; 
//	@Autowired		
//	UserDao userdao;
	@Test
	public void TestGradeAndBonus() {
		/****
		 * 测试业绩及奖金计算
		 */
//		String sql_startdate = "SELECT MAX(achieve_date) FROM tianshi.t_distributor_grade_his";
//		Date d = tDistributorDao.getJdbcTemplate().queryForObject(sql_startdate, Date.class);
//		System.out.println(d);
//		new SimpleDateFormat("yyyy-MM-dd").format(endDate).equals(dayMax.toString()
		 SpringApplicationContextHolder.getBean(TDistributorGradeController.class).calc("2012-02-12T00:00:00");	
//		String sql_batch  = "SELECT MAX(batch_no) FROM tianshi.t_distributor_grade_his";
//		int maxBatchNo = tDistributorDao.getJdbcTemplate().queryForObject(sql_batch,new RowMapper(){
//			@Override
//			public Object mapRow(ResultSet rs, int rowNumber) throws SQLException {
//				return rs.getInt(1);
//			}
//		});
//		System.out.println(maxBatchNo);
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
