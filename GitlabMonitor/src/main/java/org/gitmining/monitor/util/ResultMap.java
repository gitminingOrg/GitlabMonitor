package org.gitmining.monitor.util;

import java.util.HashMap;
import java.util.Map;

public class ResultMap {
	public static final int SUCCESS_STATUS = 0;
	public static final int FAIL_STATUS = 1;
	public static final int ERROR_STATUS = 2;
	
	private int status;
	private String info;
	private Map<String, Object> content;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Map<String, Object> getContent() {
		return content;
	}
	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
	public ResultMap(){
		super();
		this.content = new HashMap<String, Object>();
	}
	
	public boolean add(String name,Object data){
		content.put(name, data);
		return true;
	}
	public Object get(String name){
		return content.get(name);
	}
}
