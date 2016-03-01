package org.gitmining.monitor.controller;

import org.gitmining.monitor.dao.StudentDao;
import org.gitmining.monitor.service.UpdateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	public StudentDao studentDao;
	@Autowired
	public UpdateDataService updateDataService;
	@RequestMapping("/test")
	public String test(){
		updateDataService.testUpdateData();
		return "ok";
	}
}
