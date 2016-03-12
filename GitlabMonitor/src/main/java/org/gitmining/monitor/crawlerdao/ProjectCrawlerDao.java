package org.gitmining.monitor.crawlerdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Branch;
import org.gitmining.monitor.bean.Project;
import org.gitmining.monitor.bean.ProjectCommit;

public class ProjectCrawlerDao extends BasicDao {
	
	public void insertGroupProjectInfo(int id,String name,String path,int groupID){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into groupproject(id,name,path,groupid) values(?,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, path);
			ps.setInt(4, groupID);
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Project> getGroupProject(){
		List<Project> result = new ArrayList<Project>();
 		try {
			PreparedStatement ps = conn.prepareStatement("select * from groupproject");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				project.setPath(rs.getString("path"));
				project.setGroupID(rs.getInt("groupid"));
				result.add(project);
			}
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		return result;
	}
	
	public void insertCommit(String id,String author_name,String author_email,String day,int add_line,int delete_line,int file,int projectid){
 		try {
			PreparedStatement ps = conn.prepareStatement("insert into commit(id,author_name,author_email,day,add_line,delete_line,file,projectid) values(?,?,?,?,?,?,?,?)");
			ps.setString(1, id);
			ps.setString(2, author_name);
			ps.setString(3, author_email);
			ps.setString(4, day);
			ps.setInt(5, add_line);
			ps.setInt(6, delete_line);
			ps.setInt(7, file);
			ps.setInt(8, projectid);
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<ProjectCommit> getProjectCommit(){
		List<ProjectCommit> result = new ArrayList<ProjectCommit>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT x.projectid,team.name team,x.day,x.commit_count,x.add_line,x.delete_line,x.file,SUM(y.commit_count) total_commit,SUM(y.add_line) total_add,SUM(y.delete_line) total_delete from (SELECT projectid,day,count(*) commit_count,SUM(add_line) add_line,SUM(delete_line) delete_line,SUM(file) file from `commit` GROUP BY projectid,day) x,(SELECT projectid,day,count(*) commit_count,SUM(add_line) add_line,SUM(delete_line) delete_line,SUM(file) file from `commit` GROUP BY projectid,day) y , groupproject , team WHERE x.projectid = y.projectid and x.day >= y.day and x.projectid = groupproject.id and groupid = team.id GROUP BY x.projectid,x.day");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ProjectCommit projectCommit = new ProjectCommit();
				projectCommit.setId(rs.getInt("projectid"));
				projectCommit.setTeam(rs.getString("team"));
				projectCommit.setDay(rs.getString("day"));
				projectCommit.setCommit_count(rs.getInt("commit_count"));
				projectCommit.setAdd_line(rs.getInt("add_line"));
				projectCommit.setDelete_line(rs.getInt("delete_line"));
				projectCommit.setJava_file(rs.getInt("file"));
				projectCommit.setTotal_commit(rs.getInt("total_commit"));
				projectCommit.setTotal_add(rs.getInt("total_add"));
				projectCommit.setTotal_delete(rs.getInt("total_delete"));
				result.add(projectCommit);
			}
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void insertProjectCommit(ProjectCommit projectCommit){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into projectCommit(id,team,day,commit_count,add_line,delete_line,java_file,total_commit,total_add,total_delete) values(?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, projectCommit.getId());
			ps.setString(2, projectCommit.getTeam());
			ps.setString(3, projectCommit.getDay());
			ps.setInt(4, projectCommit.getCommit_count());
			ps.setInt(5, projectCommit.getAdd_line());
			ps.setInt(6, projectCommit.getDelete_line());
			ps.setInt(7, projectCommit.getJava_file());
			ps.setInt(8, projectCommit.getTotal_commit());
			ps.setInt(9, projectCommit.getTotal_add());
			ps.setInt(10, projectCommit.getTotal_delete());
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<Integer, String> getIDAndTeam(){
		Map<Integer, String> result = new HashMap<Integer, String>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT distinct id,name from groupproject");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result.put(rs.getInt("id"), rs.getString("name"));
			}
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean findProjectCommit(int id,String day){
		boolean result = false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * from projectCommit where id=? and day=?");
			ps.setInt(1, id);
			ps.setString(2, day);
			ResultSet rs = ps.executeQuery();
			if(rs.next() == true){
				result = true;
			}
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void insertBranch(int projectID,String branchName){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into branch(projectid,branchname) values(?,?)");
			ps.setInt(1, projectID);
			ps.setString(2, branchName);
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Branch> getBranchByProjectID(int projectID){
		List<Branch> result = new ArrayList<Branch>();
		try {
			PreparedStatement ps = conn.prepareStatement("select projectid,branchname from branch where projectid=?");
			ps.setInt(1, projectID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Branch branch = new Branch();
				branch.setProjectID(rs.getInt("projectid"));
				branch.setBranchName(rs.getString("branchname"));
				result.add(branch);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public boolean findCommit(int projectID,String commitID){
		boolean result = false;
		try {
			PreparedStatement ps = conn.prepareStatement("select * from commit where projectid=? and id=?");
			ps.setInt(1, projectID);
			ps.setString(2, commitID);
			ResultSet rs = ps.executeQuery();
			result = rs.next();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public boolean findProjectByID(int projectID){
		boolean result = false;
		try {
			PreparedStatement ps = conn.prepareStatement("select * from groupproject where id=?");
			ps.setInt(1, projectID);
			ResultSet rs = ps.executeQuery();
			result = rs.next();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public boolean findBranch(int projectID,String branchName){
		boolean result = false;
		try {
			PreparedStatement ps = conn.prepareStatement("select * from branch where projectid=? and branchname=?");
			ps.setInt(1, projectID);
			ps.setString(2, branchName);
			ResultSet rs = ps.executeQuery();
			result = rs.next();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public void truncateProjectCommit(){
		try {
			PreparedStatement ps = conn.prepareStatement("delete from projectCommit");
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void insertUpdateLog(String day , int start , int end){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into updateLog(day,start,end) values(?,?,?)");
			ps.setString(1, day);
			ps.setInt(2, start);
			ps.setInt(3, end);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List<String> getDate(){
		List<String> result = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("select day from updateLog");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result.add(rs.getString("day"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  result;
	}
}
