package com.iteye.tianshi.web.controller.base;

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
import com.iteye.tianshi.web.service.base.TBounsConfService;
import com.iteye.tianshi.web.service.base.TDistributorRankService;

/**
 * 奖金核算表 业务方法
 */
@Controller 
@RequestMapping("/bonus")
public class TBounsConfController extends BaseController {
	@Autowired 
	private TBounsConfService tBounsConfService;
	
	@Autowired
	private TDistributorRankService tRankService;
	
	@RequestMapping("/index")
	public String index(){
		return "admin/base/bonus";
	}
	
	/**
	 * 增加奖金核算配置表记录
	 */
	@RequestMapping(value = "/insertTBounsConf", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData insertTBounsConf(TBounsConf tBounsConf){
		//职级已经存在，需要返回说明
		if(!tBounsConfService.findByProperty("rankId", tBounsConf.getRankId()).isEmpty()){
			return new ResponseData(true, "该职级配置已经存在，若需修改请执行更新操作！");
		}
		tBounsConfService.insertEntity(tBounsConf);
		return new ResponseData(true, "ok");
	}
	
	/**
	 * 修改奖金配置表记录
	 */
	@RequestMapping(value = "/updateTBounsConf", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseData updateTBounsConf(TBounsConf tBounsConf){
		//将原先的星级拿出来，并传递等级字段，因为前台传递过来的星级为空，会覆盖掉原来的星级
		TBounsConf oldBonus = tBounsConfService.findEntity(tBounsConf.getId());
		tBounsConf.setRankId(oldBonus.getRankId());
		tBounsConfService.updateEntity(tBounsConf);
		return new ResponseData(true,"ok");
	}
	
	/**
	 * 删除奖金配置表记录
	 */
	@RequestMapping(value = "/deleteTBounsConf", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteTBounsConf(Long id){
		tBounsConfService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询奖金配置表记录
	 */
	@RequestMapping(value = "/loadTBounsConf", method = RequestMethod.POST)
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
		Map<String, Object> filters = pageRequest.getFilters();
		Long rankId= tBounsConf.getRankId();
		//根据职级查询
		if (rankId!=null) {
			filters.put("rankId", rankId);
		} 
		Page<TBounsConf> page = tBounsConfService.findAllForPage(pageRequest);
		for(TBounsConf bonus :page.getResult()){
			bonus.setRankId_Name(tRankService.findEntity(bonus.getRankId()).getRankName());
		}
		return page;
	}

}
