package org.gitmining.monitor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.gitmining.monitor.util.URLMapping.*;

@Controller
public class CourseController {
	@Autowired
	CourseService courseService;
	@RequestMapping(value=COURSE_HOMEPAGE)
	public ModelAndView showCourseManage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("courseManage");
		List<Course> courses = courseService.getAllCourses();
		view.addObject("courses", courses);
		return view;
	}
}
