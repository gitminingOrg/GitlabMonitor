package org.gitmining.monitor.service;

import java.util.List;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.bean.CourseItem;
import org.gitmining.monitor.dao.ScoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
	@Autowired
	private ScoreDao scoreDao;
	public void setScoreDao(ScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}
	
	public List<CourseItem> getCourseScore(String courseName){
		List<CourseItem> courseItems = scoreDao.getCourseItems(courseName);
		return courseItems;
	}
	
	public Course getCourseInfo(String courseName){
		Course course = scoreDao.getCourseInfo(courseName);
		return course;
	}
	
	public List<String> getAllCourseNames(){
		return scoreDao.getCourseNames();
	}
	
}
