package org.gitmining.monitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.crawlerdao.StudentCrawlerDao;
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
		updateDataService.testUpdateData();
		//mailService.sendUpdateSuccessMail();
		return "ok";
	}
	
	@RequestMapping("/relation")
	public String getRelation(){
		String day = "2016-03-16";
		int projectid = 16;
		StudentCrawlerDao studentCrawlerDao = new StudentCrawlerDao();
		List<String> members = studentCrawlerDao.getTeamMemberByProjectID(projectid);
		Map<String, Integer> memberMap = new HashMap<String, Integer>();
		
		String result = "";
		String nodes = "nodes: [";
		String links = "links: [";
		String width = "widths: [";
		
		for(int i = 0 ; i < members.size(); i ++){
			memberMap.put(members.get(i), i);
			nodes = nodes + "{size: " + studentCrawlerDao.getFileNumberByDayAndProject(projectid, day, members.get(i)) + ", name:\"" + members.get(i) + "\"},"; 
		}
		nodes = nodes.substring(0, nodes.length() - 1) + "]";
		
		for(int i = 0 ; i < members.size() - 1; i ++){
			for(int j = i + 1 ; j < members.size() ; j ++){
				links = links + "{source: " + memberMap.get(members.get(i)) + ",target: " + memberMap.get(members.get(j)) + "},";
				width = width + studentCrawlerDao.getRelationByFile(members.get(i), members.get(j), projectid) + ",";
			}
		}
		links = links.substring(0, links.length() - 1) + "]";
		width = width.substring(0, width.length() - 1) + "]";
		
		result = "{" + nodes + "," + links + "," + width + "}";
		
		return result;
	}
}
