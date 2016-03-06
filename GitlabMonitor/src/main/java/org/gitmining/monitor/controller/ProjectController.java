package org.gitmining.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.ProjectEvent;
import org.gitmining.monitor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="/project/commit/range")
	public Map<String, List> getProjectCommitItemRange(HttpServletRequest request,HttpServletResponse response){
		String team = request.getParameter("team");
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		return projectService.getProjectCommitItem(team, dayStart, dayEnd);
	}
	
	@RequestMapping(value="/project/commit")
	public ModelAndView showProjectCommitPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("projectCommit");
		String team = request.getParameter("team");
		if(team != null){
			result.addObject("team", team);
		}
		return result;
	}
	
	@RequestMapping(value="/project/event")
	public ModelAndView showProjectEventPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("projectEvent");
		String team = request.getParameter("team");
		if(team != null){
			result.addObject("team", team);
		}
		return result;
	}
	@RequestMapping(value="/project/team")
	public ModelAndView showProjectTeamPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("projectMember");
		return result;
	}
	@RequestMapping(value="/project/event/range")
	public Map<String, List> getProjectEventItemRange(HttpServletRequest request,HttpServletResponse response){
		String team = request.getParameter("team");
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		return projectService.getProjectEventItem(team, dayStart, dayEnd);
	}
	
	@RequestMapping(value="/project/summary")
	public ModelAndView showAllProjectSummary(HttpServletRequest request,HttpServletResponse response){
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		String commitOrder = request.getParameter("commitOrder");
		String eventOrder = request.getParameter("eventOrder");
		
		String method = request.getParameter("method");
		if(dayStart == null){
			dayStart = "20160101";
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			dayEnd = sdf.format(Calendar.getInstance().getTime());
		}
		List<ProjectCommit> commits = new ArrayList<ProjectCommit>();
		List<ProjectEvent> events = new ArrayList<ProjectEvent>();
		if(commitOrder == null){
			commits = projectService.selectAllProjectCommitRange(dayStart, dayEnd);
			events = projectService.selectAllProjectEventRange(dayStart, dayEnd);
		}else{
			commits = projectService.selectAllProjectCommitRangeSort(dayStart, dayEnd,commitOrder,method);
			events = projectService.selectAllProjectEventRangeSort(dayStart, dayEnd,eventOrder,method);
		}
		
		ModelAndView result = new ModelAndView("projectSummary");
		result.addObject("commits", commits);
		result.addObject("events", events);
		result.addObject("dayStart", dayStart);
		result.addObject("dayEnd", dayEnd);
		return result;
	}
	@RequestMapping("/project/teammember")
	public Map<String, Object> selectTeamStudentCommitRange(HttpServletRequest request, HttpServletResponse response){
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		if(dayStart == null){
			dayStart = "20160101";
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			dayEnd = sdf.format(Calendar.getInstance().getTime());
		}
		String team = request.getParameter("team");
		return projectService.selectTeamStudentCommitRange(team, dayStart, dayEnd);
	}
}
