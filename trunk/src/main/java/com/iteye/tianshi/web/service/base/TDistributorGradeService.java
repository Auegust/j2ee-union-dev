package com.iteye.tianshi.web.service.base;

import java.util.List;
import java.util.Map;

import com.iteye.tianshi.core.web.service.BaseService;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TDistributorGrade;

/**
 * 经销商业绩表---接口
 * @datetime 2010-8-9 上午10:34:58
 * @author jiangzx@yahoo.com
 */
public interface TDistributorGradeService extends BaseService<TDistributorGrade, Long> {

	/****
	 *   计算经销商职级
	 * @param distributorCode	经销商编号
	 * @param maxChange  		本月一次性最大消费
	 * @param tgGrade	   		该名经销商临时业绩
	 * @param tgMap		  		 经销商临时业绩缓存（因为是从下往上遍历，因此缓存的都是已经计算过的业绩）
	 * @param dirchildList 		直接下线经销商
	 */
	public void findRank(String distributorCode ,double maxChange , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList);
	
	/**
	 * 	计算小组业绩（X-Y）
	 * @param netAchieve   整网业绩
	 * @param tgGrade	        当前经销商业绩
	 * @param tgMap	                   业绩缓存（因为在计算过下线以及本人的职级后传入，所以当前缓存内已经包含他们的职级）
	 * @param dirchildList 直接下线	
	 */
	public void findCellGrade(String distributorCode , double netAchieve , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList);
}
