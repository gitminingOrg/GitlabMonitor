package org.gitmining.monitor.dao;

import java.util.List;

import org.gitmining.monitor.bean.Course;
import org.gitmining.monitor.bean.CourseItem;
import org.gitmining.monitor.bean.CourseTeamScore;
import org.gitmining.monitor.bean.ItemStatistics;
import org.gitmining.monitor.bean.ProjectVO;
import org.gitmining.monitor.bean.Score;
import org.gitmining.monitor.bean.SimpleItem;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDao extends BaseDaoImpl {
	public List<CourseItem> getCourseItems(String courseName){
		return sqlSession.selectList("score.selectCourseItemByCourseName", courseName);
	}
	
	public List<CourseTeamScore> getCourseTeamScore(String courseName){
		return sqlSession.selectList("score.selectCourseTeamScoreByCourseName", courseName);
	}
	
	public List<SimpleItem> getAllActiveItemsByCourseName(String courseName){
		return sqlSession.selectList("score.selectAllActiveItemsByCourseName", courseName);
	}
	public Course getCourseInfo(String course){
		return sqlSession.selectOne("score.selectCourseInfo", course);
	}
	public List<String> getCourseNames(){
		return sqlSession.selectList("score.selectAllCourseNames");
	}
	public int insertCourseItem(CourseItem courseItem){
		try{
			sqlSession.insert("score.insertNewCourseItem",courseItem);
			return courseItem.getId();
		}catch(Exception e){
			return -1;
		}
		
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
	
	public int updateItemStatistics(ItemStatistics itemStatistics){
		return sqlSession.update("score.updateItemStatistics", itemStatistics);
	}
	
	public CourseItem getCourseItemById(int itemId){
		return sqlSession.selectOne("score.selectItemByItemId", itemId);
	}
	
	public int insertItemStatistics(ItemStatistics itemStatistics){
		return sqlSession.insert("score.insertItemStatistics", itemStatistics);
	}
	public ItemStatistics selectItemStatisticsById(int id){
		return sqlSession.selectOne("score.selectItemStatisticsById", id);
	}
	public List<ItemStatistics> selectItemStatisticsByCourse(String courseName){
		return sqlSession.selectList("score.selectItemStatisticsByCourseName", courseName);
	}
	public int updateItemModifyTime(CourseItem courseItem){
		return sqlSession.update("score.updateItemLastModify", courseItem);
	}
	public List<ItemStatistics> selectItemStatisticsByCourseNameNotModify(String courseName){
		return sqlSession.selectList("score.selectItemStatisticsByCourseNameNotModify", courseName);
	}
	public List<CourseItem> selectCourseItemByCourseNameModify(String courseName){
		return sqlSession.selectList("score.selectCourseItemByCourseNameModify", courseName);
	}
}
