package org.gitmining.monitor.bean;

import java.util.ArrayList;
import java.util.List;

public class ScoreRange {
	String name;
	volatile List<Double> data;
	public ScoreRange(String name) {
		super();
		this.name = name;
		data = new ArrayList<Double>();
	}
	
	public void addValue(double value){
		if(data != null){
			data.add(value);
		}
	}
}
