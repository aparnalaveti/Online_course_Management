package com.klef.jfsd.springboot.model;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="course_table")
public class Course 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="c_code")
	private String code;
	@Column(name="c_name",nullable=false,length = 50)
	private String title;
	@Column(name="c_desc",nullable=false,length = 100)
	private String description;
	@Column(name="c_dept",nullable=false,length = 50)
	private String department;
	@Column(name="c_prof",nullable=false,length = 50)
	private int professor;
	private Blob cimage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getProfessor() {
		return professor;
	}
	public void setProfessor(int professor) {
		this.professor = professor;
	}
	public Blob getCimage() {
		return cimage;
	}
	public void setCimage(Blob cimage) {
		this.cimage = cimage;
	}
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", code=" + code + ", title=" + title + ", description=" + description
				+ ", department=" + department + ", professor=" + professor + ", cimage=" + cimage + "]";
	}
	
}
