package org.gitmining.monitor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Course;
import org.springframework.stereotype.Repository;
@Repository
public class CourseDao extends BaseDaoImpl{
	public List<Course> selectAllCourses(){
		return sqlSession.selectList("course.selectAllCourses");
	}
	
	public boolean insertCourse(Course course){
		return sqlSession.insert("course.insertCourse", course) == 1 ?true:false;
	}
	
	public boolean deleteCourse(int id){
		return sqlSession.delete("course.deleteCourse", id) == 1 ?true:false;
	}
	
	public Course selectCourseById(int id){
		return sqlSession.selectOne("course.selectCourseById", id);
	}
	
	public Course selectCourseByName(String name){
		return sqlSession.selectOne("course.selectCourseByName", name);
	}
	
	public boolean insertCourseChoose(int course_id, int project_id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("course_id", course_id);
		params.put("project_id", project_id);
		return sqlSession.insert("course.insertCourseChoose", params) == 1 ?true:false;
	}
	
	public boolean deleteCourseChoose(int course_id, int project_id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("course_id", course_id);
		params.put("project_id", project_id);
		return sqlSession.delete("course.deleteCourseChoose", params) == 1 ?true:false;
	}
}
