package com.iteye.tianshi.web.service.base;

import java.util.List;

import com.iteye.tianshi.core.web.service.BaseService;
import com.iteye.tianshi.web.model.base.TDistributor;

/**
 * 经销商信息表 --接口
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
	 * 获取指定节点下 所有子节点
	 * @dateime 2012-1-15 下午05:51:53
	 * @author chenfengming456@163.com
	 * @param id  指定节点的id
	 * @param floors 指定从哪个层级开始向下查询
	 * @return
	 */
	public List<TDistributor> findAllChildrenDistributors(Long id,Integer floors);
	
	/***
	 * 是否空记录
	 * @return
	 */
	public boolean hasZeroRecord();
}
