package org.gitmining.monitor.bean;

import java.io.Serializable;

public class RangeColor implements Serializable{
	int from;
    int to;
    String color;
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
