package org.gitmining.monitor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.bean.CourseItem;
import org.gitmining.monitor.bean.ProjectVO;
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
		if(item_id == -1){
			return false;
		}
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
	
	public List<ProjectVO> getAllCourseGroupNames(String courseName){
		return scoreDao.getCourseProjects(courseName);
	}
	
	public Map<String, Object> updateScore(int project_id, int item_id, int num){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Score score = new Score();
			score.setItem_id(item_id);
			score.setProject_id(project_id);
			score.setScore(num);
			scoreDao.updateProjectItemScore(score);	
			result.put("status", "ok");
		}catch(Exception e){
			result.put("status", "wrong");
			result.put("info", "server error");
		}
		return result;
	}
	
	public Map<String, Object> deleteCourseItem(int item_id){
		Map<String, Object> result = new HashMap<String, Object>();
		boolean status = scoreDao.disableCourseItem(item_id);
		if(status){
			result.put("status", "ok");
		}else{
			result.put("status", "fail");
			result.put("info", "delete failure");
		}
		return result;
	}
}
