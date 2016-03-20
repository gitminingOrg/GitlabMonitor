package org.gitmining.monitor.controller;

import org.gitmining.monitor.dao.ScoreDao;
import org.gitmining.monitor.dao.StudentDao;
import org.gitmining.monitor.service.MailService;
import org.gitmining.monitor.service.UpdateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {
	@Autowired
	public StudentDao studentDao;
	@Autowired
	public UpdateDataService updateDataService;
	@Autowired
	public MailService mailService;
	@Autowired
	public ScoreDao scoreDao;
	@RequestMapping("/test")
	public Object test(){
		//updateDataService.testUpdateData();
		//mailService.sendUpdateSuccessMail();
		return scoreDao.getCourseProjects("2016_nju_se_cseiii");
	}
	
	@RequestMapping("/try")
	public ModelAndView tryTable(){
		//updateDataService.testUpdateData();
		ModelAndView view = new ModelAndView("tryjsp");
		return view;
	}
}
