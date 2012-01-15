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

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import com.iteye.tianshi.core.page.Page;
import com.iteye.tianshi.core.page.PageRequest;
import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.util.SequenceAchieve;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.dao.base.TDistributorDao;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.service.base.TDistributorRankService;
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
	@Autowired
	private TDistributorRankService rankService ;
	@Autowired
	private TDistributorDao tDistributorDao;

	@RequestMapping("/index")
	public String index() {
		return "admin/base/distributor";
	}

	/**
	 * 应预先插入一条记录代表顶级记录
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
	@TriggersRemove(cacheName = "distributorCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public ResponseData insertTDistributor(TDistributor tDistributor)
			throws Exception {
		//上级编号不存在且数据库有经销商记录，则报请填写上级编号的异常
		if(!StringUtils.hasText(tDistributor.getSponsorCode())){
			return new ResponseData(true,"上级编号不得为空，请填写上级编号");
		}
		//上级编号是否填写正确
		
		List<TDistributor> dist = tDistributorService.findByProperty("distributorCode", tDistributor.getSponsorCode());
		if(StringUtils.hasText(tDistributor.getSponsorCode()) && dist.isEmpty()){
			return new ResponseData(true,"上级编号填写有误，数据库查无记录");
		}
		//设置上级ID
		tDistributor.setSponsorId(dist.get(0).getId());
		//设置层数
		tDistributor.setFloors(dist.get(0).getFloors()+1);
		//生成编号
		SequenceAchieve sequenceAchieve = SequenceAchieve.getInstance();
		String distributorCode = sequenceAchieve.getDistributorCode(tDistributorDao);
		tDistributor.setDistributorCode(distributorCode);
		//初始化星级为一星
		tDistributor.setRankId(102001L);
		//当前时间
		tDistributor.setCreateTime(new Date());
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
	@TriggersRemove(cacheName = "distributorCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
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
	@TriggersRemove(cacheName = "distributorCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public ResponseData updateTDistributor(TDistributor tDistributor) {
		//上级编号未填写且数据库有经销商记录，则报请填写上级编号的异常
		if(!StringUtils.hasText(tDistributor.getSponsorCode())){
			return new ResponseData(true,"上级编号不得为空，请填写上级编号");
		}
		//上级编号是否填写正确
		if(StringUtils.hasText(tDistributor.getSponsorCode()) && 
				tDistributorService.findByProperty("distributorCode", tDistributor.getSponsorCode()).isEmpty()){
			return new ResponseData(true,"上级编号填写有误，数据库查无记录");
		}
		tDistributorService.updateEntity(tDistributor);
		return new ResponseData(true,"ok");
	}

	/**
	 * 查询经销商信息, 只接受POST请求
	 * 
	 * @param id
	 * @return TDistributor
	 */
	@RequestMapping(value = "/loadDistributor", method = RequestMethod.POST)
	@ResponseBody
	@Cacheable(cacheName="distributorCache")
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
	@Cacheable(cacheName="distributorCache")
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
		Map<String, Object> filters = pageRequest.getFilters();
		//查询条件
		String distCode = tDistributor.getDistributorCode();
		Long shopId = tDistributor.getShopId();
		String sponsorCode = tDistributor.getSponsorCode();
		Long rankId = tDistributor.getRankId();
		//根据编号查询
		if (StringUtils.hasText(distCode)) {
			likeFilters.put("distributorCode", distCode);
		} 
		//根据上级编号查询
		if (StringUtils.hasText(sponsorCode)) {
			likeFilters.put("sponsorCode", sponsorCode);
		} 
		//根据商铺查询
		if(shopId!=null){
			filters.put("shopId", shopId);
		}
		//根据职级查询
		if(rankId!=null){
			filters.put("rankId", rankId);
		}
		Page<TDistributor> page = tDistributorService.findAllForPage(pageRequest);
		//根节点不显示
		int rootIndex = -1;
		//将主键ID转换成名称回显
		for(TDistributor dist :page.getResult()){
			if(dist.getId() == -1L){ 
				rootIndex = page.getResult().indexOf(dist);
			}
			if(StringUtils.hasText(dist.getSponsorCode()) ){
				String sponsor_Name = tDistributorService.findByProperty("distributorCode", dist.getSponsorCode()).get(0).getDistributorName();
				dist.setSponsor_Name(sponsor_Name);
			}
			if(dist.getShopId() != null){
				String shop_Name = tShopInfoService.findEntity(dist.getShopId()).getShopName();
				dist.setShop_Name(shop_Name);
			}
			if(dist.getRankId()!=null){
				String rankId_Name = rankService.findEntity(dist.getRankId()).getRankName();
				dist.setRankId_Name(rankId_Name);
			}
		}
		if(rootIndex!=-1){
			page.getResult().remove(rootIndex);
		}
		return page;
	}

}
