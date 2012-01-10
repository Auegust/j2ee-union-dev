package com.iteye.tianshi.web.controller.base;

import java.util.ArrayList;
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
import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.util.SequenceAchieve;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TShopInfo;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 * 专卖店管理界面的业务方法
 * 
 * @datetime 2012-01-10 14:27:09
 * @author chenfengming456@163.com
 */
public class TShopInfoController extends BaseController {
	@Autowired
	TShopInfoService tShopInfoService;

	@Autowired
	TDistributorService tDistributorService;

	@RequestMapping("/index")
	public String index() {
		return "admin/base/distributor";
	}

	/**
	 * 公共操作,根据经销商编号回显经销商相关信息,只接受POST请求
	 * 
	 * @param TDistributor
	 * @return ResponseData
	 */
	@RequestMapping(value = "/findDistributor", method = RequestMethod.POST)
	@ResponseBody
	public TDistributor findDistributor(
			@RequestParam("tDistributorCode") String tDistributorCode) {
		List<TDistributor> tDistributorList = tDistributorService
				.findByProperty("distributorCode", tDistributorCode);
		return tDistributorList.get(0);
	}

	/**
	 * 公共操作,查询出数据库中所有专卖点的编号和名称
	 * 
	 * @param
	 */
	@RequestMapping(value = "/findShopInfo")
	@ResponseBody
	public List<Map<String, String>> geTShopInfos() {
		List<TShopInfo> tShopInfoslList = tShopInfoService.findAllEntity();
		List<Map<String, String>> tShopInfosCodes = new ArrayList<Map<String, String>>();
		for (int i = 0; i < tShopInfoslList.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(tShopInfoslList.get(i).getShopCode(), tShopInfoslList
					.get(i).getShopName());
			tShopInfosCodes.add(map);
			map = null;
		}
		return tShopInfosCodes;
	}

	/**
	 * 新增专卖店, 只接受POST请求
	 * 
	 * @param TShopInfo
	 * @return ResponseData
	 */
	@RequestMapping(value = "/insertTShopInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTShopInfo(TShopInfo tShopInfo) throws Exception {
		SequenceAchieve sequenceAchieve = SequenceAchieve.getInstance();
		String tShopCode = sequenceAchieve.getTShopInfoCode();
		tShopInfo.setShopCode(tShopCode);
		tShopInfoService.insertEntity(tShopInfo);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 删除专卖店, 只接受POST请求
	 * 
	 * @param id
	 * @return ResponseData
	 */
	@RequestMapping(value = "/deleteTShopInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteUser(Long id) {
		tShopInfoService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 更新专卖店信息, 只接受POST请求
	 * 
	 * @param TShopInfo
	 * @return ResponseData
	 */
	@RequestMapping(value = "/updateTShopInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData updateTShopInfo(TShopInfo tShopInfo) {
		tShopInfoService.updateEntity(tShopInfo);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 查询专卖店信息, 只接受POST请求
	 * 
	 * @param id
	 * @return TShopInfo
	 */
	@RequestMapping(value = "/loadTShopInfo", method = RequestMethod.POST)
	@ResponseBody
	public TShopInfo loadDistributor(Long id) {
		TShopInfo tShopInfo = tShopInfoService.findEntity(id);
		return tShopInfo;
	}

	/**
	 * 专卖店管理，查询专卖店信息，按照专卖店优先级降序排序
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryTShopInfos")
	@ResponseBody
	public Page<TShopInfo> pageQueryDistributor(
			@RequestParam("start") int startIndex,
			@RequestParam("limit") int pageSize, TShopInfo tShopInfo,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PageRequest<TShopInfo> pageRequest = new PageRequest<TShopInfo>(
				startIndex, pageSize);

		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);

		Map<String, String> likeFilters = pageRequest.getLikeFilters();
		if (StringUtils.hasText(tShopInfo.getShopCode())) {
			likeFilters.put("shopCode", tShopInfo.getShopCode());
		} else if (StringUtils.hasText(tShopInfo.getShopCountry())) {
			likeFilters.put("shopCountry", tShopInfo.getShopCountry());
		} else if (StringUtils.hasText(tShopInfo.getShopCity())) {
			likeFilters.put("shopCity", tShopInfo.getShopCity());
		} else if (StringUtils.hasText(tShopInfo.getShopName())) {
			likeFilters.put("shopName", tShopInfo.getShopName());
		} 
		Page<TShopInfo> page = tShopInfoService.findAllForPage(pageRequest);
		return page;
	}

}
