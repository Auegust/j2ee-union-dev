package com.iteye.tianshi.web.controller.base;

import java.util.ArrayList;
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
import com.iteye.tianshi.core.util.SequenceAchieve;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TShopInfo;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 * 经销商管理界面的业务方法
 * 
 * @datetime 2012-01-10 10:30:12
 * @author chenfengming456@163.com
 */
@Controller
@RequestMapping("/distributor")
public class TDistributorController extends BaseController {
	@Autowired
	private TDistributorService tDistributorService;

	@Autowired
	private TShopInfoService tShopInfoService;

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
	 * 公共操作,查询出数据库中所有专卖点的编号
	 * 
	 * @param
	 */
	@RequestMapping(value = "/findShopInfos")
	@ResponseBody
	public List<String> geTShopInfos() {
		List<TShopInfo> tShopInfoslList = tShopInfoService.findAllEntity();
		List<String> tShopInfosCodes = new ArrayList<String>();
		for (int i = 0; i < tShopInfoslList.size(); i++) {
			tShopInfosCodes.add(tShopInfoslList.get(i).getShopCode());
		}
		return tShopInfosCodes;
	}

	/**
	 * 新增经销商信息, 只接受POST请求
	 * 
	 * @param TDistributor
	 * @return ResponseData
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertTDistributor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTDistributor(TDistributor tDistributor)
			throws Exception {
		SequenceAchieve sequenceAchieve = SequenceAchieve.getInstance();
		String distributorCode = sequenceAchieve.getDistributorCode();
		tDistributor.setDistributorCode(distributorCode);
		tDistributorService.insertEntity(tDistributor);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 删除经销商, 只接受POST请求
	 * 
	 * @param id
	 * @return ResponseData
	 */
	@RequestMapping(value = "/deleteTDistributor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteUser(Long id) {
		tDistributorService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 更新经销商信息, 只接受POST请求
	 * 
	 * @param TDistributor
	 * @return ResponseData
	 */
	@RequestMapping(value = "/updateTDistributor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData updateTDistributor(TDistributor tDistributor) {
		tDistributorService.updateEntity(tDistributor);
		return ResponseData.SUCCESS_NO_DATA;
	}

	/**
	 * 查询经销商信息, 只接受POST请求
	 * 
	 * @param id
	 * @return TDistributor
	 */
	@RequestMapping(value = "/loadDistributor", method = RequestMethod.POST)
	@ResponseBody
	public TDistributor loadDistributor(Long id) {
		TDistributor tDistributor = tDistributorService.findEntity(id);
		return tDistributor;
	}

	/**
	 * 经销商管理，查询经销商信息，按照经销商优先级降序排序
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryTDistributors")
	@ResponseBody
	public Page<TDistributor> pageQueryDistributor(
			@RequestParam("start") int startIndex,
			@RequestParam("limit") int pageSize, TDistributor tDistributor,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PageRequest<TDistributor> pageRequest = new PageRequest<TDistributor>(
				startIndex, pageSize);

		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);

		Map<String, String> likeFilters = pageRequest.getLikeFilters();
		if (StringUtils.hasText(tDistributor.getDistributorCode())) {
			likeFilters.put("distributorCode", tDistributor
					.getDistributorCode());
		} else if (StringUtils.hasText(tDistributor.getSponsorId())) {
			likeFilters.put("distributorCode", tDistributor.getSponsorId());
		} else if (StringUtils.hasText(tDistributor.getTShopInfo()
				.getShopCode())) {
			likeFilters.put("shop_id", tDistributor.getTShopInfo()
					.getShopCode());
		} else if (StringUtils.hasText(tDistributor.getTShopInfo()
				.getShopCountry())) {
			likeFilters.put("shopCountry", tDistributor.getTShopInfo()
					.getShopCountry());
		}
		Page<TDistributor> page = tDistributorService
				.findAllForPage(pageRequest);
		return page;
	}

}
