package com.iteye.tianshi.web.controller.base;

import java.util.HashMap;
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
import com.iteye.tianshi.web.model.base.TDistributorRank;
import com.iteye.tianshi.web.service.base.TBounsConfService;
import com.iteye.tianshi.web.service.base.TDistributorRankService;

/**
 * 奖金核算表 业务方法
 * @dateime 2012-1-13 下午01:38:26
 * @author chenfengming456@163.com
 */
@Controller 
@RequestMapping("/TBounsConf")
public class TBounsConfController extends BaseController {
	@Autowired 
	private TBounsConfService tBounsConfService;
	
	@Autowired
	private TDistributorRankService tRankService;
	@RequestMapping("/index")
	
	public String index(){
		return "admin/base/TBounsConf";
	}
	
	/**
	 * 获取所有职级ID和名称
	 * @dateime 2012-1-13 下午01:37:34
	 * @author chenfengming456@163.com
	 * @param 
	 * @return Map
	 */
	@RequestMapping(value="/getRanks",method=RequestMethod.POST)
	public Map<String, String> getRanks(){
		Map<String, String> rankMap = new HashMap<String, String>();
		List<TDistributorRank> list = tRankService.findAllEntity();
		for (TDistributorRank tRank:list) {
			rankMap.put(tRank.getId().toString(), tRank.getRankName());
		}
		return rankMap;
	}
	
	/**
	 * 增加奖金核算配置表记录
	 * @dateime 2012-1-13 下午01:42:02
	 * @author chenfengming456@163.com
	 * @param tBounsConf
	 */
	@RequestMapping(value = "/insertTBounsConf", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTBounsConf(TBounsConf tBounsConf){
		tBounsConfService.insertEntity(tBounsConf);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 修改奖金配置表记录
	 * @dateime 2012-1-13 下午01:54:18
	 * @author chenfengming456@163.com
	 * @param tBounsConf
	 * @return
	 */
	@RequestMapping(value = "/updateTBounsConf", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseData updateTBounsConf(TBounsConf tBounsConf){
		tBounsConfService.createOrUpdate(tBounsConf);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 删除奖金配置表记录
	 * @dateime 2012-1-13 下午01:53:55
	 * @author chenfengming456@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteTBounsConf", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteTBounsConf(Long id){
		tBounsConfService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询奖金配置表记录
	 * @dateime 2012-1-13 下午01:57:52
	 * @author chenfengming456@163.com
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findTBounsConf", method = RequestMethod.POST)
	@ResponseBody
	public TBounsConf findTBounsConf(Long id){
		return tBounsConfService.findEntity(id);
	}
	
	@RequestMapping("/pageQueryTBounsConf")
	@ResponseBody
	public Page<TBounsConf> pageQueryTBounsConf(
			@RequestParam("start") int startIndex,
			@RequestParam("limit") int pageSize, TBounsConf tBounsConf,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String dir) {
		PageRequest<TBounsConf> pageRequest = new PageRequest<TBounsConf>(
				startIndex, pageSize);
		if (StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);
		Map<String, String> likeFilters = pageRequest.getLikeFilters();
		Map<String, Object> filters = pageRequest.getFilters();
		Long randID= tBounsConf.getRankId();
		//根据职级查询
		if (StringUtils.hasText(randID+"")) {
			likeFilters.put("rankId", randID+"");
		} 
		Page<TBounsConf> page = tBounsConfService.findAllForPage(pageRequest);
		
		//将职级对于的名称查出来 放入remark字段 方便前台调用
		Map<String, String> map =  getRanks();
		for(TBounsConf tBounsConf2:page.getResult()){
			String rankName = map.get(tBounsConf2.getRankId());
			tBounsConf2.setRemark(rankName);
		}
		return page;
	}

}
