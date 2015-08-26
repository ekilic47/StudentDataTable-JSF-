package com.example.database;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


public class Student {
	private Integer stu_id;
	private String stu_name;
	private String stu_surname;

	public Integer getStu_id() {
		return stu_id;
	}
	public void setStu_id(Integer stu_id) {
		this.stu_id = stu_id;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public String getStu_surname() {
		return stu_surname;
	}
	public void setStu_surname(String stu_surname) {
		this.stu_surname = stu_surname;
	}


}
