package org.gitmining.monitor.crawlerdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gitmining.monitor.bean.Student;
import org.gitmining.monitor.bean.StudentCommit;

public class StudentCrawlerDao extends BasicDao{
	
	public void insertTeamInfo(int id,String name,String path,String description,String web_url,int ucode){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into team(id,name,path,description,web_url,ucode) values(?,?,?,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, path);
			ps.setString(4, description);
			ps.setString(5, web_url);
			ps.setInt(6, ucode);
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertTeamMemberInfo(int memberID,String memberName,String team){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into teamstudent(id,name,team) values(?,?,?)");
			ps.setInt(1, memberID);
			ps.setString(2, memberName);
			ps.setString(3, team);
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Integer> getTeamID(){
		List<Integer> result = new ArrayList<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement("select id from team");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result.add(rs.getInt("id"));
			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List<StudentCommit> getStudentCommit(){
		List<StudentCommit> result = new ArrayList<StudentCommit>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT x.projectid,x.author_name,x.day,x.commit_count,x.add_line,x.delete_line,x.file,SUM(y.commit_count) total_commit,SUM(y.add_line) total_add,SUM(y.delete_line) total_delete  from (SELECT author_name,projectid,day,count(*) commit_count,SUM(add_line) add_line,SUM(delete_line) delete_line,SUM(file) file from `commit` GROUP BY projectid,day,author_name) x,(SELECT author_name,projectid,day,count(*) commit_count,SUM(add_line) add_line,SUM(delete_line) delete_line,SUM(file) file from `commit` GROUP BY projectid,day,author_name) y WHERE x.projectid = y.projectid and x.author_name = y.author_name and x.day >= y.day GROUP BY x.projectid,x.day,x.author_name");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				StudentCommit studentCommit = new StudentCommit();
				studentCommit.setId(rs.getInt("projectid"));
				studentCommit.setStudent(rs.getString("author_name"));
				studentCommit.setDay(rs.getString("day"));
				studentCommit.setCommit_count(rs.getInt("commit_count"));
				studentCommit.setAdd_line(rs.getInt("add_line"));
				studentCommit.setDelete_line(rs.getInt("delete_line"));
				studentCommit.setJava_file(rs.getInt("file"));
				studentCommit.setTotal_commit(rs.getInt("total_commit"));
				studentCommit.setTotal_add(rs.getInt("total_add"));
				studentCommit.setTotal_delete(rs.getInt("total_delete"));
				result.add(studentCommit);
			}
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void insertStudentCommmit(StudentCommit studentCommit){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into studentCommit(id,student,day,commit_count,add_line,delete_line,java_file,total_commit,total_add,total_delete) values(?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, studentCommit.getId());
			ps.setString(2, studentCommit.getStudent());
			ps.setString(3, studentCommit.getDay());
			ps.setInt(4, studentCommit.getCommit_count());
			ps.setInt(5, studentCommit.getAdd_line());
			ps.setInt(6, studentCommit.getDelete_line());
			ps.setInt(7, studentCommit.getJava_file());
			ps.setInt(8, studentCommit.getTotal_commit());
			ps.setInt(9, studentCommit.getTotal_add());
			ps.setInt(10, studentCommit.getTotal_delete());
			ps.execute();
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Object> getStudentIDAndName(){
		List<Object> result = new ArrayList<Object>();
		List<Integer> ids = new ArrayList<Integer>();
		List<String> student = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT groupproject.id,teamstudent.`name` FROM groupproject,team,teamstudent WHERE teamstudent.team = team.`name` and team.id = groupid");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ids.add(rs.getInt("id"));
				student.add(rs.getString("name"));
			}
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.add(ids);
		result.add(student);
		return result;
	}
	
	public boolean findStudentCommit(int id,String name,String day){
		boolean result = false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * from studentCommit where id=? and student=? and day=?");
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, day);
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
	
	public boolean findTeam(int id,String name){
		boolean result = false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * from team where id=? and name=?");
			ps.setInt(1, id);
			ps.setString(2, name);
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
	
	public boolean findStudent(int memberID,String memberName,String team){
		boolean result = false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * from student where id=? and name=? and team=?");
			ps.setInt(1, memberID);
			ps.setString(2, memberName);
			ps.setString(3, team);
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
	
	public void truncateStudentCommit(){
		try {
			PreparedStatement ps = conn.prepareStatement("delete from studentCommit");
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List<Integer> getStudentID(){
		List<Integer> result = new ArrayList<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement("select distinct id from teamstudent");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result.add(rs.getInt("id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public void insertStudentInfo(Student student){
		try {
			PreparedStatement ps = conn.prepareStatement("insert into studentinfo(id,name,web_url,created_at,bio,email) values(?,?,?,?,?,?)");
			ps.setInt(1, student.getId());
			ps.setString(2, student.getName());
			ps.setString(3, student.getWeb_url());
			ps.setString(4, student.getCreated_at());
			ps.setString(5, student.getBio());
			ps.setString(6, student.getEmail());
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List<String> getTeamMemberByProjectID(int projectID){
		List<String> result = new ArrayList<String>();
		try {
			PreparedStatement ps = conn.prepareStatement("select teamstudent.`name` from groupproject,team,teamstudent where groupproject.id=? and groupproject.groupid = team.id and team.`name` = teamstudent.team");
			ps.setInt(1, projectID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result.add(rs.getString("name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public int getFileNumberByDayAndProject(int porjectID,String day,String author_name){
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT author_name,COUNT(DISTINCT filename) number from `commit`,file where day <=? and id = sha and author_name=? and projectid=?");
			ps.setString(1, day);
			ps.setString(2, author_name);
			ps.setInt(3, porjectID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result = rs.getInt("number");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	public int getRelationByFile(String authorA,String authorB,int projectID,String day){
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement("select COUNT(*) number from (select DISTINCT filename from `commit`,file where author_name=? and projectid=? and id = sha and day<=?) A,(select DISTINCT filename from `commit`,file where author_name=? and projectid =? and id = sha and day<=?) B WHERE A.filename = B.filename");
			ps.setString(1, authorA);
			ps.setInt(2, projectID);
			ps.setString(3, day);
			ps.setString(4, authorB);
			ps.setInt(5, projectID);
			ps.setString(6, day);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result = rs.getInt("number");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
}
