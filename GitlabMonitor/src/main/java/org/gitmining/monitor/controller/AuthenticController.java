package org.gitmining.monitor.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.gitmining.monitor.bean.User;
import org.gitmining.monitor.service.MailService;
import org.gitmining.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class AuthenticController {
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/login")
	public ModelAndView showLogin(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView();
		view.setViewName("login");
		return view;
	}
	
	@RequestMapping("/register1")
	public ModelAndView showSign(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView();
		view.setViewName("register1");
		return view;
	}
	
	@RequestMapping("/register")
	public String showSign(Model model) {
		model.addAttribute(new User());
		return "register";
	}

	@RequestMapping("/register/add") 
	public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute(user);
			return "register";
		}
		if(userService.getUserByName(user.getName()) != null) {
			bindingResult.rejectValue("name", "this name exists");
			return "register";
		}
		String token = generateToken(20);
		user.setToken(token);
		user.setStatus(0);
//		userService.newUser(user);
		return "redirect:/activation/" + user.getName();
	}
	
	@RequestMapping(value="/activation/{name}")
	public String activateUser(@PathVariable String name, Model model) {
//		User user = userService.getUserByName(name);
//		//发送邮件
//		String toEmail = user.getEmail();
//		String title = "GitlabMonitor验证邮箱登陆";
//		String content = "请点击一下连接进行认证"
//						+"<a href=\""
//						+"http://localhost:8080/GitlabMonitor/activationEmail"
//						+ user.getName() + "/"
//						+ user.getToken()
//						+ "\">点击</a>";
		
		User user = new User();
		user.setName(name);
		//发送邮件
		String toEmail = "604865895@qq.com";
		String title = "GitlabMonitor验证邮箱";
		String content = "请点击一下连接进行认证"
						+"<a href=\""
						+"http://localhost:8080/GitlabMonitor/activationEmail"+"/"
						+ name + "/"
						+ generateToken(20)
						+ "\">点击</a>";
		mailService.sendMail(toEmail, title, content);
//		model.addAttribute(user);
		return "activation";
	}
	
	@RequestMapping(value="/activationEmail/{name}/{token}")
	public String activateUserEmail(@PathVariable String name, @PathVariable String token, Model model) {
		System.out.println("jixufangwen");
		User user = userService.getUserByName(name);
		if(user == null) {
			model.addAttribute("noName", "用户名不存在");
			return "activation";
		}
		if(!user.getToken().equals(token)){
			model.addAttribute("activationFail", "邮箱验证失败");
			return "activation";
		}
		int status = user.getStatus() + 1;
		user.setStatus(status);
		userService.saveUser(user);
		return "activation";
	}
	
	//生成随机Token
	private String generateToken(int length) {
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString(); 
	}
}
