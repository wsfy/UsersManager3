package com.hsp.domain;

public class User {
	
	private String name;
	private String pwd;
	private int id;
	private int grade;
	private String email;
	
	public User() {
		
	}
	
	public User(int id, String name, String email, int grade, String pwd) {
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.grade = grade;
		this.pwd = pwd;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
