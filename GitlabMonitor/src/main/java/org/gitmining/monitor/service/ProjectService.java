package org.gitmining.monitor.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.DeadLine;
import org.gitmining.monitor.bean.MemberCommit;
import org.gitmining.monitor.bean.ProjectComment;
import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.ProjectEvent;
import org.gitmining.monitor.bean.ProjectVO;
import org.gitmining.monitor.bean.RangeColor;
import org.gitmining.monitor.bean.Student;
import org.gitmining.monitor.bean.StudentCommit;
import org.gitmining.monitor.bean.TeamVO;
import org.gitmining.monitor.dao.ProjectDao;
import org.gitmining.monitor.dao.StudentDao;
import org.gitmining.monitor.util.FilterUtil;
import org.gitmining.monitor.util.FormulaUtil;
import org.gitmining.monitor.util.RandomColorGenerater;
import org.gitmining.monitor.util.ResultMap;
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
	
	public ResultMap projectCommitByCourse(String course,String startDay, String endDay, String order, String method, String formula, String filter){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		if(course != null && course.equalsIgnoreCase("all")){
			course = null;
		}
		List<ProjectCommit> result = projectDao.selectCourseProjectCommitRangeSort(course,startDay, endDay, order, method);
		Map<String, Integer> filterMap = FilterUtil.parseFilter(filter);
		
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
				if(formula != null && formula.trim().length() > 0){
					formula = formula.replaceAll(" ", "");
					projectCommit.setFormula(FormulaUtil.calFormula(formula, dict));
				}
			}
			
		}
		
		ResultMap resultMap = new ResultMap();
		resultMap.setStatus(ResultMap.SUCCESS_STATUS);
		resultMap.add("commits", result);
		return resultMap;
	}
	public Map<String, Object> getProjectCommitItem(int projectId, String startDay, String endDay){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("commit_count", projectDao.selectProjectCommitItemRange("commit_count",projectId,startDay,endDay));
		result.put("add_line", projectDao.selectProjectCommitItemRange("add_line",projectId,startDay,endDay));
		result.put("delete_line", projectDao.selectProjectCommitItemRange("delete_line",projectId,startDay,endDay));
		result.put("java_file", projectDao.selectProjectCommitItemRange("java_file",projectId,startDay,endDay));
		
		List<String> days =  projectDao.selectProjectCommitItemRangeDay("day",projectId,startDay,endDay);
		result.put("day", days);
		result.put("rangeColors", getRangeColors(days, projectId));
		return result;
	}
	public List<Student> getTeamStudent(String team){
		List<Student> students = new ArrayList<Student>();
		if(team == null || team.length() == 0){
			return students;
		}
		students = projectDao.selectTeamStudent(team);
		return students;
	}
	
	public List<ProjectVO> getTeamProject(String team){
		List<ProjectVO> projectVOs = new ArrayList<ProjectVO>();
		if(team == null || team.length() == 0){
			return projectVOs;
		}
		projectVOs = projectDao.selectTeamProject(team);
		return projectVOs;
	}
	
	public TeamVO getTeamInfo(String team){
		if(team == null || team.length() == 0){
			return new TeamVO();
		}
		TeamVO teamVO = projectDao.selectTeamInfo(team);
		if(teamVO == null){
			teamVO = new TeamVO();
		}
		return teamVO;
	}
	
	public List<TeamVO> getLikeTeams(String team){
		if(team == null){
			team="";
		}
		return projectDao.selectLikeTeams(team);
	}
	
	public ResultMap selectTeamStudentCommitRange(int projectId,String startDay, String endDay){
		if(startDay==null){
			startDay="2016-01-01";
		}
		if(endDay==null){
			endDay="2020-01-01";
		}
		ResultMap result = new ResultMap();
		List<StudentCommit> commits = studentDao.selectTeamStudentCommitRange(startDay, endDay, projectId);
		String[] statistics = {"commit_count","add_line","delete_line","java_file","total_commit","total_add","total_delete"};
		result.add("statistics", statistics);
		
		List<MemberCommit> memberCommits = new ArrayList<MemberCommit>();
		for (int i = 0; i < commits.size(); i++) {
			StudentCommit commit = commits.get(i);
			MemberCommit memberCommit = new MemberCommit();
			memberCommit.setName(commits.get(i).getStudent());
			List<Integer> data = new ArrayList<Integer>();
			data.add(commit.getCommit_count());
			data.add(commit.getAdd_line());
			data.add(commit.getDelete_line());
			data.add(commit.getJava_file());
			data.add(commit.getTotal_commit());
			data.add(commit.getTotal_add());
			data.add(commit.getTotal_delete());
			memberCommit.setData(data);
			memberCommits.add(memberCommit);
		}
		result.add("memberCommits", memberCommits);
		result.setStatus(ResultMap.SUCCESS_STATUS);
		return result;
	}
	
	public List<String> getAllCourses(){
		return projectDao.selectAllCourseNames();
	}
	
	public List<RangeColor> getRangeColors(List<String> days, int projectId){
		List<RangeColor> rangeColors = new ArrayList<RangeColor>();
		if(days.size() == 0){
			return rangeColors;
		}
		
		List<DeadLine> deadLines = projectDao.getProjectDeadLine(projectId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDay=null;
		Date endDay = null;
		try {
			System.out.println(days.get(0).toString());
			startDay = sdf.parse(days.get(0));
			endDay = sdf.parse(days.get(days.size()-1));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long maxRange = (endDay.getTime() - startDay.getTime()) /1000 /60 /60 /24;
		long startTime = startDay.getTime();
		for (DeadLine deadLine : deadLines) {
			try{
				Date start = sdf.parse(deadLine.getStart_day());
				Date end = sdf.parse(deadLine.getEnd_day());
				int from = 0;
				int to = 0;
				if(start.before(startDay)){
					from = 0;
				}else if(start.after(endDay)){
					continue;
				}else{
					from =(int) ((start.getTime() - startTime) /1000 /60 /60 /24);
				}
				
				if(end.before(startDay)){
					continue;
				}else if(end.after(endDay)){
					to = (int) maxRange;
				}else{
					to = (int) ((end.getTime() - startTime) /1000 /60 /60 /24);
				}
				
				String color = RandomColorGenerater.getRandomColor();
				RangeColor rangeColor = new RangeColor();
				rangeColor.setColor(color);
				rangeColor.setFrom(from);
				rangeColor.setTo(to);
				
				rangeColors.add(rangeColor);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return rangeColors;
	}
	
	public List<ProjectCommit> selectCourseProjectCommitRangeSort(String course,String
			startDay, String endDay, String order, String method, String formula, String filter){
				
		
		return null;
	}
}
