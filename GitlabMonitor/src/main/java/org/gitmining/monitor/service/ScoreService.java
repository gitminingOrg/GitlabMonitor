package org.gitmining.monitor.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.bean.CourseItem;
import org.gitmining.monitor.bean.CourseTeamScore;
import org.gitmining.monitor.bean.ItemStatistics;
import org.gitmining.monitor.bean.ProjectVO;
import org.gitmining.monitor.bean.Score;
import org.gitmining.monitor.bean.SimpleItem;
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
	
	public List<CourseTeamScore> getCourseTeamScores(String courseName){
		List<CourseTeamScore> teamScores = scoreDao.getCourseTeamScore(courseName);
		return teamScores;
	}
	
	public List<SimpleItem> getActiveItemNames(String courseName){
		return scoreDao.getAllActiveItemsByCourseName(courseName);
	}
	
	public boolean addCourseScore(int courseId, String columnName){
		CourseItem courseItem = new CourseItem();
		courseItem.setCourse_id(courseId);
		courseItem.setName(columnName);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		courseItem.setLast_modify(sdf.format(calendar.getTime()));
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
		courseItem.setScores(scores);
		updateItemStatistics(courseItem);
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
			//update course item modify time
			CourseItem courseItem = new CourseItem();
			courseItem.setId(item_id);
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			courseItem.setLast_modify(sdf.format(calendar.getTime()));
			scoreDao.updateItemModifyTime(courseItem);
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
	
	public List<ItemStatistics> getCourseItemStatistics(String courseName){
		//get no modify statistics
		List<ItemStatistics> statistics = scoreDao.selectItemStatisticsByCourseNameNotModify(courseName);
		//get item modifytime
		List<CourseItem> items = scoreDao.selectCourseItemByCourseNameModify(courseName);
		//update
		for (CourseItem courseItem : items) {
			ItemStatistics itemStatistics = updateItemStatistics(courseItem);
			statistics.add(itemStatistics);
		}
		return statistics;
	}
	
	public ItemStatistics updateItemStatistics(int item_id){
		CourseItem courseItem = scoreDao.getCourseItemById(item_id);
		return updateItemStatistics(courseItem);
	}
	
	public ItemStatistics updateItemStatistics(CourseItem courseItem){
		List<Score> scores = courseItem.getScores();
		int size = scores.size();
		Collections.sort(scores, new Comparator<Score>() {
			public int compare(Score o1, Score o2) {
				// TODO Auto-generated method stub
				return o1.getScore() - o2.getScore();
			}
		});
		
		double median = size % 2 == 0 ? (0.5 * scores.get(size/2).getScore() + 0.5 *scores.get(size/2-1).getScore()) : scores.get(size/2).getScore();
		double q1 = scores.get((int)((size+1)*0.25+0.5-1)).getScore();
		double q2 = scores.get((int)((size+1)*0.75+0.5-1)).getScore();
		double range = scores.get(size-1).getScore() - scores.get(0).getScore();
		
		int sum = 0;
		for (Score score : scores) {
			sum+=score.getScore();
		}
		double average = 1.0 * sum / size;
		
		double variance = 0;
		for (Score score : scores) {
			variance+=(score.getScore()-average)*(score.getScore()-average)*1.0/size;
		}
		
		ItemStatistics itemStatistics = new ItemStatistics();
		itemStatistics.setAverage(average);
		itemStatistics.setItem_id(courseItem.getId());
		itemStatistics.setLower_quartile(q1);
		itemStatistics.setMedian(median);
		itemStatistics.setRange(range);
		itemStatistics.setUpper_quartile(q2);
		itemStatistics.setVariance(variance);
		itemStatistics.setItem_name(courseItem.getName());
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		itemStatistics.setTime(sdf.format(date));
		scoreDao.insertItemStatistics(itemStatistics);
		return itemStatistics;
	}
}
