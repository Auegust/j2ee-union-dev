package com.iteye.tianshi.web.controller.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.service.base.TBounsConfService;
import com.iteye.tianshi.web.service.base.TDistributorBounService;
import com.iteye.tianshi.web.service.base.TDistributorBounsHisService;
import com.iteye.tianshi.web.service.base.TDistributorGradeHisService;
import com.iteye.tianshi.web.service.base.TDistributorGradeService;
import com.iteye.tianshi.web.service.base.TDistributorService;
import com.iteye.tianshi.web.service.base.TProductDetailService;
import com.iteye.tianshi.web.service.base.TShopInfoService;

/**
 * 经销商业绩表
 * 
 */
@Controller(value="gradeController")
@RequestMapping("/grade")
public class TDistributorGradeController extends BaseController {
	@Autowired
	TDistributorGradeDao tDistributorGradeDao;

	@Autowired
	TDistributorGradeService tDistributorGradeService;

	@Autowired
	TDistributorGradeHisService tDistributorGradeHisService;
	
	@Autowired
	TShopInfoService tShopInfoService;

	@Autowired
	TDistributorService tDistributorService;

	@Autowired
	TProductDetailService tProductDetailService;
	
	@Autowired
	TBounsConfService bounsConfService;
	
	@Autowired
	TDistributorBounService  distributorBounService ;
	
	@Autowired
	TDistributorBounsHisService  distributorBounsHisService ;
	
	/**任务调度调用计算的接口---test*/
	public ResponseData calc() {
		String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return calc(now);
	}
	
	/**
	 * 计算经销商业绩表，并且算出职级，历史记录只能查询前一个月的，因为每次计算完毕，更新历史业绩表会覆盖上一个月的历史业绩表
	 * @param endDate 2012-02-14T00:00:00
	 */
	@RequestMapping("/calc")
	@ResponseBody
	public ResponseData calc(@RequestParam(required = false)String  endDate) {
		return tDistributorGradeService.mainCompute(endDate);
	}
}
