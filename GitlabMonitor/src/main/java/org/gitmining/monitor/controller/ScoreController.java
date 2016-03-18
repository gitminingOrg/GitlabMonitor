package org.gitmining.monitor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.CourseItem;
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
		view.addObject("itemScores", courseItems);
		view.addObject("course", scoreService.getCourseInfo("2016_nju_se_cseiii"));
		view.addObject("courseNames", scoreService.getAllCourseNames());
		//view.addObject("length", courseItems.get(0).getScores().size());
		return view;
	}
}
