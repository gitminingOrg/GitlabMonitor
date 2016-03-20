package org.gitmining.monitor.bean;

import java.util.Map;

public class ProjectCommit {
	private int id;
	private String name;
	private String team;
	private String day;
	private int commit_count;
	private int add_line;
	private int delete_line;
	private int java_file;
	private int total_commit;
	private int total_add;
	private int total_delete;
	private double formula = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getCommit_count() {
		return commit_count;
	}
	public void setCommit_count(int commit_count) {
		this.commit_count = commit_count;
	}
	public int getAdd_line() {
		return add_line;
	}
	public void setAdd_line(int add_line) {
		this.add_line = add_line;
	}
	public int getDelete_line() {
		return delete_line;
	}
	public void setDelete_line(int delete_line) {
		this.delete_line = delete_line;
	}
	public int getJava_file() {
		return java_file;
	}
	public void setJava_file(int java_file) {
		this.java_file = java_file;
	}
	public int getTotal_commit() {
		return total_commit;
	}
	public void setTotal_commit(int total_commit) {
		this.total_commit = total_commit;
	}
	public int getTotal_add() {
		return total_add;
	}
	public void setTotal_add(int total_add) {
		this.total_add = total_add;
	}
	public int getTotal_delete() {
		return total_delete;
	}
	public void setTotal_delete(int total_delete) {
		this.total_delete = total_delete;
	}
	public double getFormula() {
		return formula;
	}
	public void setFormula(double formula) {
		this.formula = formula;
	}
	
	public boolean validate(Map<String, Integer> filter){
		if(filter.containsKey("commit_count<") && commit_count >= filter.get("commit_count<")){
				return false;
		}
		if(filter.containsKey("commit_count>") && commit_count <= filter.get("commit_count>")){
			return false;
		}
		
		if(filter.containsKey("add_line<") && add_line >= filter.get("add_line<")){
			return false;
		}
		if(filter.containsKey("add_line>") && add_line <= filter.get("add_line>")){
			return false;
		}
		
		if(filter.containsKey("delete_line<") && delete_line >= filter.get("delete_line<")){
			return false;
		}
		if(filter.containsKey("delete_line>") && delete_line <= filter.get("delete_line>")){
			return false;
		}
		
		if(filter.containsKey("java_file<") && java_file >= filter.get("java_file<")){
			return false;
		}
		if(filter.containsKey("java_file>") && java_file <= filter.get("java_file>")){
			return false;
		}
		
		if(filter.containsKey("total_add<") && total_add >= filter.get("total_add<")){
			return false;
		}
		if(filter.containsKey("total_add>") && total_add <= filter.get("total_add>")){
			return false;
		}
		
		if(filter.containsKey("total_delete<") && total_delete >= filter.get("total_delete<")){
			return false;
		}
		if(filter.containsKey("total_delete>") && total_delete <= filter.get("total_delete>")){
			return false;
		}
		return true;
	}
}
