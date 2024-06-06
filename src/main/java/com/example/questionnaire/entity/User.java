package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
<<<<<<< HEAD
=======

	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "name")
	private String name;
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ans_id")
	private int ansId;
	
	@Column(name = "name")
	private String name;

	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "age")
	private int age;
	
<<<<<<< HEAD
	@Column(name = "ans")
	private String ans;
	
	@Column(name = "qn_id")
	private int qnId;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int ansId, String name, String phoneNumber, String email, int age, String ans, int qnId,
			int questionId) {
		super();
		this.ansId = ansId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.age = age;
		this.ans = ans;
		this.qnId = qnId;
	}
	
	public int getAnsId() {
		return ansId;
	}

	public void setAnsId(int ansId) {
		this.ansId = ansId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public int getQnId() {
		return qnId;
	}

	public void setQnId(int qnId) {
		this.qnId = qnId;
	}


=======
	@Column(name = "qn_id")
	private int qnId;

	@Column(name = "question_id")
	private int questionId;
	
	@Column(name = "answer")
	private String answer;
	
	
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
}
