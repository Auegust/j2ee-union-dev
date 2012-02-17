package com.iteye.tianshi.web.controller.support;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iteye.tianshi.core.util.UtilTool;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.controller.base.TDistributorGradeController;
import com.iteye.tianshi.web.dao.base.TShopInfoDao;
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
	TShopInfoDao daoTest;
	@Autowired
	TShopInfoService tShopInfoService;
	@Autowired(required = true)  
	private ApplicationContext ctx;
	@Autowired
	TDistributorGradeController gradeClockController;

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
	
	@RequestMapping("/calc")
	public String index(){
		return "admin/base/calc";
	}
	
	@RequestMapping("/clock")
	public void clock(){
		/**Quartz测试用例**/
		int count = daoTest.getJdbcTemplate().queryForInt("select count(*) from t_distributor");
		System.out.println("executing..."+count);
//		String endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//		gradeClockController.calc(endDate);
	}
	
	
	@RequestMapping("/control")
	public void control(@RequestParam(required=true) boolean close){
		 /**修改定时状态*/
		 reqClockStateChange(close);
		 /**获取定时器工厂*/
		 SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean) ctx.getBean("&myScheduler");
		 try {
			 if(close){
				 /**若未恢复，则恢复**/
				schedulerFactory.getScheduler().resumeAll();
				Thread.sleep(3L * 1000L); 
			 }else{
				 /**若未暂停，则暂停*/
				 schedulerFactory.getScheduler().pauseAll();
				 Thread.sleep(3L * 1000L); 
			 }
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前定时状态
	 * @return
	 */
	@RequestMapping("/getClockState")
	@ResponseBody
	public boolean getClockState(){
		return UtilTool.clock;
	}
	
	/**
	 * 修改当前定时状态
	 */
	private void reqClockStateChange(boolean close){
		UtilTool.clock = !close;
	}
}
