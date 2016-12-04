package org.gitmining.monitor.controller;

import static org.gitmining.monitor.util.URLMapping.COURSE_ADD;
import static org.gitmining.monitor.util.URLMapping.COURSE_HOMEPAGE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.service.CourseService;
import org.gitmining.monitor.util.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CourseController {
	@Autowired
	CourseService courseService;
	
	/**
	 * mapping to the course page,show all the courses included in our system
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=COURSE_HOMEPAGE)
	public ModelAndView showCourseManage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("courseManage");
		List<Course> courses = courseService.getAllCourses();
		view.addObject("courses", courses);
		return view;
	}
	
	/**
	 * add a new course
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=COURSE_ADD)
	public ResultMap addCourse(HttpServletRequest request,HttpServletResponse response){
		ResultMap resultMap = new ResultMap();
		String name = request.getParameter("name");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String teachers = request.getParameter("teachers");
		
		boolean success = courseService.addCourse(name, 10085, starttime, endtime, teachers);
		List<Course> courses = courseService.getAllCourses();
		resultMap.add("courses", courses);
		if(success){
			resultMap.setStatus(ResultMap.SUCCESS_STATUS);
			resultMap.setInfo("Adding new course succeed !");
		}else{
			resultMap.setStatus(ResultMap.FAIL_STATUS);
			resultMap.setInfo("Adding new course failed !");
		}
		return resultMap;
	}
}
