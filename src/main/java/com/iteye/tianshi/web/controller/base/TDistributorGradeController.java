package com.iteye.tianshi.web.controller.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.googlecode.ehcache.annotations.Cacheable;
import com.iteye.tianshi.core.page.Page;
import com.iteye.tianshi.core.page.PageRequest;
import com.iteye.tianshi.core.util.SQLOrderMode;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TDistributorGrade;
import com.iteye.tianshi.web.model.base.TDistributorGradeHis;
import com.iteye.tianshi.web.model.base.TProductDetail;
import com.iteye.tianshi.web.model.base.TShopInfo;
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
	@Cacheable(cacheName = "distributorCache")
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
	 * 获取当月产品销售额(美元) map键放的是经销商编号 值放的是经销商业绩表里的每条记录值，每个值之间用|分隔
	 * 
	 * @dateime 2012-1-16 上午11:13:32
	 * @author chenfengming456@163.com
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TDistributorGrade> getCurSaleGrades() {
		List<TDistributorGrade> list = new ArrayList<TDistributorGrade>();
		
		String curYear = getCurDate().substring(0, 4);
		String curMon = getCurDate().substring(4, 6);
		String endDate = curYear + curMon + "25";// 结束日期
		String startDate = "";// 开始日期
		StringBuilder sql = new StringBuilder(
				"SELECT distributor_code,sum(sum_price) as sum_price ,sum(pv) as pv FROM t_product_list where sale_time>'");
		if (curMon.equals("01")) {
			startDate = (Integer.valueOf(curYear) - 1) + "" + "1225";
		} else {
			startDate = curYear + (Integer.valueOf(curMon) - 1) + "25";
		}
		sql.append(startDate + "' and sale_time<'").append(
				endDate + "' group by distributor_code");
		/**tgMap 键存放TDistributorGrade的编号属性    值放对于此编号的对象**/
		Map<String, TDistributorGrade> tgMap = new HashMap<String, TDistributorGrade>();
		// 获取经销商当月个人业绩(销售额)
		List<Map<String, Object>> tglist = tDistributorGradeDao.getJdbcTemplate().queryForList(sql.toString());
		/**将tglist数据拷贝到tgMap中去**/
		for(Map<String, Object> map:tglist){
			String distributor_code = map.get("distributor_code").toString();
			TDistributorGrade tgGrade = new TDistributorGrade();/**经销商编号**/
			tgGrade.setPersonAchieve((Double)map.get("sum_price"));/**当月总销售额**/
			tgGrade.setAccuAchieve((Double)map.get("pv"));/**当月个人业绩**/
			tgGrade.setAccuPAchieve((Double)map.get("sum_price"));/**累计个人总销售额**/
			tgMap.put(distributor_code, tgGrade);
			tgGrade = null;
		}

		/**
		 * 获取直接业绩 间接业绩 累计业绩
		 */
		// 从产品销售明细表里 从下往上查询经销商数据
		List<TProductDetail> acheveList = tProductDetailService
				.findByPropertysAndOrders(new String[] {}, new String[] {},
						new String[] {}, new String[] { "DISTRIBUTOR_CODE",
								"FLOORS" }, SQLOrderMode.DESC);
		//定义一个Map 键存放产品销售明细表里 从下往上查询经销商数据的编号 值放当前数据组成的对象TProductDetail
		Map<String, TProductDetail> tpMap = new HashMap<String, TProductDetail>();
		for (TProductDetail tProductDetail:acheveList) {
			tpMap.put(tProductDetail.getDistributorCode(), tProductDetail);
		}
		//取到销售网络中的最后一层
		int lstfloor=0;
		if(acheveList.size()!=0)
			acheveList.get(0).getFloors();
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
		for (TProductDetail tProductDetail : acheveList) {
			/**最后一层的经销商没有下线  只需考虑自己的个人累计pv值即可**/
			
			if (tProductDetail.getFloors()==lstfloor) {
				String distributorCode = tProductDetail.getDistributorCode();
				TDistributorGrade tgGrade = tgMap.get(distributorCode);
				double directAchieve = tProductDetail.getPv()>200?(tProductDetail.getPv()-200):0;/**本人个人累计业绩大于200PV部分。**/
				double indirectAchieve = 0;//无间接业绩
				/**直接业绩**/
				tgGrade.setDirectAchieve(directAchieve);
				/**间接业绩**/
				tgGrade.setIndirectAchieve(indirectAchieve);
				/**整网业绩**/
				tgGrade.setNetAchieve(tgGrade.getAccuAchieve());
				/**小组业绩**/
				tgGrade.setCellAchieve(tgGrade.getAccuAchieve());
			}else{/**从倒数第二层开始要考虑下线的个人累计pv部分和自己的个人累计pv部分**/
				Long distributorId = tProductDetail.getDistributorId();
				String distributorCode = tProductDetail.getDistributorCode();
				TDistributorGrade tgGrade = tgMap.get(distributorCode);
				int dirfloors = tProductDetail.getFloors()+1;/**直接下线层级**/
				List<TDistributor> dirchildList = tDistributorService.findAllDirChildrenDistributors(distributorId, dirfloors);/**所有直接子节点**/
				List<TDistributor> indirchildList = tDistributorService.findAllChildrenDistributors(distributorId, dirfloors);/**所有间接子节点**/
				double directAchieve_self = tProductDetail.getPv()>200?(tProductDetail.getPv()-200):0;/**本人个人累计业绩大于200PV部分。**/
				double directAchieve_down = tProductDetail.getPv()<=200?tProductDetail.getPv():200;/**所有直接下线个人累计业绩小于或等于200PV部分。**/
				double indirectAchieve_dirdown=0;/**所有直接下线个人累计业绩大于200PV部分。**/
				double indirectAchieve_indirdown=0;/**所有间接下线个人累计业绩之和。**/
				double dirnetAchieve=0;/**所有直接下线的个人累计。**/
				double indirnetAchieve=0;/**所有间接下线的个人累计。**/
				for(TDistributor tDistributor:dirchildList){
					indirectAchieve_dirdown += tpMap.get(tDistributor.getDistributorCode()).getPv()>200?(tProductDetail.getPv()-200):0;
					dirnetAchieve += tpMap.get(tDistributor.getDistributorCode()).getPv();
				}
				for(TDistributor tDistributor:indirchildList){
					indirectAchieve_indirdown += tpMap.get(tDistributor.getDistributorCode()).getPv();
					indirnetAchieve += tpMap.get(tDistributor.getDistributorCode()).getPv();
				}
				
				/**直接业绩**/
				tgGrade.setDirectAchieve(directAchieve_self+directAchieve_down);
				/**间接业绩**/
				tgGrade.setIndirectAchieve(indirectAchieve_dirdown+indirectAchieve_indirdown);
				/**整网业绩**/
				tgGrade.setNetAchieve(tgGrade.getAccuAchieve()+dirnetAchieve+indirnetAchieve);/**个人+直接下线+间接下线  累计之和**/
			}
		}
		
		/** 获取经销商累计个人业绩(销售额)**/
		List<TDistributorGradeHis> hisList = tDistributorGradeHisService
				.findAllEntity();
		for (TDistributorGradeHis his : hisList) {
			String distributorCode = his.getDistributorCode();
			TDistributorGrade tgGrade = tgMap.get(distributorCode);
			
			double accuPAchieve = his.getAccuPAchieve() + tgGrade.getPersonAchieve();
			/**累计个人业绩(销售额)**/
			tgGrade.setAccuPAchieve(accuPAchieve);
			/**累计业绩**/
			tgGrade.setAccuAchieve(his.getAccuAchieve()+tgGrade.getAccuAchieve());
		}

		
		return list;
	}
}
