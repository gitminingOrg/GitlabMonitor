package org.gitmining.monitor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.StudentCommit;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends BaseDaoImpl{
	public List<StudentCommit> selectStudentCommitRange(String student, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("student", student);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		System.out.println(sqlSession);
		return sqlSession.selectList("student.selectStudentCommitRange", params);
	}
}
