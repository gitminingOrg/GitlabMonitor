package org.gitmining.monitor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.ProjectComment;
import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.ProjectEvent;
import org.gitmining.monitor.bean.StudentComment;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao extends BaseDaoImpl{
	public boolean insertProjectComment(ProjectComment projectComment){
		return sqlSession.insert("project.insertProjectComment", projectComment) == 1 ?true:false;
	}
	
	public List<ProjectComment> selectProjectComment(String team){
		return sqlSession.selectList("project.selectProjectComment", team);
	}
	
	public Integer selectProjectCommentCount(String team, String token,String time){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("team", team);
		params.put("token", token);
		params.put("time", time);
		return sqlSession.selectOne("project.selectProjectCommentCount",params);
	}
	public List<ProjectCommit> selectProjectCommitRange(String team, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("team", team);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectCommitRange", params);
	}
	
	public List<ProjectCommit> selectAllProjectCommitRange(String startDay, String endDay){
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("startDay", startDay);
//		params.put("endDay", endDay);
//		return sqlSession.selectList("project.selectAllProjectCommitRange", params);
		return selectAllProjectCommitRangeSort(startDay, endDay, "commit_count", "desc");
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
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("startDay", startDay);
//		params.put("endDay", endDay);
//		return sqlSession.selectList("project.selectAllProjectEventRange", params);
		return selectAllProjectEventRangeSort(startDay, endDay, "total", "desc");
	}
	
	public List<ProjectEvent> selectAllProjectEventRangeSort(String startDay, String endDay, String order, String method){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		params.put("order", order);
		params.put("method", method);
		System.out.println(method);
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
	
	public boolean insertProjectEvent(ProjectEvent projectEvent){
		return sqlSession.insert("project.insertProjectEvent", projectEvent) == 1 ?true:false;
	}
}
