package com.iteye.tianshi.web.controller.base;

import java.text.DecimalFormat;
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
import com.iteye.tianshi.core.util.DictionaryHolder;
import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.util.SequenceAchieve;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.dao.base.TProductInfoDao;
import com.iteye.tianshi.web.model.base.TProductInfo;
import com.iteye.tianshi.web.service.base.TProductInfoService;

/**
 * 产品界面的业务方法
 * 
 * @date 2012-01-10 19:30:33
 * @author chenfengming456@163
 */
@Controller
@RequestMapping("/product")
public class TProductInfoController extends BaseController {
	@Autowired
	TProductInfoService tProductInfoService;
	@Autowired
	private TProductInfoDao tProductInfoDao;
	
	@RequestMapping("/index")
	public String index() {
		return "admin/base/product";
	}

	/**
	 * 公共操作,根据产品编号查询出数据库中产品名称
	 * 
	 * @param
	 */
	@RequestMapping(value = "/findProductInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getTProductInfo(
			@RequestParam("productCode") String productCode) {
		List<TProductInfo> tProductInfoList = tProductInfoService.findByProperty("productCode", productCode);
		return tProductInfoList.get(0).getProductName();
	}
	
	/**
	 * 新增产品, 只接受POST请求
	 * 
	 * @param TProductInfo
	 * @return ResponseData
	 */
	@RequestMapping(value = "/insertTProductInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTProductInfo(TProductInfo tProductInfo) throws Exception {
		SequenceAchieve sequenceAchieve = SequenceAchieve.getInstance();
		String tProductInfoCode = sequenceAchieve.getTProductInfoCode(tProductInfoDao);
		tProductInfo.setProductCode(tProductInfoCode);
		tProductInfoService.insertEntity(tProductInfo);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 删除产品, 只接受POST请求
	 * 
	 * @param id
	 * @return ResponseData
	 */
	@RequestMapping(value = "/deleteTProductInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteTProductInfo(Long id) {
		tProductInfoService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 更新产品信息, 只接受POST请求
	 * 
	 * @param TProductInfo
	 * @return ResponseData
	 */
	@RequestMapping(value = "/updateTProductInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData updateTShopInfo(TProductInfo tProductInfo) {
		tProductInfoService.updateEntity(tProductInfo);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 查询产品信息, 只接受POST请求
	 * 
	 * @param id
	 * @return TShopInfo
	 */
	@RequestMapping(value = "/loadTProductInfo", method = RequestMethod.POST)
	@ResponseBody
	public TProductInfo loadDistributor(Long id) {
		return tProductInfoService.findEntity(id);
	}

	/**
	 * 产品管理，查询产品信息，按照产品优先级降序排序
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryTProductInfos")
	@ResponseBody
	public Page<TProductInfo> pageQueryDistributor(
			@RequestParam("start") int startIndex,
			@RequestParam("limit") int pageSize, TProductInfo tProductInfo,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PageRequest<TProductInfo> pageRequest = new PageRequest<TProductInfo>(
				startIndex, pageSize);
		
		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);

		Map<String, String> likeFilters = pageRequest.getLikeFilters();
		if (StringUtils.hasText(tProductInfo.getProductCode())) {
			likeFilters.put("productCode", tProductInfo.getProductCode());
		} 
		if (StringUtils.hasText(tProductInfo.getProductName())) {
			likeFilters.put("productName", tProductInfo.getProductName());
		} 
		Page<TProductInfo> page = tProductInfoService.findAllForPage(pageRequest);
		for(TProductInfo product : page.getResult()){
			product.setProductPrice_Name(new DecimalFormat("###,###.00").format(product.getProductPrice()));
			product.setProductBv_Name(new DecimalFormat("###,###.00").format(product.getProductBv()));
			product.setProductPv_Name(new DecimalFormat("###,###.00").format(product.getProductPv()));
		}
		DictionaryHolder.transfercoder(page.getResult(), 104L, "getStatus");
		return page;
	}
}
