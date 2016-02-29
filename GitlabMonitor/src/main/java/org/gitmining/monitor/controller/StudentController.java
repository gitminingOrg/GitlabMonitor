package org.gitmining.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.StudentCommit;
import org.gitmining.monitor.bean.StudentEvent;
import org.gitmining.monitor.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class StudentController {
	@Autowired
	private StudentService studentService;
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	@RequestMapping(value="/student/commit/range")
	public Map<String, List> getStudentCommitItemRange(HttpServletRequest request,HttpServletResponse response){
		String student = request.getParameter("student");
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		return studentService.getStudentCommitItem(student, dayStart, dayEnd);
	}
	
	@RequestMapping(value="/student/commit")
	public ModelAndView showStudentCommitPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("studentCommit");
		return result;
	}
	
	@RequestMapping(value="/student/event")
	public ModelAndView showStudentEventPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("studentEvent");
		return result;
	}
	
	@RequestMapping(value="/student/event/range")
	public Map<String, List> getStudentEventItemRange(String student, String dayStart, String dayEnd){
		return studentService.getStudentEventItem(student, dayStart, dayEnd);
	}
	
	@RequestMapping(value="/student/summary")
	public ModelAndView showAllStudentSummary(HttpServletRequest request,HttpServletResponse response){
		String dayStart = request.getParameter("dayStart");
		String dayEnd = request.getParameter("dayEnd");
		String order = request.getParameter("order");
		String method = request.getParameter("method");
		if(dayStart == null){
			dayStart = "20160101";
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			dayEnd = sdf.format(Calendar.getInstance().getTime());
		}
		List<StudentCommit> commits = new ArrayList<StudentCommit>();
		List<StudentEvent> events = new ArrayList<StudentEvent>();
		if(order == null){
			commits = studentService.selectAllStudentCommitRange(dayStart, dayEnd);
			events = studentService.selectAllStudentEventRange(dayStart, dayEnd);
		}else{
			commits = studentService.selectAllStudentCommitRangeSort(dayStart, dayEnd,order,method);
			events = studentService.selectAllStudentEventRangeSort(dayStart, dayEnd,order,method);
		}
		
		ModelAndView result = new ModelAndView("studentSummary");
		result.addObject("commits", commits);
		result.addObject("events", events);
		
		return result;
	}
	
}
