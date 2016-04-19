package org.gitmining.monitor.bean;

import java.util.List;

public class MemberCommit {
	private String name;
	private List<Integer> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	
	
}
