package com.iteye.tianshi.web.service.base.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.iteye.tianshi.core.util.ConstantUtil;
import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.util.SQLOrderMode;
import com.iteye.tianshi.core.util.UtilTool;
import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TBounsConfDao;
import com.iteye.tianshi.web.dao.base.TDistributorBounDao;
import com.iteye.tianshi.web.dao.base.TDistributorBounsHisDao;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.dao.base.TDistributorGradeHisDao;
import com.iteye.tianshi.web.model.base.TBounsConf;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TDistributorBoun;
import com.iteye.tianshi.web.model.base.TDistributorBounsHis;
import com.iteye.tianshi.web.model.base.TDistributorGrade;
import com.iteye.tianshi.web.model.base.TDistributorGradeHis;
import com.iteye.tianshi.web.model.support.RankCache;
import com.iteye.tianshi.web.service.base.TDistributorGradeService;
import com.iteye.tianshi.web.service.base.TDistributorService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author Jackson
 */
@Service
public class TDistributorGradeServiceImpl extends BaseServiceImpl<TDistributorGrade, Long> implements TDistributorGradeService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TBounsConfDao bounsConfDao;
	@Autowired
	private TDistributorGradeDao distributorGradeDao;
	@Autowired
	private TDistributorGradeHisDao distributorGradeHisDao;
	@Autowired
	private TDistributorBounDao distributorBounDao ;
	@Autowired
	private TDistributorBounsHisDao distributorBounHisDao ;
	@Autowired
	private TDistributorService tDistributorService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(TDistributorGradeServiceImpl.class);

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributorGrade, Long> getGenericDao() {
		return this.distributorGradeDao;
	}
	
	
	/**
	 * 计算职级和奖金【核心模块】
	 * 
	 */
	
	@Override
	@Transactional
	public ResponseData mainCompute(String  endDate){

		if(!StringUtils.hasText(endDate)){
			endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}

		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).substring(0, 10);
		String d_end =  endDate.substring(0, 10);
		if(!now.equals(d_end)){
			return new ResponseData(true, "请选择今天计算，计算日期必须在当天，以保证重复计算的精确性");
		}
		String startDate = "2012-01-12 00:00:00";// 开始日期初始化
		String sql_startdate = "SELECT MAX(achieve_date) FROM tianshi.t_distributor_grade_his";
		Date dayMax = jdbcTemplate.queryForObject(sql_startdate, Date.class);
		if(dayMax != null){
			/**取系统最大日期的1秒后*/
			startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(UtilTool.getNextDate(dayMax));
		}
		/**当月批次查询*/
		String sql_batch  = "SELECT MAX(batch_no) FROM tianshi.t_distributor_grade_his";
		Integer oldbatchNo = jdbcTemplate.queryForObject(sql_batch, Integer.class);
		if(oldbatchNo == null){
			oldbatchNo =0;
		}
		/***计算过一次了，必须清空后重新计算*/
		if(dayMax!=null && endDate.substring(0, 10).equals(dayMax.toString().substring(0, 10))){
			/**计算之前，清空业绩表，奖金表，历史业绩表和历史奖金表按当月时间清除*/
			String bouns_sql = "TRUNCATE TABLE tianshi.t_distributor_bouns";
			String grade_sql = "TRUNCATE TABLE tianshi.t_distributor_grade";
			String bouns_his_sql = "DELETE  FROM  tianshi.t_distributor_bouns_his WHERE batch_no="+oldbatchNo;
			String grade_his_sql = "DELETE  FROM  tianshi.t_distributor_grade_his WHERE batch_no="+oldbatchNo;
			jdbcTemplate.execute(grade_sql);
			jdbcTemplate.execute(bouns_sql);
			jdbcTemplate.execute(grade_his_sql);
			jdbcTemplate.execute(bouns_his_sql);
			/**将重复数据清空后，再继续还原到上个月开始计算*/
			startDate="2012-01-12 00:00:00";
			dayMax = jdbcTemplate.queryForObject(sql_startdate, Date.class);
			if(dayMax != null){
				/**取系统最大日期的后一分钟，减少误差时间*/
				startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(UtilTool.getNextDate(dayMax));
			}
			oldbatchNo =jdbcTemplate.queryForObject(sql_batch, Integer.class);
			if(oldbatchNo == null){
				oldbatchNo =0;
			}
		}
		
		StringBuilder sql = new StringBuilder(
				"SELECT distributor_id ,distributor_code, Max(PV) AS maxChange ,SUM(sale_number * pv) as SUM_PV ,SUM(sale_number * bv) as SUM_BV ,floors FROM t_product_list where create_time>'");
		/**根据经销商分组和按层级排序*/ 
		sql.append(startDate + "' and create_time<'").append(
				endDate + "' group by distributor_code order by floors desc");
		/**tgMap 键存放TDistributorGrade的编号属性    值放对于此编号的对象**/
		Map<String, TDistributorGrade> tgMap = new HashMap<String, TDistributorGrade>();
		List<Map<String, Object>> tglist = jdbcTemplate.queryForList(sql.toString());
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
			TDistributorGradeHis his = tgGrade.getHisGradeCopy(new TDistributorGradeHis());
			/**将当月的业绩备份到历史表，无论是新用户还是老用户*/
			his.setBatchNo(oldbatchNo+1); /**批次号+1代表新月份计算后的备份*/
			distributorGradeHisDao.createOrUpdate(his);
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
				findRank(dist , distributorCode ,maxChange , tgGrade , tgMap , dirchildList);
			}
		}
		dirchildList = null;
		indirchildList = null;
		/**次月的经销商历史业绩，当然肯定没有当月新加入的*/
		List<TDistributorGradeHis> hisLastMonList = distributorGradeHisDao.findByProperty("batchNo",oldbatchNo);
		Map<String,TDistributorGradeHis> hisMap = new HashMap<String, TDistributorGradeHis>(hisLastMonList.size());
		if(!hisLastMonList.isEmpty()){
			for(TDistributorGradeHis his:hisLastMonList){
				hisMap.put(his.getDistributorCode(), his);
				his = null;
			}
		}
		hisLastMonList = null;
		/**计算经销商累计个人业绩以及累计业绩**/
		List<TDistributorGradeHis> hisList = distributorGradeHisDao.findByProperty("batchNo",oldbatchNo+1);
		/**遍历历史表是因为历史表已经被初始化了，包括两部分，第一部分是以前存在的经销商，第二部分是新增加的经销商*/
		String distributorCode = null;
		double accuPAchieve = 0D;
		double personAchieve_self = 0D;
		double accuAchieve = 0D;
		double personAchieve_bleow200 = 0D;
		for (TDistributorGradeHis his : hisList) {
			distributorCode = his.getDistributorCode();
			tgGrade = tgMap.get(distributorCode);
			/**计算累计业绩，与个人累计业绩*/
			if(oldbatchNo==0 || !hisMap.containsKey(distributorCode)){
				/**第一个月才开始统计，或当月新加入的经销商，他们的个人累计业绩是当月的累计业绩*/
				accuPAchieve = tgGrade.getPersonAchieve();
			}else {
				/**否则是次月 + 当月的累计业绩*/
				double hislastMon = hisMap.get(distributorCode).getAccuPAchieve();
				accuPAchieve = hislastMon + tgGrade.getPersonAchieve();
				accuAchieve = hisMap.get(distributorCode).getAccuAchieve();
			}
			/**累计个人业绩**/
			tgGrade.setAccuPAchieve(accuPAchieve);
			/**累计业绩  == 个人业绩小于200部分+直接+间接+历史累计**/
			personAchieve_self = tgGrade.getPersonAchieve();
			personAchieve_bleow200=personAchieve_self<=200D?personAchieve_self:200D;
			tgGrade.setAccuAchieve(personAchieve_bleow200 + tgGrade.getDirectAchieve() + tgGrade.getIndirectAchieve() + accuAchieve);
			/**累计业绩入库*/
			TDistributorGradeHis copyHis = tgGrade.getHisGradeCopy(his);
			copyHis.setId(his.getId());
			copyHis.setBatchNo(oldbatchNo+1);
			copyHis.setAchieveDate(new Date());
			distributorGradeHisDao.update(copyHis);
			copyHis = null;
			/**经销商业绩保存*/
			distributorGradeDao.createOrUpdate(tgGrade);
			/**经销商职级保存*/
			tDistributorService.findEntity(tgGrade.getDistributorId()).setRankId(tgGrade.getRank());
			his = null;
		}
		hisMap = null;
		tgGrade = null;
		hisList = null;
		
		/**计算奖金，（直接奖，间接奖，领导奖，荣衔奖，特别奖，国际奖）其中BonusAchieve是计算奖金的累计*/
		List<TBounsConf> cfgList = bounsConfDao.findAll();
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
						if(selfAchieve>200 && indirChild.getRankId()<=rank){
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
					distBonus.setIndirectBouns((directBouns + indirectBouns)<0D?0D:(directBouns + indirectBouns)); /**①+②*/
					/**领导奖计算*/
					double leadership = calcLeaderShip(dist,rank,bonusCfgMap,dirchildList,tgMap);
					distBonus.setLeaderBouns(leadership);
					/************************计算完毕************************/
			}
			
			/**保存经销商奖金表*/
			distributorBounDao.create(distBonus);
			/**保存奖金历史表*/
			TDistributorBounsHis  hisBonus = distBonus.copyToHis(new TDistributorBounsHis());
			hisBonus.setBatchNo(oldbatchNo+1);
			distributorBounHisDao.create(hisBonus);
			hisBonus = null;
			dist = null;
		}
		distBonus = null;
		dirchildList = null;
		indirchildList = null;
		bouns = null;
		allDistributors = null;
		tgMap = null;
		bonusCfgMap = null;
		logger.info("计算成功，当前批次号 {} ， 计算日期 {}", oldbatchNo+1 ,new Date());
		return new ResponseData(true, "计算完毕！可以去<font size=4 color=red>报表管理</font>查看相关报表");
	
		/*try{}catch(Exception e){
			logger.error("计算失败， 计算日期 {}，请务必在当天重新计算，错误信息：{}", new Date(),e.getMessage());
			e.printStackTrace();
			return new ResponseData(true,"计算出现未知错误，请及时反馈！");
		}finally{
			
		}*/
	}
	
	
	
	/****
	 *   计算经销商职级
	 * @param distributorCode	经销商编号
	 * @param maxChange  		本月一次性最大消费
	 * @param tgGrade	   		该名经销商临时业绩
	 * @param tgMap		  		 经销商临时业绩缓存（因为是从下往上遍历，因此缓存的都是已经计算过的业绩）
	 * @param dirchildList 		直接下线经销商
	 */
	private void findRank(TDistributor dist , String distributorCode ,double maxChange , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList){
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
	private void findCellGrade(TDistributor dist ,String distributorCode , double netAchieve , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList){
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
	private double calcLeaderShip(TDistributor dist ,Long rank ,Map<Long , TBounsConf> bonusCfgMap,List<TDistributor> dirchildList,Map<String, TDistributorGrade> tgMap) {
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
		List<String> list = jdbcTemplate.queryForList(sql.toString(),String.class);
		return list;
	}
}
