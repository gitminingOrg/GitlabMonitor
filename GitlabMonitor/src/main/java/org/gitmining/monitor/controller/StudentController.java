package org.gitmining.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.StudentComment;
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
	@RequestMapping(value="/student/comment")
	public List<StudentComment> getStudentComment(HttpServletRequest request,HttpServletResponse response){
		String student = request.getParameter("student");
		return studentService.getStudentComments(student);
	}
	
	@RequestMapping(value="/student/comment/submit")
	public Map<String,Object> getStudentCommentSubmit(HttpServletRequest request,HttpServletResponse response){
		String student = request.getParameter("student");
		String token = request.getParameter("token");
		String words = request.getParameter("sen");
		return studentService.insertStudentComments(student, token, words);
	}
	
	@RequestMapping(value="/student/commit")
	public ModelAndView showStudentCommitPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("studentCommit");
		String student = request.getParameter("student");
		List<StudentComment> comments = new ArrayList<StudentComment>();
		if(student != null){
			result.addObject("student", student);
			comments = studentService.getStudentComments(student);
		}
		result.addObject("comments", comments);
		return result;
	}
	
	@RequestMapping(value="/student/event")
	public ModelAndView showStudentEventPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView result = new ModelAndView("studentEvent");
		String student = request.getParameter("student");
		if(student != null){
			result.addObject("student", student);
		}
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
		String commitOrder = request.getParameter("commitOrder");
		String eventOrder = request.getParameter("eventOrder");
		
		String method = request.getParameter("method");
		
		if(dayStart == null){
			dayStart = "2016-01-01";
		}
		if(dayEnd == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dayEnd = sdf.format(Calendar.getInstance().getTime());
		}
		List<StudentCommit> commits = new ArrayList<StudentCommit>();
		List<StudentEvent> events = new ArrayList<StudentEvent>();
		if(commitOrder == null){
			commits = studentService.selectAllStudentCommitRange(dayStart, dayEnd);
			events = studentService.selectAllStudentEventRange(dayStart, dayEnd);
		}else{
			commits = studentService.selectAllStudentCommitRangeSort(dayStart, dayEnd,commitOrder,method);
			events = studentService.selectAllStudentEventRangeSort(dayStart, dayEnd,eventOrder,method);
		}
		
		ModelAndView result = new ModelAndView("studentSummary");
		result.addObject("commits", commits);
		result.addObject("events", events);
		result.addObject("dayStart", dayStart);
		result.addObject("dayEnd", dayEnd);
		return result;
	}
	
}
