package com.iteye.tianshi.web.controller.base;

import java.util.Date;
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
import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.model.base.TBounsConf;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TProductDetail;
import com.iteye.tianshi.web.model.base.TProductInfo;
import com.iteye.tianshi.web.model.base.TShopInfo;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.TProductDetailService;
import com.iteye.tianshi.web.service.base.TProductInfoService;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 * 订单录入 业务方法
 * @dateime 2012-1-13 下午03:22:08
 * @author chenfengming456@163.com
 */
@Controller 
@RequestMapping("/TProductDetail")
public class TProductDetailController extends BaseController {
	@Autowired
	private TProductDetailService tDetailService;
	
	@Autowired
	private TDistributorService tDistributorService;
	
	@Autowired
	private TShopInfoService tShopInfoService;
	
	@Autowired
	private TProductInfoService tProductInfoService;
	
	@RequestMapping("/index")
	public String index(){
		return "admin/base/TBounsConf";
	}
	
	/**
	 * 获取经销商信息
	 * @dateime 2012-1-13 下午04:17:37
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getDistributorName",method=RequestMethod.POST)
	public String getDistributorName(String distributorCode) throws Exception{
		List<TDistributor> list = tDistributorService.findByProperty("distributorCode", distributorCode);
		if (list.size()!=0) {
			return list.get(0).getDistributorName();
		}else{
			throw new Exception("经销商编号不存在");
		}
	}
	
	/**
	 * 获取产品信息
	 * @dateime 2012-1-13 下午04:17:37
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getProductName",method=RequestMethod.POST)
	public String getProductName(String productCode) throws Exception{
		List<TProductInfo> list = tProductInfoService.findByProperty("productCode", productCode);
		if (list.size()!=0) {
			return list.get(0).getProductName();
		}else{
			throw new Exception("产品编号不存在");
		}
	}
	
	/**
	 * 获取专卖店信息
	 * @dateime 2012-1-13 下午04:17:37
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getShopName",method=RequestMethod.POST)
	public String getShopName(String shopCode) throws Exception{
		List<TShopInfo> list = tShopInfoService.findByProperty("shopCode", shopCode);
		if (list.size()!=0) {
			return  list.get(0).getShopName();
		}else{
			throw new Exception("专卖店编号不存在");
		}
	}
	
	/**
	 * 订单录入
	 * @dateime 2012-1-13 下午04:40:03
	 * @author chenfengming456@163.com
	 * @param tDetail
	 * @return
	 */
	@RequestMapping(value = "/insertTProductDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTBounsConf(TProductDetail tDetail){
		tDetailService.insertEntity(tDetail);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 订单修改
	 * @dateime 2012-1-13 下午04:40:15
	 * @author chenfengming456@163.com
	 * @param tDetail
	 * @return
	 */
	@RequestMapping(value = "/updateTProductDetail", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseData updateTBounsConf(TProductDetail tDetail){
		tDetailService.createOrUpdate(tDetail);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 订单删除
	 * @dateime 2012-1-13 下午04:40:36
	 * @author chenfengming456@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteTProductDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteTBounsConf(Long id){
		tDetailService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 订单查询
	 * @dateime 2012-1-13 下午04:40:52
	 * @author chenfengming456@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findTProductDetail", method = RequestMethod.POST)
	@ResponseBody
	public TProductDetail findTBounsConf(Long id){
		return tDetailService.findEntity(id);
	}
	
	@RequestMapping("/pageQueryTProductDetail")
	@ResponseBody
	public Page<TProductDetail> pageQueryTProductDetail(
			@RequestParam("start") int startIndex,
			@RequestParam("limit") int pageSize, TProductDetail tDetail,
			@RequestParam(required = false) String distributorCode,
			@RequestParam(required = false) String productCode,
			@RequestParam(required = false) String shopCode,
			@RequestParam(required = false) Date startTime,
			@RequestParam(required = false) Date endTime,			
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PageRequest<TProductDetail> pageRequest = new PageRequest<TProductDetail>(
				startIndex, pageSize);
		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);
//		Map<String, String> likeFilters = pageRequest.getLikeFilters();
		Map<String, Object> filters = pageRequest.getFilters();
		pageRequest.setStartTime(startTime);
		pageRequest.setEndTime(endTime);
		//根据经销商编号查询
		if (StringUtils.hasText(distributorCode)) {
			filters.put("distributorCode", distributorCode);
		} 
		//根据产品编号查询
		if (StringUtils.hasText(productCode)) {
			filters.put("productCode", productCode);
		} 
		
		//根据专卖店编号查询
		if (StringUtils.hasText(shopCode)) {
			filters.put("shopCode", shopCode);
		} 
		
		Page<TProductDetail> page = tDetailService.findAllForPage(pageRequest);
		
		return page;
	}

}
