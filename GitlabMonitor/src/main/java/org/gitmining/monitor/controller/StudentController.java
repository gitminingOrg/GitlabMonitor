package org.gitmining.monitor.controller;

import java.util.List;
import java.util.Map;

import org.gitmining.monitor.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
	@Autowired
	private StudentService studentService;
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	@RequestMapping(value="/student/commit/range")
	public Map<String, List> getStudentCommitItemRange(String student, String dayStart, String dayEnd){
		return studentService.getStudentCommitItem(student, dayStart, dayEnd);
	}
	
	@RequestMapping(value="/student/event/range")
	public Map<String, List> getStudentEventItemRange(String student, String dayStart, String dayEnd){
		return studentService.getStudentEventItem(student, dayStart, dayEnd);
	}
	
}
