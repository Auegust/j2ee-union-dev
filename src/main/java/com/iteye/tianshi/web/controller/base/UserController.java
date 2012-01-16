package com.iteye.tianshi.web.controller.base;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.iteye.tianshi.web.model.base.User;
import com.iteye.tianshi.web.service.base.UserService;

/**
 * 用户管理界面的业务方法
 *
 * @datetime 2010-8-8 下午04:47:03
 * @author jiangzx@yahoo.com
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	//~ Instance fields ================================================================================================
	@Autowired
	private UserService userService;
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/base/user";
	}
	
	/**
	 * 用户管理，查询用户信息，按照用户优先级降序排序
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryUsers")
	@ResponseBody
	public Page<User> pageQueryUsers(@RequestParam("start")int startIndex, 
			@RequestParam("limit")int pageSize, User user, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PageRequest<User> pageRequest = new PageRequest<User>(startIndex, pageSize);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);
		
		Map<String, String> likeFilters = pageRequest.getLikeFilters();
		if(StringUtils.hasText(user.getUsername()))
			likeFilters.put("username", user.getUsername());
		Page<User> page = userService.findAllForPage(pageRequest);
		return page;
	}
	
	/**
	 * 保存用户, 只接受POST请求
	 * 
	 * @param user
	 * @return ResponseData
	 */
	@RequestMapping(value="/insertUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertUser(User user) {
		//初始密码：000000
		user.setCreateTime(new Date());
		userService.insertEntity(user);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询用户, 只接受POST请求
	 * 
	 * @param id
	 * @return User
	 */
	@RequestMapping(value="/loadUser", method=RequestMethod.POST)
	@ResponseBody
	public User loadUser(Long id) {
		User user = userService.findEntity(id);
		user.setPassword(null);
		return user;
	}
	
	/**
	 * 更新用户, 只接受POST请求
	 * 
	 * @param user
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateUser(User user) {
		User oldUser = userService.findEntity(user.getId());
		oldUser.setUsername(user.getUsername());
		userService.updateEntity(oldUser);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 删除用户, 只接受POST请求
	 * 
	 * @param id
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteUser", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteUser(Long id) {
		userService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	@RequestMapping("/login") 
	@ResponseBody
	public ResponseData login(HttpServletRequest request) {
		User user = new User();
		String userName = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		
		List<User> list  =userService.findByProperty("username", userName);
		
		if(list.size() == 0) {
			return new ResponseData(false, "UsernameNotFound", "用户【" + userName + "】不存在.");
		} else if(!password.equals(list.get(0).getPassword())) {
			return new ResponseData(false, "BadCredentials", "密码不正确，请重新输入.");
		} else {
			user.setUsername("admin");
			HttpSession session = request.getSession();
			session.setAttribute("__SESSIONKEY__", user);
			return ResponseData.SUCCESS_NO_DATA;
		}
	}
	
	@RequestMapping("/logout") 
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/login");   
	}
}
