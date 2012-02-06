package com.iteye.tianshi.web.controller.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iteye.tianshi.core.page.Page;
import com.iteye.tianshi.core.page.PageRequest;
import com.iteye.tianshi.core.util.ConstantUtil;
import com.iteye.tianshi.core.util.SQLOrderMode;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.model.base.TBounsConf;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TDistributorBoun;
import com.iteye.tianshi.web.model.base.TDistributorGrade;
import com.iteye.tianshi.web.model.base.TDistributorGradeHis;
import com.iteye.tianshi.web.model.base.TShopInfo;
import com.iteye.tianshi.web.service.base.TBounsConfService;
import com.iteye.tianshi.web.service.base.TDistributorBounService;
import com.iteye.tianshi.web.service.base.TDistributorBounsHisService;
import com.iteye.tianshi.web.service.base.TDistributorGradeHisService;
import com.iteye.tianshi.web.service.base.TDistributorGradeService;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.TProductDetailService;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 * 经销商业绩表
 * 
 */
@Controller
@RequestMapping("/grade")
public class TDistributorGradeController extends BaseController {
	@Autowired
	TDistributorGradeDao tDistributorGradeDao;

	@Autowired
	TDistributorGradeService tDistributorGradeService;

	@Autowired
	TDistributorGradeHisService tDistributorGradeHisService;
	
	@Autowired
	TShopInfoService tShopInfoService;

	@Autowired
	TDistributorService tDistributorService;

	@Autowired
	TProductDetailService tProductDetailService;
	
	@Autowired
	TBounsConfService bounsConfService;
	
	@Autowired
	TDistributorBounService  distributorBounService ;
	
	@Autowired
	TDistributorBounsHisService  distributorBounsHisService ;

	/**
	 * 获取所有专卖店信息
	 * 
	 */
	@RequestMapping(value = "/getAllTShopInfos", method = RequestMethod.POST)
	public List<TShopInfo> getAllTShopInfos() {
		return tShopInfoService.findAllEntity();
	}

	@RequestMapping(value = "/getTDistributors", method = RequestMethod.POST)
	public TDistributor getTDistributors(Long id) {
		return tDistributorService.findEntity(id);
	}

