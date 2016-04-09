package org.gitmining.monitor.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScoreRange implements Serializable{
	private String name;
	private List<Integer> data;
	
	
	public ScoreRange() {
		super();
	}

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

	public ScoreRange(String name) {
		super();
		this.name = name;
		data = new ArrayList<Integer>();
		for(int i=0;i<10; i++){
			data.add(0);
		}
	}
	
	public void setValue(int index,int value){
		if(data.size() == 10){
			data.set(index,value);
		}
	}
}
