package org.gitmining.monitor.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.ProjectComment;
import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.ProjectEvent;
import org.gitmining.monitor.bean.StudentComment;
import org.gitmining.monitor.bean.StudentCommit;
import org.gitmining.monitor.dao.ProjectDao;
import org.gitmining.monitor.dao.StudentDao;
import org.gitmining.monitor.util.FilterUtil;
import org.gitmining.monitor.util.FormulaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao projectDao;
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
	@Autowired
	private StudentDao studentDao;
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public List<ProjectComment> getProjectComments(String team){
		return projectDao.selectProjectComment(team);
	}
	
	public Map<String, Object> insertProjectComments(String team, String token, String words){
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
			System.out.println(minuteAgo);
			if(words.length() > 150 || projectDao.selectProjectCommentCount(team, token, minuteAgo) >= 10){
				resultMap.put("status", 0);
				resultMap.put("reason", "why u have so many words to say?");
			}else{
				ProjectComment studentComment = new ProjectComment();
				studentComment.setTeam(team);
				studentComment.setToken(token);
				studentComment.setWords(words);
				studentComment.setTime(now);
				boolean insert = projectDao.insertProjectComment(studentComment);
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
	public Map<String, Object> getProjectCommitItem(String project, String startDay, String endDay){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commit_count", projectDao.selectProjectCommitItemRange("commit_count",project,startDay,endDay));
		result.put("add_line", projectDao.selectProjectCommitItemRange("add_line",project,startDay,endDay));
		result.put("delete_line", projectDao.selectProjectCommitItemRange("delete_line",project,startDay,endDay));
		result.put("java_file", projectDao.selectProjectCommitItemRange("java_file",project,startDay,endDay));
		result.put("day", projectDao.selectProjectCommitItemRangeDay("day",project,startDay,endDay));
		return result;
	}
	
	public Map<String, List> getProjectEventItem(String project, String startDay, String endDay){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		Map<String, List> result = new HashMap<String, List>();
		result.put("push", projectDao.selectProjectEventItemRange("push",project,startDay,endDay));
		result.put("issue", projectDao.selectProjectEventItemRange("issue",project,startDay,endDay));
		result.put("comment", projectDao.selectProjectEventItemRange("comment",project,startDay,endDay));
		result.put("create", projectDao.selectProjectEventItemRange("create",project,startDay,endDay));
		result.put("total", projectDao.selectProjectEventItemRange("total",project,startDay,endDay));
		result.put("day", projectDao.selectProjectEventItemRangeDay("day",project,startDay,endDay));
		return result;
	}
	
	public List<ProjectCommit> selectAllProjectCommitRange(String startDay, String endDay, String formula,String filter){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		List<ProjectCommit> result = projectDao.selectAllProjectCommitRange(startDay, endDay);
		Map<String, Integer> filterMap = FilterUtil.parseFilter(filter);
		if(formula != null && formula.trim().length() > 0){
			formula = formula.replaceAll(" ", "");
			for (int i=0; i<result.size(); i++) {
				ProjectCommit projectCommit = result.get(i);
				if(filterMap != null && !projectCommit.validate(filterMap)){
					result.remove(projectCommit);
					i--;
				}else{
					Map<String, Double> dict = new HashMap<String, Double>();
					dict.put("add_line", (double) projectCommit.getAdd_line());
					dict.put("delete_line", (double) projectCommit.getDelete_line());
					dict.put("commit_count", (double) projectCommit.getCommit_count());
					dict.put("java_file", (double) projectCommit.getJava_file());
					dict.put("total_add", (double) projectCommit.getTotal_add());
					dict.put("total_delete", (double) projectCommit.getTotal_delete());
					dict.put("total_commit", (double) projectCommit.getTotal_commit());
					
					projectCommit.setFormula(FormulaUtil.calFormula(formula, dict));
				}
				
			}
		}
		return result;
	}
	
	public List<ProjectCommit> selectAllProjectCommitRangeSort(String startDay, String endDay, String order, String method, String formula, String filter){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		List<ProjectCommit> result = projectDao.selectAllProjectCommitRangeSort(startDay, endDay, order, method);
		Map<String, Integer> filterMap = FilterUtil.parseFilter(filter);
		if(formula != null && formula.trim().length() > 0){
			formula = formula.replaceAll(" ", "");
			for (int i=0; i<result.size(); i++) {
				ProjectCommit projectCommit = result.get(i);
				if(filterMap != null && !projectCommit.validate(filterMap)){
					result.remove(projectCommit);
					i--;
				}else{
					Map<String, Double> dict = new HashMap<String, Double>();
					dict.put("add_line", (double) projectCommit.getAdd_line());
					dict.put("delete_line", (double) projectCommit.getDelete_line());
					dict.put("commit_count", (double) projectCommit.getCommit_count());
					dict.put("java_file", (double) projectCommit.getJava_file());
					dict.put("total_add", (double) projectCommit.getTotal_add());
					dict.put("total_delete", (double) projectCommit.getTotal_delete());
					dict.put("total_commit", (double) projectCommit.getTotal_commit());
					
					projectCommit.setFormula(FormulaUtil.calFormula(formula, dict));
				}
				
			}
		}
		return result;
	}
	
	public List<ProjectEvent> selectAllProjectEventRange(String startDay, String endDay){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		List<ProjectEvent> result = projectDao.selectAllProjectEventRange(startDay, endDay);
		return result;
	}
	
	public List<ProjectEvent> selectAllProjectEventRangeSort(String startDay, String endDay, String order, String method){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		List<ProjectEvent> result = projectDao.selectAllProjectEventRangeSort(startDay, endDay, order, method);
		return result;
	}
	
	public Map<String, Object> selectTeamStudentCommitRange(String team,String startDay, String endDay){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		Map<String, Object> result = new HashMap<String, Object>();
		List<StudentCommit> commits = studentDao.selectTeamStudentCommitRange(startDay, endDay, team);
		String[] statistics = {"commit_count","add_line","delete_line","java_file","total_commit","total_add","total_delete"};
		result.put("statistics", statistics);
		for (int i = 0; i < commits.size(); i++) {
			StudentCommit commit = commits.get(i);
			result.put("stu"+i, commits.get(i).getStudent());
			List<Integer> data = new ArrayList<Integer>();
			data.add(commit.getCommit_count());
			data.add(commit.getAdd_line());
			data.add(commit.getDelete_line());
			data.add(commit.getJava_file());
			data.add(commit.getTotal_commit());
			data.add(commit.getTotal_add());
			data.add(commit.getTotal_delete());
			result.put("data"+i, data);
		}
		return result;
	}
	
}
