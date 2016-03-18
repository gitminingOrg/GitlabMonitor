package org.gitmining.monitor.dao;

import java.util.List;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.bean.CourseItem;
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
}
