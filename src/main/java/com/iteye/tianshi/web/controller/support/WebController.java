package com.iteye.tianshi.web.controller.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.model.base.TDistributorRank;
import com.iteye.tianshi.web.model.base.TShopInfo;
import com.iteye.tianshi.web.service.base.TDistributorRankService;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 * 一些公用的的业务方法
 *
 * @datetime 2010-8-8 下午04:47:03
 * @author jiangzx@yahoo.com
 */
@Controller
@RequestMapping("/web")
public class WebController extends BaseController {
	@Autowired
	TDistributorRankService rankService;
	@Autowired
	TShopInfoService tShopInfoService;
	/************
	 * 查询所有等级
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/findAllRank", method=RequestMethod.POST)
	@ResponseBody
	public List<TDistributorRank> findAllRank() {
		return rankService.findAllEntity();
	}
	
	/************
	 * 查询所有专卖店
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/findAllShop", method=RequestMethod.POST)
	@ResponseBody
	public List<TShopInfo> findAllShop() {
		return tShopInfoService.findAllEntity();
	}
	
}
