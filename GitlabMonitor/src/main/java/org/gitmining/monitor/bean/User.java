package org.gitmining.monitor.bean;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class User {
	private int id;
	@Size(min=3, max=20, message="Name must be between 3 and 20 character long.")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="Name must be alphanumeric with no spaces")
	private String name;
	@Size(min=6, max=20, message="The password must be at least 6 character long.")
	private String password;
	@Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message="Invalid email address.")
	private String email;
	private String token;
	private int school_id;
	@NotBlank(message="Please choose one role")
	private String role;
	// 状态：激活需要管理员和邮箱认证，0：均未认证；1：邮箱认证管理员未认证；2：邮箱未认证管理员认证；3：均已认证
	private int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getSchool_id() {
		return school_id;
	}
	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