	/**
	 * 经销商业绩分页查询
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryDistributorGrade")
	@ResponseBody
	public Page<TDistributorGrade> pageQueryDistributorGrade(
			@RequestParam("start") int startIndex,
			@RequestParam("limit") int pageSize,
			@RequestParam(required = false) String distributorCode,
			@RequestParam(required = false) String shopCode,
			@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PageRequest<TDistributorGrade> pageRequest = new PageRequest<TDistributorGrade>(
				startIndex, pageSize);
		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);
		pageRequest.setStartTime(startTime);
		pageRequest.setEndTime(endTime);
		pageRequest.setTimeField("saleTime");
		Map<String, Object> filters = pageRequest.getFilters();
		// 根据编号查询
		if (StringUtils.hasText(distributorCode)) {
			filters.put("distributorCode", distributorCode);
		}
		// 根据商铺查询
		if (StringUtils.hasText(shopCode)) {
			StringBuilder distributorCodes = new StringBuilder(
					"distributorCode in(");
			List<TDistributor> list = tDistributorService.findByProperty(
					"shopCode", shopCode);
			int count = 0;
			int size = list.size() - 1;
			for (TDistributor tDistributor : list) {
				if (count++ < size) {
					distributorCodes.append("'").append(
							tDistributor.getDistributorCode()).append("',");
				} else {
					distributorCodes.append("'").append(
							tDistributor.getDistributorCode()).append("')");
				}
			}
			pageRequest.setExtraCondition(distributorCodes.toString());
		}
		Page<TDistributorGrade> page = tDistributorGradeService
				.findAllForPage(pageRequest);
		// 将主键ID转换成名称回显
		for (TDistributorGrade dist : page.getResult()) {
			String distributorName = getTDistributors(dist.getDistributorId())
					.getDistributorName();
			dist.setDistributorName(distributorName);
		}
		return page;
	}

	/**
	 * 计算经销商业绩表，并且算出职级，历史记录只能查询前一个月的，因为每次计算完毕，更新历史业绩表会覆盖上一个月的历史业绩表
	 */
	@RequestMapping("/calc")
	@ResponseBody
	public boolean calcGradeAndBonus() {
		
		String curYear = new SimpleDateFormat("yyyyMMdd").format(new Date()).substring(0, 4);
		String curMon = new SimpleDateFormat("yyyyMMdd").format(new Date()).substring(4, 6);
		String endDate = curYear + curMon + "25";// 结束日期
		String startDate = "";// 开始日期
		StringBuilder sql = new StringBuilder(
				"SELECT distributor_id ,distributor_code, Max(PV) AS maxChange ,SUM(sale_number * pv) as SUM_PV ,SUM(sale_number * bv) as SUM_BV ,floors FROM t_product_list where create_time>'");
		if (curMon.equals("01")) {
			//当月一月
			startDate = (Integer.valueOf(curYear) - 1) + "" + "1225"; 	
		} else {
			//上月25号
			startDate = curYear + (String.format("%02d", Integer.valueOf(curMon) - 1)) + "25"; 
		}
		/**根据经销商分组和按层级排序*/ 
		sql.append(startDate + "' and create_time<'").append(
				endDate + "' group by distributor_code order by floors desc");
		/**tgMap 键存放TDistributorGrade的编号属性    值放对于此编号的对象**/
		Map<String, TDistributorGrade> tgMap = new HashMap<String, TDistributorGrade>();
		List<Map<String, Object>> tglist = tDistributorGradeDao.getJdbcTemplate().queryForList(sql.toString());
		/**查询出所有经销商，按层级降序查询，并且初始化他们的个人业绩为零*/
		List<TDistributor> allDistributors = tDistributorService.findByPropertysAndOrder(new String[]{}, new String[]{}, new Object[]{}, "floors", SQLOrderMode.DESC);
		TDistributorGrade tgGrade = null;
		for(TDistributor it : allDistributors){
			String distributorCode = it.getDistributorCode();
			if(distributorCode.equals(ConstantUtil._top_)) //顶级不纳入计算
				continue;
			tgGrade = new TDistributorGrade();
			/**ID*/
			tgGrade.setDistributorId(it.getId());
			/**Code*/
			tgGrade.setDistributorCode(distributorCode);
			/**个人业绩（当月）**/
			tgGrade.setPersonAchieve(0D);
			/**个人业绩奖金（当月），是根据职级计算奖金用的**/
			tgGrade.setBonusAchieve(0D);
			/**当月最大消费*/
			tgGrade.setMaxChange(0D);
			
			/**计算日期*/
			tgGrade.setAchieveDate(new Date());
			tgMap.put(distributorCode, tgGrade);
		}
		tgGrade = null;
		/**将当月购买产品即具有个人业绩的经销商进行计算，而没有业绩的经销商的个人业绩归零（他们只有累计业绩）**/
		for(Map<String, Object> map:tglist){
			/**经销商编号**/
			String distributor_code = map.get("distributor_code").toString();
			tgGrade = tgMap.get(distributor_code);
			/**个人业绩（当月）**/
			tgGrade.setPersonAchieve((Double)map.get("SUM_PV")); 
			/**个人业绩奖金（当月）**/
			tgGrade.setBonusAchieve((Double)map.get("SUM_BV"));
			/**当月最大消费*/
			tgGrade.setMaxChange((Double)map.get("maxChange"));
			/**先将新加入的经销商的业绩（已计算出个人业绩和计算日期）拷贝到历史表*/
			if(tDistributorGradeHisService.findByProperty("distributorCode", distributor_code).isEmpty()){
				TDistributorGradeHis his = tgGrade.getHisGradeCopy();
				his.setAccuPAchieve(0D);
				his.setAccuAchieve(0D);
				tDistributorGradeHisService.createOrUpdate(his);
				his = null;
			}
			map = null;
		}
		tgGrade = null;
		tglist = null;
		/**
		 * 取到销售网络中的最后一层
		 */
		int lastfloor=allDistributors.get(0).getFloors();		
		/**
		 * 直接业绩：包括两部分（每部分均指一个月内的业绩）
		 * 第一部分是指所有直接下线个人累计业绩小于或等于200PV部分。
		 * 第二部分是指本人个人累计业绩大于200PV部分。
		 * 间接业绩：包括两部分（每部分均指一个月内的业绩）
		 * 第一部分是指所有直接下线个人累计业绩大于200PV部分。
		 * 第二部分是指所有间接下线个人累计业绩之和。
		 * 累计业绩：包括三部分：第一部分是指个人业绩小于或等于200PV部分；第二部分是指累计直接业绩；第三部分是指累计间接业绩。
		 * 整网业绩：某直销商当月整个网络产生的业绩之和。
		 * 小组业绩：小组业绩是两个业绩额的差值，举例：小组业绩=X—Y。
		 * X值是指当月的本人整网业绩，
		 * Y值是指该直销商的同职级及以上职级下线同月的整网业绩。
		 */
		List<TDistributor> dirchildList = null;
		List<TDistributor> indirchildList = null;  
		for (TDistributor dist : allDistributors) {
			/**最后一层，或者 非最后一层，但没有下线的经销商，  只需考虑自己的个人累计 业绩即可**/
			String distributorCode = dist.getDistributorCode();
			if(distributorCode.equals(ConstantUtil._top_)) //顶级不纳入计算
				continue;
			boolean noChild = tDistributorService.findByProperty("sponsor_code", distributorCode).isEmpty();
			if (dist.getFloors()==lastfloor || noChild) {
				tgGrade = tgMap.get(distributorCode);
				/**个人业绩*/
				double personAchevePV = tgGrade.getPersonAchieve(); 
				double personAcheveBV = tgGrade.getBonusAchieve();/**该经销商的本月个人业绩————奖金**/
				dist.setBonusAchieve(personAcheveBV); 
				double directAchieve = personAchevePV>200D?(personAchevePV-200D):0D;/**本人个人累计业绩大于200PV部分。**/
				double directAchieve_self_BV = personAcheveBV>200D?(personAcheveBV-200D):0D;/**本人个人累计业绩大于200PV部分————奖金。**/
				double indirectAchieve = 0D;//无间接业绩
				/**直接业绩**/
				tgGrade.setDirectAchieve(directAchieve);
				tgGrade.setDirectAchieve_BV(directAchieve_self_BV);
				/**间接业绩**/
				tgGrade.setIndirectAchieve(indirectAchieve);
				/**整网业绩**/
				tgGrade.setNetAchieve(tgGrade.getPersonAchieve());
				/**小组业绩**/
				tgGrade.setCellAchieve(tgGrade.getPersonAchieve());
			}else{/**从倒数第二层开始要考虑下线的个人累计pv部分和自己的个人累计pv部分**/
				tgGrade = tgMap.get(distributorCode);
				double personAchevePV = tgGrade.getPersonAchieve();/**该经销商的本月个人业绩**/
				double personAcheveBV = tgGrade.getBonusAchieve();/**该经销商的本月个人业绩————奖金**/
				int dirfloors = dist.getFloors()+1;/**直接下线层级**/
				dirchildList = tDistributorService.findAllDirChildrenDistributors(dist.getId(), dirfloors);/**所有直接子节点**/
				indirchildList = tDistributorService.findAllIndirChildrenDistributors(dist.getId(), dirfloors);/**所有间接子节点**/
				double directAchieve_self = personAchevePV>200D?(personAchevePV-200D):0D;/**本人个人累计业绩大于200PV部分。**/
				double directAchieve_self_BV = personAcheveBV>200D?(personAcheveBV-200D):0D;/**本人个人累计业绩大于200PV部分————奖金。**/
				double directAchieve_down = 0D;/**所有直接下线个人累计业绩小于或等于200PV部分。**/
				double directAchieve_down_BV = 0D;/**所有直接下线个人累计业绩小于或等于200PV部分。**/
				double indirectAchieve_dirdown=0D;/**所有直接下线个人累计业绩大于200PV部分。**/
				double indirectAchieve_indirdown=0D;/**所有间接下线个人累计业绩之和。**/
				double dirnetAchieve=0D;/**所有直接下线的个人累计。**/
				double indirnetAchieve=0D;/**所有间接下线的个人累计。**/
				for(TDistributor tDistributor:dirchildList){
					/**直接下线经销商的个人业绩*/
					double dirchildPersonalAchevePV = tgMap.get(tDistributor.getDistributorCode()).getPersonAchieve();
					double dirchildPersonalAcheveBV = tgMap.get(tDistributor.getDistributorCode()).getBonusAchieve();
					/**直接下线个人业绩大于200PV部分*/
					indirectAchieve_dirdown += dirchildPersonalAchevePV>200D?(dirchildPersonalAchevePV-200D):0D;
					/**直接下线个人业绩小于或等于200PV部分*/
					directAchieve_down += dirchildPersonalAchevePV<=200D?dirchildPersonalAchevePV:200D;
					directAchieve_down_BV += dirchildPersonalAcheveBV<=200D?dirchildPersonalAcheveBV:200D; //奖金
					/**用于算整网业绩用的 */
					dirnetAchieve += dirchildPersonalAchevePV;
					tDistributor = null;
				}
				for(TDistributor tDistributor:indirchildList){
					/**间接下线个人业绩之和*/
					indirectAchieve_indirdown += tgMap.get(tDistributor.getDistributorCode()).getPersonAchieve();
					/**用于算整网业绩用的 */
					indirnetAchieve +=tgMap.get(tDistributor.getDistributorCode()).getPersonAchieve();
					tDistributor = null;
				}
				/**直接业绩**/
				tgGrade.setDirectAchieve(directAchieve_self+directAchieve_down);
				tgGrade.setDirectAchieve_BV(directAchieve_self_BV + directAchieve_down_BV); //奖金
				/**间接业绩**/
				tgGrade.setIndirectAchieve(indirectAchieve_dirdown+indirectAchieve_indirdown);
				/**整网业绩 == 个人业绩+直接下线+间接下线 */
				tgGrade.setNetAchieve(tgGrade.getPersonAchieve() + dirnetAchieve + indirnetAchieve);
			}
			/***
			 * 计算经销商职级，因为经销商列表正在从最低层开始遍历，所以符合由下往上计算的原则
			 */
			double  maxChange  = tgGrade.getMaxChange();
			/**当月没有购买产品的经销商职级均一星（即当月最大消费为零）*/
			if(maxChange<=0D){
				dist.setRankId(ConstantUtil._lev_1);
				tgGrade.setRank(ConstantUtil._lev_1);
			}else{
				/**计算经销商职级和小组业绩*/
				tDistributorGradeService.findRank(dist , distributorCode ,maxChange , tgGrade , tgMap , dirchildList);
			}
		}
		dirchildList = null;
		indirchildList = null;
		
		/**计算经销商累计个人业绩以及累计业绩**/
		List<TDistributorGradeHis> hisList = tDistributorGradeHisService.findAllEntity();
		/**遍历历史表是因为历史表已经被初始化了，包括两部分，第一部分是以前存在的经销商，第二部分是新增加的经销商*/
		String distributorCode = null;
		double accuPAchieve = 0D;
		double personAchieve_self = 0D;
		double personAchieve_bleow200 = 0D;
		for (TDistributorGradeHis his : hisList) {
			distributorCode = his.getDistributorCode();
			tgGrade = tgMap.get(distributorCode);
			/**分两种情况：1.该名经销商当月没有购买产品说明在历史表中有记录，业绩归零  2.该名经销商购买了产品，其业绩可以正常纳入计算*/
			accuPAchieve = his.getAccuPAchieve() + tgGrade.getPersonAchieve();
			/**累计个人业绩**/
			tgGrade.setAccuPAchieve(accuPAchieve);
			/**累计业绩  == 个人业绩小于200部分+直接+间接+历史累计**/
			personAchieve_self = tgGrade.getPersonAchieve();
			personAchieve_bleow200=personAchieve_self<=200D?personAchieve_self:200D;
			tgGrade.setAccuAchieve(personAchieve_bleow200 + tgGrade.getDirectAchieve() + tgGrade.getIndirectAchieve() + his.getAccuAchieve());
			/**累计个人业绩--入历史库*/
			tDistributorGradeHisService.createOrUpdate(tgGrade.getHisGradeCopy());
			/**经销商业绩保存*/
			tDistributorGradeService.createOrUpdate(tgGrade);
			/**经销商职级保存*/
			tDistributorService.findEntity(tgGrade.getDistributorId()).setRankId(tgGrade.getRank());
			his = null;
		}
		tgGrade = null;
		hisList = null;
		
		/**计算奖金，（直接奖，间接奖，领导奖，荣衔奖，特别奖，国际奖）其中BonusAchieve是计算奖金的累计*/
		List<TBounsConf> cfgList = bounsConfService.findAllEntity();
		/**初始化奖金配置表*/
		Map<Long , TBounsConf> bonusCfgMap =  new HashMap<Long , TBounsConf>();
		for(TBounsConf bonus : cfgList){
			bonusCfgMap.put(bonus.getRankId(), bonus);
			bonus = null;
		}
		cfgList = null;
		/**初始化【星级-个人最低消费】配置表*/
		Map<Long,Double> cfgLowAchieve = new HashMap<Long,Double>();
		cfgLowAchieve.put(ConstantUtil._lev_1, 0D);
		cfgLowAchieve.put(ConstantUtil._lev_2, 0D);
		cfgLowAchieve.put(ConstantUtil._lev_3, 0D);
		cfgLowAchieve.put(ConstantUtil._lev_4, 10D);
		cfgLowAchieve.put(ConstantUtil._lev_5, 30D);
		cfgLowAchieve.put(ConstantUtil._lev_6, 60D);
		cfgLowAchieve.put(ConstantUtil._lev_7, 100D);
		cfgLowAchieve.put(ConstantUtil._lev_8, 200D);
		cfgLowAchieve.put(ConstantUtil._lev_s_1, 300D);
		cfgLowAchieve.put(ConstantUtil._lev_s_2, 300D);
		cfgLowAchieve.put(ConstantUtil._lev_s_3, 300D);
		/**遍历经销商，当前经销商已有职级等信息*/
		TBounsConf bouns = null;
		TDistributorBoun distBonus = null;
		String distbutorCode = null;
		for (TDistributor dist : allDistributors) {
			distbutorCode = dist.getDistributorCode();
			if(distbutorCode.equals("000001")){
				System.out.println("---ponit---");/**断点，查看A的职级，小组业绩信息*/
			}
			if(ConstantUtil._top_.equals(distbutorCode)){
				continue;
			}
			distBonus = new TDistributorBoun();
			/**经销商编码*/
			distBonus.setDistributorCode(distbutorCode);
			/**经销商ID*/
			distBonus.setDistributorId(dist.getId());
			/**计算日期*/
			distBonus.setBounsDate(new Date());
			tgGrade = tgMap.get(distbutorCode);/**业绩*/
			Long rank = dist.getRankId(); /**职级*/
			bouns = bonusCfgMap.get(rank); /**职级对应的奖金分类*/
			/**计算之前，必须满足本月个人累计PV的最低消费额度****/
			if(tgGrade.getPersonAchieve()>=cfgLowAchieve.get(rank)){
					/***********************计算直接奖金***************/
					distBonus.setDirectBouns(tgGrade.getDirectAchieve_BV()* bouns.getDirectP()/100);
					/***********************计算间接奖金，见①和②***************************/
					/**查询出所有直接下线*/
					dirchildList = tDistributorService.findAllDirChildrenDistributors(dist.getId(), dist.getFloors()+1);
					/**①查询出所有直接下线，直接下线中大于200BV的部分纳入间接奖*/
					double directBouns =0D;
					for(TDistributor dirChild: dirchildList){
						/**若职级大于上线的职级，则获取不到它的间接奖*/
						if(dirChild.getRankId()> rank){
							continue;
						}else{
							/**获取当前节点直接奖比例*/
							double dirChildBonus = bonusCfgMap.get(dirChild.getRankId()).getDirectP()/100;
							/**累计当前的直接下线的间接奖*/
							double dir = tgMap.get(dirChild.getDistributorCode()).getBonusAchieve();
							directBouns += (dir>200D?(dir-200D ):0D)* (bouns.getDirectP()/100 - dirChildBonus);
						}
					}
					/**查询出所有间接下线*/
					indirchildList = tDistributorService.findAllIndirChildrenDistributors(dist.getId(), dist.getFloors()+1);
					/**②查询出所有间接下线，间接下线中要分两部分计算间接奖，第一部分：大于200的部分，第二部分：小于等于200的部分纳入间接奖*/
					double indirectBouns = 0D;
					for(TDistributor indirChild: indirchildList){
						/**获取当前节点直接奖比例*/
						double dirChildBonus = bonusCfgMap.get(indirChild.getRankId()).getDirectP()/100;
						/**indirChild本身的个人累计*/
						double selfAchieve = tgMap.get(indirChild.getDistributorCode()).getBonusAchieve();
						/**大于200的情况*/
						if(selfAchieve>200 && indirChild.getRankId()<rank){
							/***超过200的部分*/
							indirectBouns += (selfAchieve-200D )* (bouns.getDirectP()/100 - dirChildBonus);
							/**等于200的部分，需要获取上级经销商的职级奖比例*/
							indirectBouns +=  200D*(bouns.getDirectP()/100-bonusCfgMap.get(tgMap.get(indirChild.getSponsorCode()).getRank()).getDirectP()/100);
						/**小于等于200的情况*/
						}else{
							indirectBouns +=  selfAchieve*(bouns.getDirectP()/100-bonusCfgMap.get(tgMap.get(indirChild.getSponsorCode()).getRank()).getDirectP()/100);
						}
						indirChild = null;
					}
					distBonus.setIndirectBouns(directBouns + indirectBouns); /**①+②*/
					/**领导奖计算*/
					double leadership = tDistributorGradeService.calcLeaderShip(dist,rank,bonusCfgMap,dirchildList,tgMap);
					distBonus.setLeaderBouns(leadership);
					/************************计算完毕************************/
			}
			
			/**保存经销商奖金表*/
			distributorBounService.insertEntity(distBonus);
			/**保存奖金历史表*/
			distributorBounsHisService.insertEntity(distBonus.copyToHis());
			dist = null;
		}
		distBonus = null;
		dirchildList = null;
		indirchildList = null;
		bouns = null;
		allDistributors = null;
		tgMap = null;
		bonusCfgMap = null;
		return true;
	}
}
