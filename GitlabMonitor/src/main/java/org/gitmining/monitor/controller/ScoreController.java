package org.gitmining.monitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.CourseItem;
import org.gitmining.monitor.bean.ProjectVO;
import org.gitmining.monitor.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ScoreController {
	@Autowired
	private ScoreService scoreService;
	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	@RequestMapping("/project/score")
	public ModelAndView showProjectScore(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView("projectScore");
		List<CourseItem> courseItems = scoreService.getCourseScore("2016_nju_se_cseiii");
		List<ProjectVO> projects = scoreService.getAllCourseGroupNames("2016_nju_se_cseiii");
		view.addObject("itemScores", courseItems);
		view.addObject("projects", projects);
		view.addObject("course", scoreService.getCourseInfo("2016_nju_se_cseiii"));
		view.addObject("courseNames", scoreService.getAllCourseNames());
		//view.addObject("length", courseItems.get(0).getScores().size());
		return view;
	}
	
	@RequestMapping("/project/score/add")
	public ModelAndView addProjectScore(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView("projectScore");
		String columnName = request.getParameter("column");
		int courseId = Integer.parseInt(request.getParameter("course_id"));
		String courseName = request.getParameter("course_name");
		scoreService.addCourseScore(courseId, columnName);
		List<ProjectVO> projects = scoreService.getAllCourseGroupNames("2016_nju_se_cseiii");
		List<CourseItem> courseItems = scoreService.getCourseScore("2016_nju_se_cseiii");
		view.addObject("itemScores", courseItems);
		view.addObject("projects", projects);
		view.addObject("course", scoreService.getCourseInfo("2016_nju_se_cseiii"));
		view.addObject("courseNames", scoreService.getAllCourseNames());
		//view.addObject("length", courseItems.get(0).getScores().size());
		return view;
	}
	
	@RequestMapping("/project/score/change")
	public Map<String, Object> changeProjectScore(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			int item_id = Integer.parseInt(request.getParameter("item_id"));
			int project_id = Integer.parseInt(request.getParameter("project_id"));
			int score = Integer.parseInt(request.getParameter("score"));
			result = scoreService.updateScore(project_id, item_id, score);
		}catch(NumberFormatException e){
			result.put("status", "wrong");
			result.put("info", "data not a int");
		}
		return result;
	}
	
	@RequestMapping("/project/score/delete")
	public ModelAndView deleteProjectScore(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView("projectScore");
		int itemId = Integer.parseInt(request.getParameter("delete_item"));
		scoreService.deleteCourseItem(itemId);
		
		List<CourseItem> courseItems = scoreService.getCourseScore("2016_nju_se_cseiii");
		List<ProjectVO> projects = scoreService.getAllCourseGroupNames("2016_nju_se_cseiii");
		view.addObject("projects", projects);
		view.addObject("itemScores", courseItems);
		view.addObject("course", scoreService.getCourseInfo("2016_nju_se_cseiii"));
		view.addObject("courseNames", scoreService.getAllCourseNames());
		//view.addObject("length", courseItems.get(0).getScores().size());
		return view;
	}
}
