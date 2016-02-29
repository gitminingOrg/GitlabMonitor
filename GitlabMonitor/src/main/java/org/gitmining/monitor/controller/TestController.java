package org.gitmining.monitor.controller;

import org.gitmining.monitor.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	public StudentDao studentDao;
	@RequestMapping("/test")
	public String test(){
		studentDao.selectStudentCommitRange("aa", "2016-01-01", "2016-08-08");
		return "ok";
	}
}
