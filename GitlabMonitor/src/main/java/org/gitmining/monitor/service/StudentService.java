package org.gitmining.monitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.StudentCommit;
import org.gitmining.monitor.bean.StudentEvent;
import org.gitmining.monitor.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Autowired
	private StudentDao studentDao;
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	public Map<String, List> getStudentCommitItem(String student, String startDay, String endDay){
		Map<String, List> result = new HashMap<String, List>();
		result.put("commit_count", studentDao.selectStudentCommitItemRange("commit_count",student,startDay,endDay));
		result.put("add_line", studentDao.selectStudentCommitItemRange("add_line",student,startDay,endDay));
		result.put("delete_line", studentDao.selectStudentCommitItemRange("delete_line",student,startDay,endDay));
		result.put("java_file", studentDao.selectStudentCommitItemRange("java_file",student,startDay,endDay));
		result.put("day", studentDao.selectStudentCommitItemRange("day",student,startDay,endDay));
		return result;
	}

	public Map<String, List> getStudentEventItem(String student, String startDay, String endDay){
		Map<String, List> result = new HashMap<String, List>();
		result.put("push", studentDao.selectStudentEventItemRange("push",student,startDay,endDay));
		result.put("issue", studentDao.selectStudentEventItemRange("issue",student,startDay,endDay));
		result.put("comment", studentDao.selectStudentEventItemRange("comment",student,startDay,endDay));
		result.put("create", studentDao.selectStudentEventItemRange("create",student,startDay,endDay));
		result.put("total", studentDao.selectStudentEventItemRange("total",student,startDay,endDay));
		result.put("day", studentDao.selectStudentEventItemRange("day",student,startDay,endDay));
		return result;
	}
	
	public List<StudentCommit> selectAllStudentCommitRange(String startDay, String endDay){
		List<StudentCommit> result = studentDao.selectAllStudentCommitRange(startDay, endDay);
		return result;
	}
	
	public List<StudentCommit> selectAllStudentCommitRangeSort(String startDay, String endDay, String order, String method){
		List<StudentCommit> result = studentDao.selectAllStudentCommitRangeSort(startDay, endDay, order, method);
		return result;
	}
	
	public List<StudentEvent> selectAllStudentEventRange(String startDay, String endDay){
		List<StudentEvent> result = studentDao.selectAllStudentEventRange(startDay, endDay);
		return result;
	}
	
	public List<StudentEvent> selectAllStudentEventRangeSort(String startDay, String endDay, String order, String method){
		List<StudentEvent> result = studentDao.selectAllStudentEventRangeSort(startDay, endDay, order, method);
		return result;
	}
	
}
