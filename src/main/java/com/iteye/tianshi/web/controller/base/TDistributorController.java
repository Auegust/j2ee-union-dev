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
import com.iteye.tianshi.core.util.DictionaryHolder;
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
	 * 判断上级经销商是否存在（编号不存在，插入失败返回"上级经销商编号填写有误，数据库查无记录"）
	 * 判断数据库是否有记录，（针对插入第一个经销商的情况）
	 * 
	 * @param TDistributor
	 * @return ResponseData
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertTDistributor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTDistributor(TDistributor tDistributor)
			throws Exception {
		//上级编号不存在且数据库有经销商记录，则报请填写上级编号的异常
		if(!StringUtils.hasText(tDistributor.getSponsorCode())&& !tDistributorService.hasZeroRecord()){
			return new ResponseData(true,"上级编号不得为空，请填写上级编号");
		}
		//上级编号是否填写正确
		if(StringUtils.hasText(tDistributor.getSponsorCode()) && 
				tDistributorService.findByProperty("distributorCode", tDistributor.getSponsorCode()).isEmpty()){
			return new ResponseData(true,"上级编号填写有误，数据库查无记录");
		}
		SequenceAchieve sequenceAchieve = SequenceAchieve.getInstance();
		String distributorCode = sequenceAchieve.getDistributorCode();
		tDistributor.setDistributorCode(distributorCode);
		tDistributorService.insertEntity(tDistributor);
		return new ResponseData(true,"ok");
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
	 * 判断上级经销商是否存在（编号不存在，插入失败返回"上级经销商编号填写有误，数据库查无记录"）
	 * @param TDistributor
	 * @return ResponseData
	 */
	@RequestMapping(value = "/updateTDistributor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData updateTDistributor(TDistributor tDistributor) {
		tDistributorService.createOrUpdate(tDistributor);
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
		//查询条件
		String distCode = tDistributor.getDistributorCode();
		Long shopId = tDistributor.getShopId();
		String sponsorCode = tDistributor.getSponsorCode();
		if (StringUtils.hasText(distCode)) {
			//编号
			likeFilters.put("distributorCode", distCode);
		} else if (StringUtils.hasText(sponsorCode)) {
			//上级编号
			likeFilters.put("sponsorCode", sponsorCode);
		} else if (shopId!=null) {
			//所属店铺
			likeFilters.put("shop_id", shopId.toString());
		}
		Page<TDistributor> page = tDistributorService.findAllForPage(pageRequest);
		//将主键ID转换成名称回显
		for(TDistributor dist :page.getResult()){
			if(StringUtils.hasText(sponsorCode) ){
				String sponsor_Name = tDistributorService.findByProperty("sponsorCode", sponsorCode).get(0).getDistributorName();
				dist.setSponsor_Name(sponsor_Name);
			}
			if(shopId != null){
				String shop_Name = tShopInfoService.findEntity(shopId).getShopName();
				dist.setShop_Name(shop_Name);
			}
		}
		//将字典转换成名称回显（星级是字典项）
		DictionaryHolder.transfercoder(page.getResult(), 102L, "getRankId");
		return page;
	}

}
