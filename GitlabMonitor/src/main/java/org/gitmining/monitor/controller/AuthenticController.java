package org.gitmining.monitor.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.gitmining.monitor.bean.User;
import org.gitmining.monitor.service.MailService;
import org.gitmining.monitor.service.UserService;
import org.gitmining.monitor.util.ResultMap;
import org.gitmining.monitor.util.URLMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
			bindingResult.rejectValue("name", "validate.name.exist", "this name exists");
			return "register";
		}
		String token = generateToken(20);
		user.setToken(token);
		user.setStatus(0);
		userService.newUser(user);
		return "redirect:/activation/" + user.getName();
	}
	
	@RequestMapping(value="/activation/{name}")
	public String activateUser(@PathVariable String name, Model model) {
		User user = userService.getUserByName(name);
		//发送邮件
		String toEmail = user.getEmail();
		String title = "GitlabMonitor验证邮箱登陆";
		//http://www.gitmining.net
		String content = "Click "
						+"<a href=\""
						+"http://localhost:8080/GitlabMonitor/activationEmail" + "/"
						+ user.getName() + "/"
						+ user.getToken()
						+ "\">here</a> to activate your email(if not works, you can copy the link to your browser)";
		String content2 = "<a href=\""
				+"http://www.gitmining.net" + "\">点击</a>";
		mailService.sendHtmlMail(toEmail, title, content);
		model.addAttribute(user);
		model.addAttribute("emailSend", "activation email has been send, please check it.");
		return "activationResult";
	}
	
	@RequestMapping(value="/activationEmail/{name}/{token}")
	public String activateUserEmail(@PathVariable String name, @PathVariable String token, Model model) {
		User user = userService.getUserByName(name);
		if(user == null) {
//			System.out.println("noName");
			model.addAttribute("noName", "no such user");
			return "activationResult";
		}
		model.addAttribute("user", user);
		if(!user.getToken().equals(token)){
//			System.out.println("EmailActivationFail");
			model.addAttribute("emailActivationFail", "email activation fail");
			return "activationResult";
		}
		int status = user.getStatus();
		if(status == 0 || status == 1) status = 1;
		else status = 3;
		user.setStatus(status);
		userService.changeUserStatus(user);
		model.addAttribute("emailActivationSuccess", "email activation success");
		return "activationResult";
	}
	
	@RequestMapping(value="/admin/activation/{name}")
	public String adminActivateUser(@PathVariable String name, Model model) {
		User user = userService.getUserByName(name);
		int status = user.getStatus();
		if(status == 0 || status == 2) status = 2;
		else status = 3;
		user.setStatus(status);
		userService.changeUserStatus(user);
		return "userActivation";
	}
	
	@RequestMapping(value="/admin/unactivatedUsers")
	public String getUnactivatedUsers(HttpServletRequest request, Model model) {
		List<User> users = userService.getUnactivatedUsers();
		model.addAttribute(users);
		return "userActivation";
	}
	
	@RequestMapping(value="/admin/users")
	public String getUsers(HttpServletRequest request, Model model) {
		User user = new User();
		
		List<User> users = userService.getUsers(user);
		model.addAttribute(users);
		return "userManage";
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
