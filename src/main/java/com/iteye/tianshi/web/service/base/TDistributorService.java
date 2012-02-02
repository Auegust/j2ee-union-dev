package com.iteye.tianshi.web.service.base;

import java.util.List;

import com.iteye.tianshi.core.web.service.BaseService;
import com.iteye.tianshi.web.model.base.TDistributor;

/**
 * 经销商信息表
 * @datetime 2010-8-9 上午10:34:58
 * @author jiangzx@yahoo.com
 */
public interface TDistributorService extends BaseService<TDistributor, Long> {
	/***
	 * 查询表无记录
	 * @return
	 */
    public final static String SQL_COUNT = TDistributorService.class.getName()+".hasZeroRecord";
    
    /**
	 * 获取指定节点下 所有间接子节点
	 */
	public List<TDistributor> findAllIndirChildrenDistributors(Long id,Integer floors);
	
	/**
	 * 获取指定节点下 所有直接子节点
	 */
	public List<TDistributor> findAllDirChildrenDistributors(Long id,Integer floors);
	/***
	 * 是否空记录
	 * @return
	 */
	public boolean hasZeroRecord();
	
	/****
	 * 删除经销商级联信息表
	 * ①奖金核算信息表
	 * ②奖金核算信息历史表
	 * ③经销商业绩表
	 * ④经销商业绩历史表
	 * @param id
	 */
	public void deleteAllCascadeTable(Long id);
}
