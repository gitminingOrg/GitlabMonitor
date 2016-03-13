package org.gitmining.monitor.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.StudentComment;
import org.gitmining.monitor.bean.StudentCommit;
import org.gitmining.monitor.bean.StudentEvent;
import org.gitmining.monitor.dao.StudentDao;
import org.gitmining.monitor.util.FormulaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Autowired
	private StudentDao studentDao;
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	public List<StudentComment> getStudentComments(String student){
		return studentDao.selectStudentComment(student);
	}
	
	public Map<String, Object> insertStudentComments(String student, String token, String words){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpClientDeal deal = new HttpClientDeal();
		if(!token.equals("adminliujia") && !deal.getHttpTokenStatus(token)){
			resultMap.put("status", 0);
			resultMap.put("reason", "token invalid");
			
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			String now = sdf.format(calendar.getTime());
			calendar.add(Calendar.MINUTE, -5);
			String minuteAgo = sdf.format(calendar.getTime());
			if(words.length() > 150 || studentDao.selectStudentCommentCount(student, token, minuteAgo) >= 10){
				resultMap.put("status", 0);
				resultMap.put("reason", "why u have so many words to say?");
			}else{
				StudentComment studentComment = new StudentComment();
				studentComment.setStudent(student);
				studentComment.setToken(token);
				studentComment.setWords(words);
				studentComment.setTime(now);
				boolean insert = studentDao.insertStudentComment(studentComment);
				if(insert == false){
					resultMap.put("status", 1);
					resultMap.put("reason", "insert succeed");
				}else{
					resultMap.put("status", 1);
					resultMap.put("reason", "insert succeed");
				}
			}
			
		}
		return resultMap;
	}
	
	public Map<String, List> getStudentCommitItem(String student, String startDay, String endDay){
		Map<String, List> result = new HashMap<String, List>();
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		result.put("commit_count", studentDao.selectStudentCommitItemRange("commit_count",student,startDay,endDay));
		result.put("add_line", studentDao.selectStudentCommitItemRange("add_line",student,startDay,endDay));
		result.put("delete_line", studentDao.selectStudentCommitItemRange("delete_line",student,startDay,endDay));
		result.put("java_file", studentDao.selectStudentCommitItemRange("java_file",student,startDay,endDay));
		result.put("day", studentDao.selectStudentCommitItemRangeDay("day",student,startDay,endDay));
		return result;
	}

	public Map<String, List> getStudentEventItem(String student, String startDay, String endDay){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		Map<String, List> result = new HashMap<String, List>();
		result.put("push", studentDao.selectStudentEventItemRange("push",student,startDay,endDay));
		result.put("issue", studentDao.selectStudentEventItemRange("issue",student,startDay,endDay));
		result.put("comment", studentDao.selectStudentEventItemRange("comment",student,startDay,endDay));
		result.put("create", studentDao.selectStudentEventItemRange("create",student,startDay,endDay));
		result.put("total", studentDao.selectStudentEventItemRange("total",student,startDay,endDay));
		result.put("day", studentDao.selectStudentEventItemRangeDay("day",student,startDay,endDay));
		return result;
	}
	
	public List<StudentCommit> selectAllStudentCommitRange(String startDay, String endDay,String formula){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		List<StudentCommit> result = studentDao.selectAllStudentCommitRange(startDay, endDay);
		if(formula != null && formula.trim().length() > 0){
			formula = formula.replaceAll(" ", "");
			for (StudentCommit studentCommit : result) {
				Map<String, Double> dict = new HashMap<String, Double>();
				dict.put("add_line", (double) studentCommit.getAdd_line());
				dict.put("delete_line", (double) studentCommit.getDelete_line());
				dict.put("commit_count", (double) studentCommit.getCommit_count());
				dict.put("java_file", (double) studentCommit.getJava_file());
				dict.put("total_add", (double) studentCommit.getTotal_add());
				dict.put("total_delete", (double) studentCommit.getTotal_delete());
				dict.put("total_commit", (double) studentCommit.getTotal_commit());
				
				studentCommit.setFormula(FormulaUtil.calFormula(formula, dict));
			}
		}
		return result;
	}
	
	public List<StudentCommit> selectAllStudentCommitRangeSort(String startDay, String endDay, String order, String method, String formula){
		formula = formula.replaceAll(" ", "");
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		List<StudentCommit> result = studentDao.selectAllStudentCommitRangeSort(startDay, endDay, order, method);
		if(formula != null && formula.trim().length() > 0){
			for (StudentCommit studentCommit : result) {
				Map<String, Double> dict = new HashMap<String, Double>();
				dict.put("add_line", (double) studentCommit.getAdd_line());
				dict.put("delete_line", (double) studentCommit.getDelete_line());
				dict.put("commit_count", (double) studentCommit.getCommit_count());
				dict.put("java_file", (double) studentCommit.getJava_file());
				dict.put("total_add", (double) studentCommit.getTotal_add());
				dict.put("total_delete", (double) studentCommit.getTotal_delete());
				dict.put("total_commit", (double) studentCommit.getTotal_commit());
				
				studentCommit.setFormula(FormulaUtil.calFormula(formula, dict));
			}
		}
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
