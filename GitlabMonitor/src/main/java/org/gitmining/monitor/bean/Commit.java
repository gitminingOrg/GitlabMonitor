package org.gitmining.monitor.bean;

public class Commit {
	private String ID;
	private String author_name;
	private String author_email;
	private String day;
	private int add_line;
	private int delete_line;
	private int file;
	private int projectID;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getAuthor_email() {
		return author_email;
	}
	public void setAuthor_email(String author_email) {
		this.author_email = author_email;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
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
	public int getFile() {
		return file;
	}
	public void setFile(int file) {
		this.file = file;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
}
