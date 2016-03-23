package org.gitmining.monitor.bean;

import java.io.Serializable;

public class MailBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3616704907813843729L;
	public static final int SIMPLE_MAIL = 1;
	public static final int HTML_MAIL = 2;
	private String from;
	private String to;
	private String title;
	private String content;
	private int type;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public MailBean(String from, String to, String title, String content,
			int type) {
		super();
		this.from = from;
		this.to = to;
		this.title = title;
		this.content = content;
		this.type = type;
	}
	
}
