package org.gitmining.monitor.service;

import java.util.List;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
	@Autowired
	private CourseDao courseDao;
	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public boolean addCourse(String name, int schoolId,String starttime, String endtime, String teachers){
		Course course = new Course();
		course.setName(name);
		course.setSchool_id(schoolId);
		course.setStarttime(starttime);
		course.setEndtime(endtime);
		course.setTeachers(teachers);
		
		return courseDao.insertCourse(course);
	}
	
	public boolean deleteCourse(int courseId){
		return courseDao.deleteCourse(courseId);
	}
	
	public List<Course> getAllCourses(){
		return courseDao.selectAllCourses();
	}
	
	public boolean courseExist(String name){
		Course course = courseDao.selectCourseByName(name);
		return course != null;
	}
	
	public boolean chooseCourse(int projectId, int courseId){
		return courseDao.insertCourseChoose(courseId, projectId);
	}
	
	public boolean unboundCourse(int projectId, int courseId){
		return courseDao.deleteCourseChoose(courseId, projectId);
	}
	
}
