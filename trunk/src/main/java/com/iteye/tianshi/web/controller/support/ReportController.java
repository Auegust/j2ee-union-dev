package com.iteye.tianshi.web.controller.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iteye.tianshi.core.web.controller.BaseController;

/**
 * 报表导航
 *
 * @datetime 2010-8-8 下午04:47:03
 * @author jiangzx@yahoo.com
 */
@Controller
@RequestMapping("/web")
public class ReportController extends BaseController {
	
	@RequestMapping("/report/{reportfilename}")
	public String index(@PathVariable String reportfilename){
		return "admin/report/"+reportfilename;
	}
}
