package org.gitmining.monitor.bean;

public class ItemStatistics {
	private int item_id;
	private String item_name;
	private double average=0;
	private double range=0;
	private double variance=0;
	private double median=0;
	private double upper_quartile=0;
	private double lower_quartile=0;
	private String time;
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}
	public double getMedian() {
		return median;
	}
	public void setMedian(double median) {
		this.median = median;
	}
	public double getUpper_quartile() {
		return upper_quartile;
	}
	public void setUpper_quartile(double upper_quartile) {
		this.upper_quartile = upper_quartile;
	}
	public double getLower_quartile() {
		return lower_quartile;
	}
	public void setLower_quartile(double lower_quartile) {
		this.lower_quartile = lower_quartile;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
