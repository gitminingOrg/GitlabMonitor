package org.gitmining.monitor.dao;

import java.util.List;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.bean.CourseItem;
import org.gitmining.monitor.bean.ProjectVO;
import org.gitmining.monitor.bean.Score;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDao extends BaseDaoImpl {
	public List<CourseItem> getCourseItems(String courseName){
		return sqlSession.selectList("score.selectCourseItemByCourseName", courseName);
	}
	public Course getCourseInfo(String course){
		return sqlSession.selectOne("score.selectCourseInfo", course);
	}
	public List<String> getCourseNames(){
		return sqlSession.selectList("score.selectAllCourseNames");
	}
	public int insertCourseItem(CourseItem courseItem){
		sqlSession.insert("score.insertNewCourseItem",courseItem);
		return courseItem.getId();
	}
	
	public List<Integer> getCourseProjectID(int courseId){
		return sqlSession.selectList("selectCourseProject",courseId);
	}
	
	public int addItemScoreBatch(List<Score> scores){
		return sqlSession.insert("score.addItemScoreBatch", scores);
	}
	
	public int updateProjectItemScore(Score score){
		return sqlSession.update("score.updateProjectItemScore", score);
	}
	public boolean disableCourseItem(int id){
		System.out.println(sqlSession.update("score.disableCourseItem", id));
		return true;
	}
	public List<ProjectVO> getCourseProjects(String name){
		return sqlSession.selectList("score.selectCourseProjects",name);
	}
}
