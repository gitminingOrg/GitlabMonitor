package org.gitmining.monitor.controller;

import org.gitmining.monitor.dao.StudentDao;
import org.gitmining.monitor.service.MailService;
import org.gitmining.monitor.service.UpdateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	public StudentDao studentDao;
	@Autowired
	public UpdateDataService updateDataService;
	@Autowired
	public MailService mailService;
	@RequestMapping("/test")
	public String test(){
		//updateDataService.testUpdateData();
		mailService.sendUpdateSuccessMail();
		return "ok";
	}
}
