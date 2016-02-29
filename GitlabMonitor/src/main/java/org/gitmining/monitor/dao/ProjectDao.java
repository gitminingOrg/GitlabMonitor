package org.gitmining.monitor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.ProjectEvent;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao extends BaseDaoImpl{
	public List<ProjectCommit> selectProjectCommitRange(String team, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("team", team);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectCommitRange", params);
	}
	
	public List<ProjectCommit> selectAllProjectCommitRange(String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectAllProjectCommitRange", params);
	}
	
	public List<ProjectCommit> selectAllProjectCommitRangeSort(String startDay, String endDay, String order, String method){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		params.put("order", order);
		params.put("method", method);
		return sqlSession.selectList("project.selectAllProjectCommitRange", params);
	}
	
	public List<Integer> selectProjectCommitItemRange(String item,String team, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("team", team);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectCommitItemRange", params);
	}
	
	public List<ProjectEvent> selectProjectEventRange(String team, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("team", team);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectEventRange", params);
	}
	
	public List<ProjectEvent> selectAllProjectEventRange(String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectAllProjectEventRange", params);
	}
	
	public List<ProjectEvent> selectAllProjectEventRangeSort(String startDay, String endDay, String order, String method){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		params.put("order", order);
		params.put("method", method);
		return sqlSession.selectList("project.selectAllProjectEventRange", params);
	}
	
	public List<Integer> selectProjectEventItemRange(String item,String team, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("team", team);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectEventItemRange", params);
	}
}
