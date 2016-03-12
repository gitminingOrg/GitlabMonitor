package org.gitmining.monitor.bean;

import java.util.ArrayList;
import java.util.List;

public class StudentData {
	private String student;
	private List<Integer> data;
	
	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

	public StudentData(String student) {
		super();
		this.student = student;
		data = new ArrayList<Integer>();
	}
	
}
