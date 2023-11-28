package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "question")
public class Question {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private int quId;
	
	@Column(name = "qn_id")
	@JsonProperty("qn_id")
	private int qnId;
	
	@Column(name = "q_title")
	@JsonProperty("q_title")
	private String title;
	
	@Column(name = "option_type")
	@JsonProperty("option_type")
	private String optionType;
	
	@Column(name = "is_necessary") 
	@JsonProperty("is_necessary")
	private boolean necessary;
	
	@Column(name = "q_option")
	@JsonProperty("q_option")
	private String option;

	public Question() {
		super();
	}

	public Question(int quId, int qnId, String title, String optionType, boolean necessary, String option) {
		super();
		this.quId = quId;
		this.qnId = qnId;
		this.title = title;
		this.optionType = optionType;
		this.necessary = necessary;
		this.option = option;
	}

	public int getQuId() {
		return quId;
	}

	public void setQuId(int quId) {
		this.quId = quId;
	}

	public int getQnId() {
		return qnId;
	}

	public void setQnId(int qnId) {
		this.qnId = qnId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "Question [quId=" + quId + ", qnId=" + qnId + ", title=" + title + ", optionType=" + optionType
				+ ", necessary=" + necessary + ", option=" + option + "]";
	}
	
	

	

}
