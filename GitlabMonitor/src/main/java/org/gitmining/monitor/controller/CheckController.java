package org.gitmining.monitor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.User;
import org.gitmining.monitor.service.MailService;
import org.gitmining.monitor.service.UserService;
import org.gitmining.monitor.util.ResultMap;
import org.gitmining.monitor.util.URLMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class CheckController {
	@Autowired
	private UserService userService;
	@Autowired
	private MailService MailService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setMailService(MailService mailService) {
		MailService = mailService;
	}

	@RequestMapping(value=URLMapping.CHECK_RRGISTER_ITEM)
	public ResultMap checkRegisterItem(HttpServletRequest request,HttpServletResponse response){
		ResultMap resultMap = new ResultMap();
		String item = request.getParameter("item");
		String value = request.getParameter("value");
		if(value == null || item == null || value.length() == 0 || !(item.equals("name") || item.equals("email"))){
			resultMap.setStatus(ResultMap.FAIL_STATUS);
			resultMap.setInfo("unsupported request!");
		}else{
			boolean valid = userService.checkUserNotExist(item, value);
			if(!valid){
				resultMap.setStatus(ResultMap.FAIL_STATUS);
				resultMap.setInfo(item + " already exists, please change");
			}else{
				resultMap.setStatus(ResultMap.SUCCESS_STATUS);
				resultMap.setInfo("ok");
			}
		}
		return resultMap;
	}
	
	@RequestMapping(value=URLMapping.LOGIN_FORGET)
	public ModelAndView passwordForget(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("forget");
		return view;
	}
	
	@RequestMapping(value=URLMapping.LOGIN_FORGET_EMAIL)
	public ResultMap sendForgetMail(HttpServletRequest request,HttpServletResponse response){
		
		ResultMap resultMap = new ResultMap();
		String email = request.getParameter("email");
		
		
		User user = userService.getUserByItem("email", email);
		if(user == null){
			resultMap.setStatus(ResultMap.FAIL_STATUS);
			resultMap.setInfo("Can not find this email, maybe you haven't registered !");
		}else{
			String form = "http://gitmining.net/GitlabMonitor/login/reset?email="+user.getEmail()+"&token="+user.getToken();
			MailService.sendHtmlMail(email, "Reset GitlabMonitor Password", "<p>if you want to reset your password, click <a href=\""+form+"\">here</a>. Otherwise omit this Email.</p><p>if the click doesn't work, copy this url into your browser:</p><p>"+form+"</p>");
		}
		return resultMap;
	}
	
	@RequestMapping(value=URLMapping.LOGIN_RESET)
	public ModelAndView loginResetPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("reset");
		view.addObject("email", request.getParameter("email"));
		view.addObject("token", request.getParameter("token"));
		return view;
	}
	
	@RequestMapping(value=URLMapping.LOGIN_PASSWORD_RESET)
	public ResultMap passwordReset(HttpServletRequest request,HttpServletResponse response){
		ResultMap resultMap = new ResultMap();
		try{
			String email = request.getParameter("email");
			String token = request.getParameter("token");
			String password = request.getParameter("password");
			User user = new User();
			user.setEmail(email);
			user.setToken(token);
			user.setPassword(password);
			int modify = userService.modifyUserByEmailToken(user);
			System.out.println(modify);
			resultMap.setStatus(ResultMap.SUCCESS_STATUS);
			resultMap.setInfo("password modified");
		}catch(Exception e){
			resultMap.setStatus(ResultMap.ERROR_STATUS);
			resultMap.setInfo("server error");
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
