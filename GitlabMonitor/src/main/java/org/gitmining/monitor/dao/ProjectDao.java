package org.gitmining.monitor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.DeadLine;
import org.gitmining.monitor.bean.ProjectComment;
import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.ProjectEvent;
import org.gitmining.monitor.bean.ProjectVO;
import org.gitmining.monitor.bean.Student;
import org.gitmining.monitor.bean.TeamVO;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao extends BaseDaoImpl{
	public boolean insertProjectComment(ProjectComment projectComment){
		return sqlSession.insert("project.insertProjectComment", projectComment) == 1 ?true:false;
	}
	
	public List<ProjectComment> selectProjectComment(String team){
		return sqlSession.selectList("project.selectProjectComment", team);
	}
	public List<TeamVO> selectLikeTeams(String team){
		team = "%"+team+"%";
		return sqlSession.selectList("project.selectTeamLikeInfo", team);
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
	
	public List<ProjectCommit> selectCourseProjectCommitRangeSort(String course,String startDay, String endDay, String order, String method){
		if(order == null || order.length() == 0){
			order = "commit_count";
		}
		if(method == null || method.length() == 0){
			method = "desc";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("course", course);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		params.put("order", order);
		params.put("method", method);
		return sqlSession.selectList("project.selectAllProjectCommitRange", params);
	}
	
	
	
	public TeamVO selectTeamInfo(String team){
		return sqlSession.selectOne("project.selectTeamInfo", team);
	}
	
	public List<Student> selectTeamStudent(String team){
		return sqlSession.selectList("project.selectTeamStudent", team);
	}
	
	public List<ProjectVO> selectTeamProject(String team){
		return sqlSession.selectList("project.selectTeamProject", team);
	}
	public List<Integer> selectProjectCommitItemRange(String item,int projectId, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("projectId", projectId);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectCommitItemRange", params);
	}
	
	public List<String> selectProjectCommitItemRangeDay(String item,int projectId, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("projectId", projectId);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectCommitItemRangeDay", params);
	}
	
	
	public List<Integer> selectProjectEventItemRange(String item,String team, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("team", team);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectEventItemRange", params);
	}
	
	public List<String> selectProjectEventItemRangeDay(String item,String team, String startDay, String endDay){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("item", item);
		params.put("team", team);
		params.put("startDay", startDay);
		params.put("endDay", endDay);
		return sqlSession.selectList("project.selectProjectEventItemRangeDay", params);
	}

	public List<String> selectAllCourseNames(){
		return sqlSession.selectList("project.selectAllCourses");
	}
	
	public List<DeadLine> getProjectDeadLine(int projectId){
		return sqlSession.selectList("project.selectCourseDeadlineByProjectId", projectId);
	}
}
