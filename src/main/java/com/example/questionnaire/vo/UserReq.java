package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserReq { //request請求,Req為縮寫

	
	private Questionnaire questionnaire = new Questionnaire(); //一張問卷
	
	@JsonProperty("questionList")
	private List<Question> questionList = new ArrayList<>(); //問卷底下多個題目
	
	private User user;

	public UserReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserReq(Questionnaire questionnaire, List<Question> questionList, User user) {
		super();
		this.questionnaire = questionnaire;
		this.questionList = questionList;
		this.user = user;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
