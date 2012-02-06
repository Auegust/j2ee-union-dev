package com.iteye.tianshi.web.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.util.ConstantUtil;
import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.model.base.TBounsConf;
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
			}else if(maxChange>=100 || personAchieve>=100){
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
				indirect = tDistributorService.findAllIndirChildrenDistributors(child.getId(), child.getFloors()+1);
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
			}else if(maxChange>=100D || personAchieve>=100){
				dist.setRankId(ConstantUtil._lev_2);
				tgGrade.setRank(ConstantUtil._lev_2);
			/** 个人购买  《天狮事业锦囊》一套*/
			}else{
				dist.setRankId(ConstantUtil._lev_1);
				tgGrade.setRank(ConstantUtil._lev_1);
			}
			ownership = null;
			/**计算小组业绩*/
			findCellGrade(dist , distributorCode,netAchieve,tgGrade,tgMap,dirchildList);
		}
	}
	
	/**
	 * 	计算经销商小组业绩（X-Y）
	 * @param netAchieve   整网业绩
	 * @param tgGrade	        当前经销商业绩
	 * @param tgMap	                   业绩缓存（因为在计算过下线以及本人的职级后传入，所以当前缓存内已经包含他们的职级）
	 * @param dirchildList 直接下线	
	 */
	public void findCellGrade(TDistributor dist ,String distributorCode , double netAchieve , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList){
		double cellGrade = 0L;
		Long rank = tgGrade.getRank();
		for(TDistributor child: dirchildList){
			/**在下线的网络中找到职级比该名经销商大的，找不到就返回0D*/
			double result = findMaxRankUnderNet(rank,child,tgMap);
			cellGrade += (result>=0D?result:0D);
		}
		tgGrade.setCellAchieve(netAchieve-cellGrade);
		dist.setCellAchieve(netAchieve-cellGrade);
	}
	
	/**查询该网络及其下线职级比当前职级大的经销商，查询到每条分支的第一个最大的就返回该经销商的整网业绩，分支网络若没找到，就会返回一个负数*/
	private double findMaxRankUnderNet(Long rank , TDistributor child ,Map<String, TDistributorGrade> tgMap){
		TDistributorGrade childGrade = tgMap.get(child.getDistributorCode());
		if(childGrade.getRank()>=rank){
			return childGrade.getNetAchieve();
		}else{
			/**查询直接下线*/
			List<TDistributor> direChildList =tDistributorService.findByProperty("sponsorCode", child.getDistributorCode());
			if(direChildList.isEmpty()){
				direChildList = null;
				return -1D; /**未找到*/
			}else{
				/**初始化未找到*/
				double result = -1D;
				/**直接下线找到了，就立刻返回，不再继续往下层查找*/
				for(TDistributor ch : direChildList){/**职级必须从tgGrade对象里获取，因为此时的经销商并未入库，等所有计算好后，遍历历史表一并入库*/
					//Long sub_rank = tgMap.get(ch.getSponsorCode()).getRank();
					result = findMaxRankUnderNet(rank,ch,tgMap);
					if(result>=0D){ /**整网业绩有可能为零，所以发现为零，也说明找到了职级较大的*/
						break;
					}
				}
				direChildList = null;
				return result;
			}
		}
	}
	
	/**
	 * 领导奖金计算
	 * @param dist           当前计算对象
	 * @param rank			   等级
	 * @param bonusCfgMap	   奖金配置表
	 * @param dirchildList   所有直接节点
	 * @return
	 */
	public double calcLeaderShip(TDistributor dist ,Long rank ,Map<Long , TBounsConf> bonusCfgMap,List<TDistributor> dirchildList,Map<String, TDistributorGrade> tgMap) {
		double leadership = 0D;
		double cellGrade = dist.getCellAchieve(); /**小组业绩*/
		if(rank == ConstantUtil._lev_5 && cellGrade> 600D){/**五星,小组业绩大于600，领取1%的奖金*/
			double gap_bonus_1 = bonusCfgMap.get(ConstantUtil._lev_5).getW1()/100; /**一代领导奖*/
			for(TDistributor lch : dirchildList){/**遍历支臂*/
				TDistributor find = getChildGradeCellHighest(lch,rank); /**find中有可能会返回lch*/
				if(find!=null){
					int floors = find.getFloors();/**定点的层级*/
					Long id = find.getId();
					/**获取第一代*/
					List<String> child_Lv1_List=findChildListByFloors (floors+1,id);/**第一代层级*/
					for(String code:child_Lv1_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_1;
						code = null;
					}
					child_Lv1_List = null;
				}
				find = null;
				lch = null;
			}
		}else if(rank == ConstantUtil._lev_6 && cellGrade> 1000D){/**六星*/
			double gap_bonus_1 = bonusCfgMap.get(ConstantUtil._lev_6).getW1()/100; /**一代领导奖*/
			double gap_bonus_2 = bonusCfgMap.get(ConstantUtil._lev_6).getW2()/100; /**二代领导奖*/
			double gap_bonus_3 = bonusCfgMap.get(ConstantUtil._lev_6).getW3()/100; /**三代领导奖*/
			for(TDistributor lch : dirchildList){/**遍历支臂*/
				TDistributor find = getChildGradeCellHighest(lch,rank);
				if(find!=null){
					int floors = find.getFloors();/**定点的层级*/
					Long id = find.getId();
					/**获取第一代*/
					List<String> child_Lv1_List=findChildListByFloors (floors+1,id);/**第一代层级*/
					for(String code:child_Lv1_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_1;
						code = null;
					}
					child_Lv1_List = null;
					/**获取第二代*/
					List<String> child_Lv2_List=findChildListByFloors (floors+2,id);/**第一代层级*/
					for(String code:child_Lv2_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_2;
						code = null;
					}
					child_Lv2_List = null;
					/**获取第三代*/
					List<String> child_Lv3_List=findChildListByFloors (floors+3,id);/**第一代层级*/
					for(String code:child_Lv3_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_3;
						code = null;
					}
					child_Lv3_List = null;
				}
				find = null;
				lch = null;
			}
			
		}else if(rank == ConstantUtil._lev_7 && cellGrade> 2000D){/**七星*/
			double gap_bonus_1 = bonusCfgMap.get(ConstantUtil._lev_7).getW1()/100; /**一代领导奖*/
			double gap_bonus_2 = bonusCfgMap.get(ConstantUtil._lev_7).getW2()/100; /**二代领导奖*/
			double gap_bonus_3 = bonusCfgMap.get(ConstantUtil._lev_7).getW3()/100; /**三代领导奖*/
			double gap_bonus_4 = bonusCfgMap.get(ConstantUtil._lev_7).getW4()/100; /**一代领导奖*/
			double gap_bonus_5 = bonusCfgMap.get(ConstantUtil._lev_7).getW5()/100; /**二代领导奖*/
			for(TDistributor lch : dirchildList){/**遍历支臂*/
				TDistributor find = getChildGradeCellHighest(lch,rank);
				if(find!=null){
					int floors = find.getFloors();/**定点的层级*/
					Long id = find.getId();
					/**获取第一代*/
					List<String> child_Lv1_List=findChildListByFloors (floors+1,id);/**第一代层级*/
					for(String code:child_Lv1_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_1;
						code = null;
					}
					child_Lv1_List = null;
					/**获取第二代*/
					List<String> child_Lv2_List=findChildListByFloors (floors+2,id);/**第一代层级*/
					for(String code:child_Lv2_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_2;
						code = null;
					}
					child_Lv2_List = null;
					/**获取第三代*/
					List<String> child_Lv3_List=findChildListByFloors (floors+3,id);/**第一代层级*/
					for(String code:child_Lv3_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_3;
						code = null;
					}
					child_Lv3_List = null;
					/**获取第四代*/
					List<String> child_Lv4_List=findChildListByFloors (floors+4,id);/**第一代层级*/
					for(String code:child_Lv4_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_4;
						code = null;
					}
					child_Lv4_List = null;
					/**获取第五代*/
					List<String> child_Lv5_List=findChildListByFloors (floors+5,id);/**第一代层级*/
					for(String code:child_Lv5_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_5;
						code = null;
					}
					child_Lv5_List = null;
				}
				find = null;
				lch = null;
			}
			
		}else if(rank >ConstantUtil._lev_7 && cellGrade> 3000D){/**八星/金狮/银狮/铜师*/
			double gap_bonus_1 = bonusCfgMap.get(ConstantUtil._lev_8).getW1()/100; /**一代领导奖*/
			double gap_bonus_2 = bonusCfgMap.get(ConstantUtil._lev_8).getW2()/100; /**二代领导奖*/
			double gap_bonus_3 = bonusCfgMap.get(ConstantUtil._lev_8).getW3()/100; /**三代领导奖*/
			double gap_bonus_4 = bonusCfgMap.get(ConstantUtil._lev_8).getW4()/100; /**四代领导奖*/
			double gap_bonus_5 = bonusCfgMap.get(ConstantUtil._lev_8).getW5()/100; /**五代领导奖*/
			double gap_bonus_6 = bonusCfgMap.get(ConstantUtil._lev_8).getW6()/100; /**六代领导奖*/
			double gap_bonus_7 = bonusCfgMap.get(ConstantUtil._lev_8).getW7()/100; /**七代领导奖*/
			double gap_bonus_8 = bonusCfgMap.get(ConstantUtil._lev_8).getW8()/100; /**八代领导奖*/
			for(TDistributor lch : dirchildList){/**遍历支臂*/
				TDistributor find = getChildGradeCellHighest(lch,rank);
				if(find!=null){
					int floors = find.getFloors();/**定点的层级*/
					Long id = find.getId();
					/**获取第一代*/
					List<String> child_Lv1_List=findChildListByFloors (floors+1,id);/**第一代层级*/
					for(String code:child_Lv1_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_1;
						code = null;
					}
					child_Lv1_List = null;
					/**获取第二代*/
					List<String> child_Lv2_List=findChildListByFloors (floors+2,id);/**第一代层级*/
					for(String code:child_Lv2_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_2;
						code = null;
					}
					child_Lv2_List = null;
					/**获取第三代*/
					List<String> child_Lv3_List=findChildListByFloors (floors+3,id);/**第一代层级*/
					for(String code:child_Lv3_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_3;
						code = null;
					}
					child_Lv3_List = null;
					/**获取第四代*/
					List<String> child_Lv4_List=findChildListByFloors (floors+4,id);/**第一代层级*/
					for(String code:child_Lv4_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_4;
						code = null;
					}
					child_Lv4_List = null;
					/**获取第五代*/
					List<String> child_Lv5_List=findChildListByFloors (floors+5,id);/**第一代层级*/
					for(String code:child_Lv5_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_5;
						code = null;
					}
					child_Lv5_List = null;
					/**获取第六代*/
					List<String> child_Lv6_List=findChildListByFloors (floors+6,id);/**第一代层级*/
					for(String code:child_Lv6_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_6;
						code = null;
					}
					child_Lv6_List = null;
					/**获取第七代*/
					List<String> child_Lv7_List=findChildListByFloors (floors+7,id);/**第一代层级*/
					for(String code:child_Lv7_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_7;
						code = null;
					}
					child_Lv7_List = null;
					/**获取第八代*/
					List<String> child_Lv8_List=findChildListByFloors (floors+8,id);/**第一代层级*/
					for(String code:child_Lv8_List){
						leadership+=tgMap.get(code).getCellAchieve().doubleValue()*gap_bonus_8;
						code = null;
					}
					child_Lv8_List = null;
				}
				find = null;
				lch = null;
			}
		}
		return leadership;
	}
	
	/***
	 * 在下级找到比当前节点职级高的节点
	 * @param lch	下级的第一层节点
	 * @param rank	需要对比的职级
	 * @return
	 */
	private TDistributor getChildGradeCellHighest(TDistributor lch ,Long rank){
		if(lch.getRankId()>=rank){
			return lch;
		}else{
			List<TDistributor> find = tDistributorService.findByProperty("sponsorCode", lch.getDistributorCode());
			for(TDistributor c:find){
				TDistributor ok =  getChildGradeCellHighest(c,rank);
				find = null;
				return ok;
			}
		}
		return null;
	}
	
	/***
	 * 获取指定节点下的第N层的所有节点
	 * @param floors
	 * @param id
	 * @return
	 */
	private List<String> findChildListByFloors (Integer floors , Long id){
		StrBuilder sql = new StrBuilder("select distributor_code FROM tianshi.t_distributor WHERE FIND_IN_SET(res_id,tianshi.getChildLst(").append(id+")) ");
		if (floors!=null) {
			sql.append("and floors=").append(floors);
		}
		List<String> list = this.distributorGradeDao.getJdbcTemplate().queryForList(sql.toString(),String.class);
		return list;
	}
}
