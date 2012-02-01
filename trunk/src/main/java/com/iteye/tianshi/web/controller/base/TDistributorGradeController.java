package com.iteye.tianshi.web.controller.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TDistributorGrade;
import com.iteye.tianshi.web.model.base.TDistributorGradeHis;
import com.iteye.tianshi.web.model.base.TShopInfo;
import com.iteye.tianshi.web.model.support.RankCache;
import com.iteye.tianshi.web.service.base.TDistributorGradeHisService;
import com.iteye.tianshi.web.service.base.TDistributorGradeService;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.TProductDetailService;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 * 经销商业绩表
 * 
 * @dateime 2012-1-16 上午12:12:45
 * @author chenfengming456@163.com
 */
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

	/**
	 * 获取所有专卖店信息
	 * 
	 * @dateime 2012-1-16 上午12:30:26
	 * @author chenfengming456@163.com
	 * @return
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
	 * 获取当前日期
	 * 
	 * @dateime 2012-1-16 上午10:09:34
	 * @author chenfengming456@163.com
	 * @return
	 */
	public String getCurDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public void accountGrade() {
		String curDate = getCurDate();// 当前日期
		// 判断当前日期是否是结算日期
		if (curDate.substring(6, 8).equals("25")) {

		}

	}

	/**
	 * 经销商管理，查询经销商信息，按照经销商优先级降序排序
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
	 * 
	 * @dateime 2012-1-16 上午11:13:32
	 * @author chenfengming456@163.com
	 * @return
	 */
	@RequestMapping("/calculateAll")
	@ResponseBody
	public boolean calculateAll() {
		
		String curYear = getCurDate().substring(0, 4);
		String curMon = getCurDate().substring(4, 6);
		String endDate = curYear + curMon + "25";// 结束日期
		String startDate = "";// 开始日期
		StringBuilder sql = new StringBuilder(
				"SELECT distributor_id ,distributor_code, MAX(pv) AS maxChange ,sum(sale_number * pv) as sum_price ,floors FROM t_product_list where create_time>'");
		if (curMon.equals("01")) {
			//当月一月
			startDate = (Integer.valueOf(curYear) - 1) + "" + "1225"; 	
		} else {
			//上月25号
			startDate = curYear + (Integer.valueOf(curMon) - 1) + "25"; 
		}
		/**根据经销商分组和按层级排序*/ 
		sql.append(startDate + "' and create_time<'").append(
				endDate + "' group by distributor_code order by floors desc");
		/**tgMap 键存放TDistributorGrade的编号属性    值放对于此编号的对象**/
		Map<String, TDistributorGrade> tgMap = new HashMap<String, TDistributorGrade>();
		List<Map<String, Object>> tglist = tDistributorGradeDao.getJdbcTemplate().queryForList(sql.toString());
		/**查询出所有经销商，按层级降序查询，并且初始化他们的个人业绩为零*/
		List<TDistributor> allDistributors = tDistributorService.findByPropertysAndOrder(new String[]{}, new String[]{}, new Object[]{}, "floors", SQLOrderMode.DESC);
		for(TDistributor it : allDistributors){
			String distributorCode = it.getDistributorCode();
			if(distributorCode.equals("-1000000")) //顶级不纳入计算
				continue;
			TDistributorGrade tgGrade = new TDistributorGrade();
			/**ID*/
			tgGrade.setDistributorId(it.getId());
			/**个人业绩（当月）**/
			tgGrade.setPersonAchieve(0D);
			/**当月最大消费*/
			tgGrade.setMaxChange(0D);
			/**计算日期*/
			tgGrade.setAchieveDate(new Date());
			tgMap.put(distributorCode, tgGrade);
			tgGrade = null;
		}
		/**将当月购买产品即具有个人业绩的经销商进行计算，而没有业绩的经销商的个人业绩归零（他们只有累计业绩）**/
		for(Map<String, Object> map:tglist){
			/**经销商编号**/
			String distributor_code = map.get("distributor_code").toString();
			TDistributorGrade tgGrade = tgMap.get(distributor_code);
			/**个人业绩（当月）**/
			tgGrade.setPersonAchieve((Double)map.get("sum_price")); 
			/**当月最大消费*/
			tgGrade.setMaxChange((Double)map.get("maxChange"));
			/**先将新加入的经销商的业绩（已计算出个人业绩和计算日期）拷贝到历史表*/
			if(tDistributorGradeHisService.findByProperty("distributorCode", distributor_code).isEmpty()){
				tDistributorGradeHisService.createOrUpdate(tgGrade.getHisGradeCopy());
			}
		}
		
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
		for (TDistributor dist : allDistributors) {
			/**最后一层，或者 非最后一层，但没有下线的经销商，  只需考虑自己的个人累计 业绩即可**/
			String distributorCode = dist.getDistributorCode();
			if(distributorCode.equals("-1000000")) //顶级不纳入计算
				continue;
			boolean noChild = tDistributorService.findByProperty("sponsor_code", distributorCode).isEmpty();
			TDistributorGrade tgGrade = null;
			List<TDistributor> dirchildList = null;
			List<TDistributor> indirchildList = null;  
			if (dist.getFloors()==lastfloor || noChild) {
				tgGrade = tgMap.get(distributorCode);
				/**个人业绩*/
				double personAchevePV = tgGrade.getPersonAchieve(); 
				double directAchieve = personAchevePV>200D?(personAchevePV-200D):0D;/**本人个人累计业绩大于200PV部分。**/
				double indirectAchieve = 0D;//无间接业绩
				/**直接业绩**/
				tgGrade.setDirectAchieve(directAchieve);
				/**间接业绩**/
				tgGrade.setIndirectAchieve(indirectAchieve);
				/**整网业绩**/
				tgGrade.setNetAchieve(tgGrade.getPersonAchieve());
				/**小组业绩**/
				tgGrade.setCellAchieve(tgGrade.getPersonAchieve());
			}else{/**从倒数第二层开始要考虑下线的个人累计pv部分和自己的个人累计pv部分**/
				Long distributorId = dist.getId();
				tgGrade = tgMap.get(distributorCode);
				double personAchevePV = tgGrade.getPersonAchieve();/**该经销商的本月个人业绩**/
				int dirfloors = dist.getFloors()+1;/**直接下线层级**/
				dirchildList = tDistributorService.findAllDirChildrenDistributors(distributorId, dirfloors);/**所有直接子节点**/
				indirchildList = tDistributorService.findAllChildrenDistributors(distributorId, dirfloors);/**所有间接子节点**/
				double directAchieve_self = personAchevePV>200D?(personAchevePV-200D):0D;/**本人个人累计业绩大于200PV部分。**/
				double directAchieve_down = 0D;/**所有直接下线个人累计业绩小于或等于200PV部分。**/
				double indirectAchieve_dirdown=0D;/**所有直接下线个人累计业绩大于200PV部分。**/
				double indirectAchieve_indirdown=0D;/**所有间接下线个人累计业绩之和。**/
				double dirnetAchieve=0D;/**所有直接下线的个人累计。**/
				double indirnetAchieve=0D;/**所有间接下线的个人累计。**/
				for(TDistributor tDistributor:dirchildList){
					/**直接下线经销商的个人业绩*/
					double dirchildPersonalAchevePV = tgMap.get(tDistributor.getDistributorCode()).getPersonAchieve();
					/**直接下线个人业绩大于200PV部分*/
					indirectAchieve_dirdown += dirchildPersonalAchevePV>200D?(dirchildPersonalAchevePV-200D):0D;
					/**直接下线个人业绩小于或等于200PV部分*/
					directAchieve_down += dirchildPersonalAchevePV<=200D?dirchildPersonalAchevePV:200D;
					/**用于算整网业绩用的 */
					dirnetAchieve += dirchildPersonalAchevePV;
				}
				for(TDistributor tDistributor:indirchildList){
					/**间接下线个人业绩之和*/
					indirectAchieve_indirdown += tgMap.get(tDistributor.getDistributorCode()).getPersonAchieve();
					/**用于算整网业绩用的 */
					indirnetAchieve += indirectAchieve_indirdown;
				}
				/**直接业绩**/
				tgGrade.setDirectAchieve(directAchieve_self+directAchieve_down);
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
				tgGrade.setRank(ConstantUtil._lev_1);
			}else{
				/**计算经销商职级*/
				findRank(distributorCode ,maxChange , tgGrade , tgMap , dirchildList);
			}
			dirchildList = null;
			indirchildList = null;
		}
		
		/** 获取经销商累计个人业绩(销售额)**/
		List<TDistributorGradeHis> hisList = tDistributorGradeHisService.findAllEntity();
		/**遍历历史表是因为历史表已经被初始化了，包括两部分，第一部分是以前存在的经销商，第二部分是新增加的经销商*/
		for (TDistributorGradeHis his : hisList) {
			String distributorCode = his.getDistributorCode();
			TDistributorGrade tgGrade = tgMap.get(distributorCode);
			/**分两种情况：1.该名经销商当月没有购买产品即在历史表中有记录业绩归零  2.该名经销商购买了产品，其业绩可以正常纳入计算*/
			double accuPAchieve = his.getAccuPAchieve() + tgGrade.getPersonAchieve();
			/**累计个人业绩**/
			tgGrade.setAccuPAchieve(accuPAchieve);
			/**累计业绩  == 个人业绩小于200部分+直接+间接+历史累计**/
			double personAchieve_self = tgGrade.getPersonAchieve();
			double personAchieve_bleow200=personAchieve_self<=200D?personAchieve_self:200D;
			tgGrade.setAccuAchieve(personAchieve_bleow200 + tgGrade.getDirectAchieve() + tgGrade.getIndirectAchieve() + his.getAccuAchieve());
			/**累计个人业绩--入历史库*/
			tDistributorGradeHisService.createOrUpdate(tgGrade.getHisGradeCopy());
			/**经销商业绩保存*/
			tDistributorGradeService.createOrUpdate(tgGrade);
			/**经销商职级保存*/
			tDistributorService.findEntity(tgGrade.getDistributorId()).setRankId(tgGrade.getRank());
		}
		return true;
	}
	
	/****
	 *   计算经销商职级
	 * @param distributorCode	经销商编号
	 * @param maxChange  		本月一次性最大消费
	 * @param tgGrade	   		该名经销商临时业绩
	 * @param tgMap		  		 经销商临时业绩缓存（因为是从下往上遍历，因此缓存的都是已经计算过的业绩）
	 * @param dirchildList 		直接下线经销商
	 */
	public void findRank(String distributorCode ,double maxChange , TDistributorGrade tgGrade , Map<String, TDistributorGrade> tgMap ,List<TDistributor> dirchildList){
		double personAchieve = tgGrade.getPersonAchieve(); //个人累计即个人业绩（当月）
		double netAchieve = tgGrade.getNetAchieve();		//累计即整网
		/**下面分别针对星级的条件进行判断*/
		if(dirchildList == null){ /**经销商无下线，小组业绩==个人业绩==整网业绩，之前已经计算过了*/
			/**（1）一次性购买大于或等于1000PV （2）个人累计大于或等于1000PV */
			if(maxChange>=1000D || personAchieve>=1000D){
				tgGrade.setRank(ConstantUtil._lev_4); //4*
			/**（1）一次性购买大于或等于200PV （2）个人累计大于或等于200PV */
			}else if(maxChange>=200D || personAchieve>=200D){
				tgGrade.setRank(ConstantUtil._lev_3); //3*
			/**（1）一次性购买大于或等于100PV */
			}else if(maxChange>=100){
				tgGrade.setRank(ConstantUtil._lev_2); //2*
			/** 个人购买  《天狮事业锦囊》一套*/
			}else{
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
				tgGrade.setRank(ConstantUtil._lev_s_3);
			/**三名八星直销商--银狮*/
			}else if(ownership.get("_lv_8")>=3){
				tgGrade.setRank(ConstantUtil._lev_s_2);
			/**二名八星直销商--铜师*/
			}else if(ownership.get("_lv_8")>=2){
				tgGrade.setRank(ConstantUtil._lev_s_1);
			/**（1）三条七星，累计300000PV （2）两条七星，累计600000PV （3）二条七星+四条六星，累计300000PV*/
			}else if((ownership.get("_lv_7")>=3 && netAchieve>=300000D)||
					(ownership.get("_lv_7")>=2 && netAchieve>=600000D)||
					(ownership.get("_lv_7")>=2 && ownership.get("_lv_6")>=5 && netAchieve>=300000D)){
				tgGrade.setRank(ConstantUtil._lev_8);
			/**（1）三条六星，累计75000PV （2）两条六星，累计150000PV（3）二条六星+四条五星，累计75000PV（4）一条六星+六条五星，累计75000PV*/
			}else if((ownership.get("_lv_6")>=3 && netAchieve>=75000D)||
					(ownership.get("_lv_6")>=2 && netAchieve>=150000D)||
					(ownership.get("_lv_6")>=2 && ownership.get("_lv_5")>=5 && netAchieve>=75000D)||
					(ownership.get("_lv_6")>=1 && ownership.get("_lv_5")>=7 && netAchieve>=75000D)){
				tgGrade.setRank(ConstantUtil._lev_7);
			/**（1）三条五星，累计18000PV；（2）两条五星，累计36000PV （3）二条五星+四条四星，累计18000PV （4）一条五星+六条四星，累计18000PV*/
			}else if((ownership.get("_lv_5")>=3 && netAchieve>=18000D)||
					(ownership.get("_lv_5")>=2 && netAchieve>=36000D)||
					(ownership.get("_lv_5")>=2 && ownership.get("_lv_4")>=5 && netAchieve>=18000D)||
					(ownership.get("_lv_5")>=1 && ownership.get("_lv_4")>=7 && netAchieve>=18000D)		){
				tgGrade.setRank(ConstantUtil._lev_6);
			/**（1）三条四星，累计4000PV； （2）两条四星，累计8000PV（3）二条四星+四条三星，累计4000PV （4）一条四星+六条三星，累计4000PV*/
			}else if((ownership.get("_lv_4")>=3 && netAchieve>=4000D)||
					 (ownership.get("_lv_4")>=2 && netAchieve>=8000D)||
					 (ownership.get("_lv_4")>=2 && ownership.get("_lv_3")>=5 && netAchieve>=4000D)||
					 (ownership.get("_lv_4")>=1 && ownership.get("_lv_3")>=7 && netAchieve>=4000D)){
				tgGrade.setRank(ConstantUtil._lev_5);
			/**（1）一次性购买大于或等于1000PV （2）个人累计大于或等于1000PV（3）三条三星，累计1200PV（4）二条三星，累计2400PV*/
			}else if(maxChange>=1000D||
					 personAchieve>=1000||
					 (ownership.get("_lv_3")>=3 && netAchieve>=1200D)||
					 (ownership.get("_lv_3")>=2 && netAchieve>=2400D)){
				tgGrade.setRank(ConstantUtil._lev_4);
			/**(1)个人累计购买大于或等于200PV（2）一次性购买大于或等于200PV*/
			}else if(maxChange>=200D || personAchieve>=200D){
				tgGrade.setRank(ConstantUtil._lev_3);
			/**一次性购买产品额大于或等于100PV*/
			}else if(maxChange>=100D){
				tgGrade.setRank(ConstantUtil._lev_2);
			/** 个人购买  《天狮事业锦囊》一套*/
			}else{
				tgGrade.setRank(ConstantUtil._lev_1);
			}
			ownership = null;
			/**计算小组业绩*/
			findCellGrade(distributorCode,netAchieve,tgGrade,tgMap,dirchildList);
		}
	}
	/**
	 * 	计算小组业绩（X-Y）
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
	public double findMaxRankUnderNet(Long rank , TDistributor child ,Map<String, TDistributorGrade> tgMap){
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
