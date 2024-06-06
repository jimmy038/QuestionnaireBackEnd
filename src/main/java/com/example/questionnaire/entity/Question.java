package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {

	@Id
	@Column(name = "question_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "qn_id")
	private int qnId;
	
	@Column(name = "question_title")
	private String qTitle;
	
	@Column(name = "option_type")
	private String optionType;
	
	@Column(name = "is_necessary")
	private String necessary;
	
	@Column(name = "q_option")
	private String option;

	public Question(int id, int qnId, String qTitle, String optionType, String necessary, String option) {
		super();
		this.id = id;
		this.qnId = qnId;
		this.qTitle = qTitle;
		this.optionType = optionType;
		this.necessary = necessary;
		this.option = option;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQnId() {
		return qnId;
	}

	public void setQnId(int qnId) {
		this.qnId = qnId;
	}

	public String getqTitle() {
		return qTitle;
	}

	public void setqTitle(String qTitle) {
		this.qTitle = qTitle;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getNecessary() {
		return necessary;
	}

	public void setNecessary(String necessary) {
		this.necessary = necessary;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

<<<<<<< HEAD
=======
	
	
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
}
