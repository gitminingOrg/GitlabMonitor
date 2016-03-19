package org.gitmining.monitor.service;

import java.util.ArrayList;
import java.util.List;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.bean.CourseItem;
import org.gitmining.monitor.bean.Score;
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
	
	public boolean addCourseScore(int courseId, String columnName){
		CourseItem courseItem = new CourseItem();
		courseItem.setCourse_id(courseId);
		courseItem.setName(columnName);
		int item_id = scoreDao.insertCourseItem(courseItem);
		List<Integer> projectIds = scoreDao.getCourseProjectID(courseId);
		List<Score> scores  = new ArrayList<Score>();
		for (int projectId : projectIds) {
			Score score = new Score();
			score.setItem_id(item_id);
			score.setProject_id(projectId);
			score.setScore(0);
			scores.add(score);
		}
		scoreDao.addItemScoreBatch(scores);
		return true;
	}
	
	public Course getCourseInfo(String courseName){
		Course course = scoreDao.getCourseInfo(courseName);
		return course;
	}
	
	public List<String> getAllCourseNames(){
		return scoreDao.getCourseNames();
	}
	
}
