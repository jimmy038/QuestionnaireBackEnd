package com.example.questionnaire.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "num")
	private int num;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "qn_id")
	private int qnid;
	
	@Column(name = "q_id")
	private int qid;
	
	@Column(name = "ans")
	private String ans;
	
	@Column(name = "date_time")
	private LocalDateTime dateTime;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int num, String phoneNumber, String email, int age, int qnid, int qid, String ans,
			LocalDateTime dateTime) {
		super();
		this.num = num;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.age = age;
		this.qnid = qnid;
		this.qid = qid;
		this.ans = ans;
		this.dateTime = dateTime;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getQnid() {
		return qnid;
	}

	public void setQnid(int qnid) {
		this.qnid = qnid;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	

	
	
}
