package com.iteye.tianshi.web.controller.base;

import java.text.SimpleDateFormat;
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
 */
@Controller 
@RequestMapping("/order")
public class TProductOrderController extends BaseController {
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
		return "admin/base/order";
	}
	
	
	/**
	 * 订单录入
	 */
	@RequestMapping(value = "/insertTProductOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTProductOrder(TProductDetail tDetail){
		//总额
		tDetail.setSumPrice(tDetail.getSalePrice()*tDetail.getSaleNumber());
		TDistributor dist = tDistributorService.findByProperty("distributorCode", tDetail.getDistributorCode()).get(0);
		//经销商ID
		tDetail.setDistributorId(dist.getId());
		//层级
		tDetail.setFloors(dist.getFloors());
		tDetail.setCreateTime(new Date());
		tDetailService.insertEntity(tDetail);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 订单修改
	 */
	@RequestMapping(value = "/updateTProductOrder", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseData updateTProductOrder(TProductDetail tDetail){
		//总额
		tDetail.setSumPrice(tDetail.getSalePrice()*tDetail.getSaleNumber());
		TDistributor dist = tDistributorService.findByProperty("distributorCode", tDetail.getDistributorCode()).get(0);
		//经销商ID
		tDetail.setDistributorId(dist.getId());
		//层级
		tDetail.setFloors(dist.getFloors());
		tDetailService.updateEntity(tDetail);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 订单删除
	 */
	@RequestMapping(value = "/deleteTProductOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteTProductOrder(Long id){
		tDetailService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 订单加载
	 * 加载时通过经销商编号，查出经销商名称、专卖店编号、专卖店名称
	 * 通过产品编号，查出产品名称、产品价格、PV值、BV值
	 */
	@RequestMapping(value = "/loadTProductOrder", method = RequestMethod.POST)
	@ResponseBody
	public TProductDetail loadTProductOrder(Long id){
		TProductDetail order =  tDetailService.findEntity(id);
		
		TDistributor dist = tDistributorService.findByProperty("distributorCode", order.getDistributorCode()).get(0);
		order.setDistributorName(dist.getDistributorName());
		Long shopId = dist.getShopId();
		dist = null;
		
		TShopInfo shop = tShopInfoService.findEntity(shopId);
		order.setShopCode(shop.getShopCode());
		order.setShopName(shop.getShopName());
		shop = null;
		
		TProductInfo product = tProductInfoService.findByProperty("productCode", order.getProductCode()).get(0);
		order.setProductName(product.getProductName());
		return order;
	}
	
	/**
	 * 订单分页查询
	 */
	@RequestMapping("/pageQueryTProductOrder")
	@ResponseBody
	public Page<TProductDetail> pageQueryTProductOrder(
			@RequestParam("start") int startIndex,
			@RequestParam("limit") int pageSize, TProductDetail tDetail,
			@RequestParam(required = false)String startTime, 
			@RequestParam(required = false)String  endTime,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) throws Exception {
		PageRequest<TProductDetail> pageRequest = new PageRequest<TProductDetail>(
				startIndex, pageSize);
		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);
		Map<String, Object> filters = pageRequest.getFilters();
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		boolean isTimeDefine = false;
		if(StringUtils.hasText(startTime)){
			isTimeDefine = true;
			pageRequest.setStartTime(sdf.parse(startTime));
		}
		if(StringUtils.hasText(endTime)){
			isTimeDefine = true;
			pageRequest.setEndTime(sdf.parse(endTime));
		}
		if(isTimeDefine){
			pageRequest.setTimeField("createTime");	
		}
		//根据经销商编号查询
		if (StringUtils.hasText(tDetail.getDistributorCode())) {
			filters.put("distributorCode", tDetail.getDistributorCode());
		} 
		//根据产品编号查询
		if (StringUtils.hasText(tDetail.getProductCode())) {
			filters.put("productCode", tDetail.getProductCode());
		} 
		
		Page<TProductDetail> page = tDetailService.findAllForPage(pageRequest);
		List<TDistributor> disList  = null;
		List<TProductInfo>  productList = null;
		for (TProductDetail tProductDetail: page.getResult()) {
			String productCode = tProductDetail.getProductCode();
			String distributorCode = tProductDetail.getDistributorCode();
			disList = tDistributorService.findByProperty("distributorCode", distributorCode);
			if (!disList.isEmpty()) {
				Long shopId = disList.get(0).getShopId();
				if(shopId!=null){
					TShopInfo tShopInfo = tShopInfoService.findEntity(shopId);
					tProductDetail.setShopCode(tShopInfo.getShopCode());
					tProductDetail.setShopName(tShopInfo.getShopName());
				}
				tProductDetail.setDistributorName(disList.get(0).getDistributorName());
			}
			productList = tProductInfoService.findByProperty("productCode", productCode);
			if (!productList.isEmpty()) {
				tProductDetail.setPV(productList.get(0).getProductPv());
				tProductDetail.setBV(productList.get(0).getProductBv());
				tProductDetail.setProductName(productList.get(0).getProductName());
			}
			tProductDetail.setSalePrice(tProductDetail.getSalePrice());
		}
		disList  = null;
		productList = null;
		return page;
	}

}
