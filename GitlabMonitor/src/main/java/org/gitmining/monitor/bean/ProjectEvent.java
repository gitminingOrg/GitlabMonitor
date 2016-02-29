package org.gitmining.monitor.bean;

public class ProjectEvent {
	private int id;
	private String student;
	private String team;
	private int push;
	private int issue;
	private int comment;
	private int create;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getPush() {
		return push;
	}
	public void setPush(int push) {
		this.push = push;
	}
	public int getIssue() {
		return issue;
	}
	public void setIssue(int issue) {
		this.issue = issue;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public int getCreate() {
		return create;
	}
	public void setCreate(int create) {
		this.create = create;
	}
	
}
