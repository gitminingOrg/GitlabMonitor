package org.gitmining.monitor.bean;

public class ProjectComment {
	private int id;
	private String team;
	private String token;
	private String words;
	private String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
