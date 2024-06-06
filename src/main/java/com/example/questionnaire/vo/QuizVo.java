package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizVo { //QuizVo裡面放一個問卷跟自己的題目,一個Vo代表一個問卷跟問卷內的題目

	private Questionnaire questionnaire = new Questionnaire(); //一張問卷
	
	@JsonProperty("questionList")
	private List<Question> questionList = new ArrayList<>(); //問卷底下多個題目

	public QuizVo() {
		super();
	}

	public QuizVo(Questionnaire questionnaire, List<Question> questionList) {
		super();
		this.questionnaire = questionnaire;
		this.questionList = questionList;
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
	
	
}
