package org.gitmining.monitor.controller;

import java.util.List;
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
	
	/**
	 * mapping to the login page
	 * @param request
	 * @param response
	 * @return
	 */
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
	
	/**
	 * activate a new registered account
	 * @param name
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/activation/{name}")
	public String activateUser(@PathVariable String name, Model model) {
		User user = userService.getUserByName(name);
		//发送邮件
		String toEmail = user.getEmail();
		String title = "GitlabMonitor验证邮箱登陆";
		//http://www.gitmining.net
		String content = "Click "
						+"<a href=\""
						+"http://www.gitmining.net/GitlabMonitor/activationEmail" + "/"
						+ user.getName() + "/"
						+ user.getToken()
						+ "\">here</a> to activate your email(if not works, you can copy the link to your browser)";
		mailService.sendHtmlMail(toEmail, title, content);
		model.addAttribute(user);
		model.addAttribute("emailSend", "activation email has been send, please check it.");
		return "activationResult";
	}
	
	@RequestMapping(value="/activationEmail/{name}/{token}")
	public String activateUserEmail(@PathVariable String name, @PathVariable String token, Model model) {
		User user = userService.getUserByName(name);
		if(user == null) {
			model.addAttribute("noName", "no such user");
			return "activationResult";
		}
		
		if(!user.getToken().equals(token)){
			model.addAttribute("user", user);
			model.addAttribute("emailActivationFail", "email activation fail");
			return "activationResult";
		}
		int status = user.getStatus();
		if(status == 0 || status == 1) status = 1;
		else status = 3;
		user.setStatus(status);
		userService.changeUserStatus(user);
		model.addAttribute("emailActivationSuccess", "email activation success");
		model.addAttribute("user", user);
		return "activationResult";
	}
	
	/**
	 * after activating by email confirmed, we need to double confirm the account by a system admin
	 * @param name
	 * @param from
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/admin/activation/{name}/{from}")
	public String adminActivateUser(@PathVariable String name,
									@PathVariable String from, Model model) {
		User user = userService.getUserByName(name);
		int status = user.getStatus();
		if(status == 0 || status == 2) status = 2;
		else status = 3;
		user.setStatus(status);
		userService.changeUserStatus(user);
		
		if(from.equals("userManage") ) {
			return "redirect:/admin/users";
		} 
		return "redirect:/admin/unactivatedUsers";
	}
	
	@RequestMapping(value="/admin/unactivatedUsers")
	public String getUnactivatedUsers(HttpServletRequest request, Model model) {
		List<User> users = userService.getUnactivatedUsers();
//		System.out.println(users.size());
		model.addAttribute("users", users);
		return "userActivation";
	}
	
	@RequestMapping(value="/admin/users")
	public String getUsers(HttpServletRequest request, Model model) {
		User user = new User();
		user.setStatus(-1);
		
		List<User> users = userService.getUsers(user);
		model.addAttribute("users", users);
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
