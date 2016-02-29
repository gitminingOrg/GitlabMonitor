package org.gitmining.monitor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.ProjectEvent;
import org.gitmining.monitor.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao projectDao;
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
	public Map<String, List> getProjectCommitItem(String project, String startDay, String endDay){
		Map<String, List> result = new HashMap<String, List>();
		result.put("commit_count", projectDao.selectProjectCommitItemRange("commit_count",project,startDay,endDay));
		result.put("add_line", projectDao.selectProjectCommitItemRange("add_line",project,startDay,endDay));
		result.put("delete_line", projectDao.selectProjectCommitItemRange("delete_line",project,startDay,endDay));
		result.put("java_file", projectDao.selectProjectCommitItemRange("java_file",project,startDay,endDay));
		result.put("day", projectDao.selectProjectCommitItemRange("day",project,startDay,endDay));
		return result;
	}
	
	public Map<String, List> getProjectEventItem(String project, String startDay, String endDay){
		Map<String, List> result = new HashMap<String, List>();
		result.put("push", projectDao.selectProjectEventItemRange("push",project,startDay,endDay));
		result.put("issue", projectDao.selectProjectEventItemRange("issue",project,startDay,endDay));
		result.put("comment", projectDao.selectProjectEventItemRange("comment",project,startDay,endDay));
		result.put("create", projectDao.selectProjectEventItemRange("create",project,startDay,endDay));
		result.put("total", projectDao.selectProjectEventItemRange("total",project,startDay,endDay));
		result.put("day", projectDao.selectProjectEventItemRange("day",project,startDay,endDay));
		return result;
	}
	
	public List<ProjectCommit> selectAllProjectCommitRange(String startDay, String endDay){
		List<ProjectCommit> result = projectDao.selectAllProjectCommitRange(startDay, endDay);
		return result;
	}
	
	public List<ProjectCommit> selectAllProjectCommitRangeSort(String startDay, String endDay, String order, String method){
		List<ProjectCommit> result = projectDao.selectAllProjectCommitRangeSort(startDay, endDay, order, method);
		return result;
	}
	
	public List<ProjectEvent> selectAllProjectEventRange(String startDay, String endDay){
		List<ProjectEvent> result = projectDao.selectAllProjectEventRange(startDay, endDay);
		return result;
	}
	
	public List<ProjectEvent> selectAllProjectEventRangeSort(String startDay, String endDay, String order, String method){
		List<ProjectEvent> result = projectDao.selectAllProjectEventRangeSort(startDay, endDay, order, method);
		return result;
	}
	
}
