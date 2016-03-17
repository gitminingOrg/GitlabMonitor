package org.gitmining.monitor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Student;
import org.gitmining.monitor.bean.StudentComment;
import org.gitmining.monitor.bean.StudentCommit;
import org.gitmining.monitor.bean.StudentEvent;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends BaseDaoImpl{
	public boolean insertStudentComment(StudentComment studentComment){
		return sqlSession.insert("student.insertStudentComment", studentComment) == 1 ?true:false;
	}
	
	public List<StudentComment> selectStudentComment(String student){
		return sqlSession.selectList("student.selectStudentComment", student);
	}
	
	public Student getStudentInfo(String student){
		return sqlSession.selectOne("student.selectStudentInfo",student);
	}
	
	public Integer selectStudentCommentCount(String student, String token,String time){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("student", student);
		params.put("token", token);
		params.put("time", time);
		return sqlSession.selectOne("student.selectStudentCommentCount",params);
	}
	
	public List<StudentCommit> selectStudentCommitRange(String student, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("student", student);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("student.selectStudentCommitRange", params);
	}
	
	public List<StudentCommit> selectAllStudentCommitRange(String startDay, String endDay){
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("startDay", startDay);
//		params.put("endDay", endDay);
//		return sqlSession.selectList("student.selectAllStudentCommitRange", params);
		return selectAllStudentCommitRangeSort(startDay, endDay, "commit_count", "desc");
	}
	
	public List<StudentCommit> selectTeamStudentCommitRange(String startDay, String endDay, String team){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		params.put("team", team);
		return sqlSession.selectList("student.selectTeamStudentCommitRange", params);
	}
	
	public List<StudentCommit> selectAllStudentCommitRangeSort(String startDay, String endDay, String order, String method){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		params.put("order", order);
		params.put("method", method);
		return sqlSession.selectList("student.selectAllStudentCommitRange", params);
	}
	
	public List<Integer> selectStudentCommitItemRange(String item,String student, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("student", student);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("student.selectStudentCommitItemRange", params);
	}
	
	public List<String> selectStudentCommitItemRangeDay(String item,String student, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("student", student);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("student.selectStudentCommitItemRangeDay", params);
	}
	
	public List<StudentEvent> selectStudentEventRange(String student, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("student", student);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("student.selectStudentEventRange", params);
	}
	
	public List<StudentEvent> selectAllStudentEventRange(String startDay, String endDay){
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("startDay", startDay);
//		params.put("endDay", endDay);
//		return sqlSession.selectList("student.selectAllStudentEventRange", params);
		return selectAllStudentEventRangeSort(startDay, endDay, "total", "desc");
	}
	
	public List<StudentEvent> selectAllStudentEventRangeSort(String startDay, String endDay, String order, String method){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		params.put("order", order);
		params.put("method", method);
		return sqlSession.selectList("student.selectAllStudentEventRange", params);
	}
	
	public List<Integer> selectStudentEventItemRange(String item,String student, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("student", student);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("student.selectStudentEventItemRange", params);
	}
	public List<String> selectStudentEventItemRangeDay(String item,String student, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("student", student);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("student.selectStudentEventItemRangeDay", params);
	}
	
	
}
