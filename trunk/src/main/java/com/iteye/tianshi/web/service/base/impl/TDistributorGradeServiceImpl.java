package com.iteye.tianshi.web.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.util.ConstantUtil;
import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TDistributorGrade;
import com.iteye.tianshi.web.model.support.RankCache;
import com.iteye.tianshi.web.service.base.TDistributorGradeService;
import com.iteye.tianshi.web.service.base.TDistributorService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorGradeServiceImpl extends BaseServiceImpl<TDistributorGrade, Long> implements TDistributorGradeService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorGradeDao distributorGradeDao;
	@Autowired
	TDistributorService tDistributorService;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributorGrade, Long> getGenericDao() {
		return this.distributorGradeDao;
	}
	
	/****
	 *   计算经销商职级
	 * @param distributorCode	经销商编号
	 * @param maxChange  		本月一次性最大消费
	 * @param tgGrade	   		该名经销商临时业绩
	 * @param tgMap		  		 经销商临时业绩缓存（因为是从下往上遍历，因此缓存的都是已经计算过的业绩）
	 * @param dirchildList 		直接下线经销商
	 */
	public void findRank(TDistributor dist , String distributorCode ,double maxChange , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList){
		double personAchieve = tgGrade.getPersonAchieve(); //个人累计即个人业绩（当月）
		double netAchieve = tgGrade.getNetAchieve();		//累计即整网
		/**下面分别针对星级的条件进行判断*/
		if(dirchildList == null){ /**经销商无下线，小组业绩==个人业绩==整网业绩，之前已经计算过了*/
			/**（1）一次性购买大于或等于1000PV （2）个人累计大于或等于1000PV */
			if(maxChange>=1000D || personAchieve>=1000D){
				dist.setRankId(ConstantUtil._lev_4);
				tgGrade.setRank(ConstantUtil._lev_4); //4*
			/**（1）一次性购买大于或等于200PV （2）个人累计大于或等于200PV */
			}else if(maxChange>=200D || personAchieve>=200D){
				dist.setRankId(ConstantUtil._lev_3);
				tgGrade.setRank(ConstantUtil._lev_3); //3*
			/**（1）一次性购买大于或等于100PV */
			}else if(maxChange>=100){
				dist.setRankId(ConstantUtil._lev_2);
				tgGrade.setRank(ConstantUtil._lev_2); //2*
			/** 个人购买  《天狮事业锦囊》一套*/
			}else{
				dist.setRankId(ConstantUtil._lev_1);
				tgGrade.setRank(ConstantUtil._lev_1); //1*
			}
		}else{ /**经销商至少有一个下线，计算职级后可以直接计算小组业绩*/
			/**遍历下属网络，统计每条网络里面的各职级经销商的个数3 4 5 6 7 8*/
			/**ownership初始化了键---职级类型，值---不同分支的职级个数*/
			Map<String , Integer > ownership =  new HashMap<String, Integer>();
			ownership.put("_lv_3", 0);
			ownership.put("_lv_4", 0);
			ownership.put("_lv_5", 0);
			ownership.put("_lv_6", 0);
			ownership.put("_lv_7", 0);
			ownership.put("_lv_8", 0);
			List<TDistributor> all = null;
			List<TDistributor> indirect = null;
			for(TDistributor child : dirchildList){
				RankCache initCache = new RankCache();
				/**查询出child下的所有子节点（包括间接节点+直接节点），TODO:这里是个大批量的操作，以后需要优化，目前没有解决思路*/
				all = tDistributorService.findAllDirChildrenDistributors(child.getId(), child.getFloors()+1);/**所有直接子节点*/
				indirect = tDistributorService.findAllChildrenDistributors(child.getId(), child.getFloors()+1);
				all.addAll(indirect); /**加入所有间接子节点*/
				all.add(child);/**加入当前节点*/
				for(TDistributor tt:all){/**遍历当前节点*/
					Long rank = tgMap.get(tt.getDistributorCode()).getRank();/**从tgGrade里获取，不能从TDistributor获取，因为还没有入库*/
					if(rank==ConstantUtil._lev_3){
						initCache.set_lv_3(true);
					}else if(rank==ConstantUtil._lev_4){
						initCache.set_lv_4(true);
					}else if(rank==ConstantUtil._lev_5){
						initCache.set_lv_5(true);
					}else if(rank==ConstantUtil._lev_6){
						initCache.set_lv_6(true);
					}else if(rank==ConstantUtil._lev_7){
						initCache.set_lv_7(true);
					}else if(rank==ConstantUtil._lev_8){
						initCache.set_lv_8(true);
					}
					tt = null;
				}
				/**处理缓存内结果，填充到ownership里面计算各个分支的职级个数*/
				if(initCache.is_lv_3()){
					ownership.put("_lv_3",ownership.get("_lv_3")+1);
				}
				if(initCache.is_lv_4()){
					ownership.put("_lv_4",ownership.get("_lv_4")+1);
				}
				if(initCache.is_lv_5()){
					ownership.put("_lv_5",ownership.get("_lv_5")+1);
				}
				if(initCache.is_lv_6()){
					ownership.put("_lv_6",ownership.get("_lv_6")+1);
				}
				if(initCache.is_lv_7()){
					ownership.put("_lv_7",ownership.get("_lv_7")+1);
				}
				if(initCache.is_lv_8()){
					ownership.put("_lv_8",ownership.get("_lv_8")+1);
				}
				/**职级在每个网络分支中的个数计算完毕，清理引用*/
				initCache = null;
				all = null;
				indirect = null;
				child = null;
			}
			/***计算经销商职级，personAchieve---本月个人累计消费，maxChange--本月最大一次性消费，netAchieve--整网累计，childNumber--N条，ownership--N星*/
			/**四名八星直销商--金狮*/
			if(ownership.get("_lv_8")>=4 ){
				dist.setRankId(ConstantUtil._lev_s_3);
				tgGrade.setRank(ConstantUtil._lev_s_3);
			/**三名八星直销商--银狮*/
			}else if(ownership.get("_lv_8")>=3){
				dist.setRankId(ConstantUtil._lev_s_2);
				tgGrade.setRank(ConstantUtil._lev_s_2);
			/**二名八星直销商--铜师*/
			}else if(ownership.get("_lv_8")>=2){
				dist.setRankId(ConstantUtil._lev_s_1);
				tgGrade.setRank(ConstantUtil._lev_s_1);
			/**（1）三条七星，累计300000PV （2）两条七星，累计600000PV （3）二条七星+四条六星，累计300000PV*/
			}else if((ownership.get("_lv_7")>=3 && netAchieve>=300000D)||
					(ownership.get("_lv_7")>=2 && netAchieve>=600000D)||
					(ownership.get("_lv_7")>=2 && ownership.get("_lv_6")>=5 && netAchieve>=300000D)){
				dist.setRankId(ConstantUtil._lev_8);
				tgGrade.setRank(ConstantUtil._lev_8);
			/**（1）三条六星，累计75000PV （2）两条六星，累计150000PV（3）二条六星+四条五星，累计75000PV（4）一条六星+六条五星，累计75000PV*/
			}else if((ownership.get("_lv_6")>=3 && netAchieve>=75000D)||
					(ownership.get("_lv_6")>=2 && netAchieve>=150000D)||
					(ownership.get("_lv_6")>=2 && ownership.get("_lv_5")>=5 && netAchieve>=75000D)||
					(ownership.get("_lv_6")>=1 && ownership.get("_lv_5")>=7 && netAchieve>=75000D)){
				dist.setRankId(ConstantUtil._lev_7);
				tgGrade.setRank(ConstantUtil._lev_7);
			/**（1）三条五星，累计18000PV；（2）两条五星，累计36000PV （3）二条五星+四条四星，累计18000PV （4）一条五星+六条四星，累计18000PV*/
			}else if((ownership.get("_lv_5")>=3 && netAchieve>=18000D)||
					(ownership.get("_lv_5")>=2 && netAchieve>=36000D)||
					(ownership.get("_lv_5")>=2 && ownership.get("_lv_4")>=5 && netAchieve>=18000D)||
					(ownership.get("_lv_5")>=1 && ownership.get("_lv_4")>=7 && netAchieve>=18000D)		){
				dist.setRankId(ConstantUtil._lev_6);
				tgGrade.setRank(ConstantUtil._lev_6);
			/**（1）三条四星，累计4000PV； （2）两条四星，累计8000PV（3）二条四星+四条三星，累计4000PV （4）一条四星+六条三星，累计4000PV*/
			}else if((ownership.get("_lv_4")>=3 && netAchieve>=4000D)||
					 (ownership.get("_lv_4")>=2 && netAchieve>=8000D)||
					 (ownership.get("_lv_4")>=2 && ownership.get("_lv_3")>=5 && netAchieve>=4000D)||
					 (ownership.get("_lv_4")>=1 && ownership.get("_lv_3")>=7 && netAchieve>=4000D)){
				dist.setRankId(ConstantUtil._lev_5);
				tgGrade.setRank(ConstantUtil._lev_5);
			/**（1）一次性购买大于或等于1000PV （2）个人累计大于或等于1000PV（3）三条三星，累计1200PV（4）二条三星，累计2400PV*/
			}else if(maxChange>=1000D||
					 personAchieve>=1000||
					 (ownership.get("_lv_3")>=3 && netAchieve>=1200D)||
					 (ownership.get("_lv_3")>=2 && netAchieve>=2400D)){
				dist.setRankId(ConstantUtil._lev_4);
				tgGrade.setRank(ConstantUtil._lev_4);
			/**(1)个人累计购买大于或等于200PV（2）一次性购买大于或等于200PV*/
			}else if(maxChange>=200D || personAchieve>=200D){
				dist.setRankId(ConstantUtil._lev_3);
				tgGrade.setRank(ConstantUtil._lev_3);
			/**一次性购买产品额大于或等于100PV*/
			}else if(maxChange>=100D){
				dist.setRankId(ConstantUtil._lev_2);
				tgGrade.setRank(ConstantUtil._lev_2);
			/** 个人购买  《天狮事业锦囊》一套*/
			}else{
				dist.setRankId(ConstantUtil._lev_1);
				tgGrade.setRank(ConstantUtil._lev_1);
			}
			ownership = null;
			/**计算小组业绩*/
			findCellGrade(distributorCode,netAchieve,tgGrade,tgMap,dirchildList);
		}
	}
	
	/**
	 * 	计算经销商小组业绩（X-Y）
	 * @param netAchieve   整网业绩
	 * @param tgGrade	        当前经销商业绩
	 * @param tgMap	                   业绩缓存（因为在计算过下线以及本人的职级后传入，所以当前缓存内已经包含他们的职级）
	 * @param dirchildList 直接下线	
	 */
	public void findCellGrade(String distributorCode , double netAchieve , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList){
		double cellGrade = 0L;
		Long rank = tgGrade.getRank();
		for(TDistributor child: dirchildList){
			/**在下线的网络中找到职级比该名经销商大的，找不到就返回0D*/
			double result = findMaxRankUnderNet(rank,child,tgMap);
			cellGrade += (result>=0D?result:0D);
		}
		tgGrade.setCellAchieve(netAchieve-cellGrade);
	}
	
	/**查询该网络及其下线职级比当前职级大的经销商，查询到每条分支的第一个最大的就返回该经销商的整网业绩，分支网络若没找到，就会返回一个负数*/
	private double findMaxRankUnderNet(Long rank , TDistributor child ,Map<String, TDistributorGrade> tgMap){
		TDistributorGrade childGrade = tgMap.get(child.getDistributorCode());
		if(childGrade.getRank()>=rank){
			return childGrade.getNetAchieve();
		}else{
			/**查询直接下线*/
			List<TDistributor> direChildList =tDistributorService.findByProperty("sponsorCode", child.getSponsorCode());
			if(direChildList.isEmpty()){
				return -1D; /**未找到*/
			}else{
				/**初始化未找到*/
				double result = -1D;
				/**直接下线找到了，就立刻返回，不再继续往下层查找*/
				for(TDistributor ch : direChildList){/**职级必须从tgGrade对象里获取，因为此时的经销商并未入库，等所有计算好后，遍历历史表一并入库*/
					result = findMaxRankUnderNet(tgMap.get(ch.getDistributorCode()).getRank(),ch,tgMap);
					if(result>=0D){ /**整网业绩有可能为零，所以发现为零，也说明找到了职级较大的*/
						break;
					}
				}
				return result;
			}
		}
	}
}
