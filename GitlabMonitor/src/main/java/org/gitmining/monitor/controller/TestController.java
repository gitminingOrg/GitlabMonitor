package org.gitmining.monitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.DayHour;
import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;
import org.gitmining.monitor.crawlerdao.StudentCrawlerDao;
import org.gitmining.monitor.dao.ScoreDao;
import org.gitmining.monitor.dao.StudentDao;
import org.gitmining.monitor.service.MailService;
import org.gitmining.monitor.service.ScoreService;
import org.gitmining.monitor.service.UpdateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {
	@Autowired
	public StudentDao studentDao;
	@Autowired
	ScoreService scoreService;
	@Autowired
	public UpdateDataService updateDataService;
	@Autowired
	public MailService mailService;
	@Qualifier(value="executor")
	@Autowired
	public ThreadPoolTaskExecutor executor;
	

	@Autowired
	public ScoreDao scoreDao;
	@RequestMapping("/test")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response){
		//updateDataService.testUpdateData();
		//mailService.sendUpdateSuccessMail();
		ModelAndView view = new ModelAndView("clock");
		return view;
	}
	
	@RequestMapping("/try")
	public ModelAndView tryTable() throws Exception{
		//updateDataService.testUpdateData();
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
		List<Integer> list = new CopyOnWriteArrayList<Integer>();
		list.add(1);
		list.add(11);
		list.add(111);
		list.add(1111);
		list.add(11111);
		for (final Integer integer : list) {
			completionService.submit(new Callable<Integer>() {	
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return integer;
				}
			});
		}
		
		for (int i = 0; i < list.size(); i++) {
			Future<Integer> f = completionService.take();
			int result = f.get();
			System.out.println(result);
		}
		
		
		ModelAndView view = new ModelAndView("tryTable");
		return view;
	}
	
	/**
	 * relationship chart data, zhaoyufeng can explain it
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/relation")
	public String getRelation(HttpServletRequest request,HttpServletResponse response){
		String day = request.getParameter("endDay");
		int projectid = Integer.parseInt(request.getParameter("projectID"));
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
				if(studentCrawlerDao.getRelationByFile(members.get(i), members.get(j), projectid , day) != 0){
					links = links + "{source: " + memberMap.get(members.get(i)) + ",target: " + memberMap.get(members.get(j)) + "},";
					width = width + studentCrawlerDao.getRelationByFile(members.get(i), members.get(j), projectid , day) + ",";
				}
			}
		}
		links = links.substring(0, links.length() - 1) + "]";
		width = width.substring(0, width.length() - 1) + "]";
		
		result = "{" + nodes + "," + links + "," + width + "}";
		
		System.out.println(result);
		return result;
	}
	
	/**
	 * day hour -> working time heat map, zhaoyufeng can explain it
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dayhour")
	public String getDayHour(HttpServletRequest request,HttpServletResponse response){
		int projectid = Integer.parseInt(request.getParameter("projectID"));
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<DayHour> dayHours = projectCrawlerDao.getDayHourByProjectID(projectid);
		
		String result = "";
		String day = "day: [";
		String hour = "hour: [";
		String value = "value: [";
		
		for(int i = 0 ; i < dayHours.size(); i ++){
			day = day + dayHours.get(i).getDay() + ","; 
			hour = hour + dayHours.get(i).getHour() + ",";
			value = value + dayHours.get(i).getValue() + ","; 
		}
		
		day = day.substring(0, day.length() - 1) + "]";
		hour = hour.substring(0, hour.length() - 1) + "]";
		value = value.substring(0, value.length() - 1) + "]";
		
		result = "{" + day + "," + hour + "," + value + "}";
		
		System.out.println(result);
		return result;
	}
}
