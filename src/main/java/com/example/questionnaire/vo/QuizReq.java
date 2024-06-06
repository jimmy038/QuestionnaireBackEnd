package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;

public class QuizReq {

	private Questionnaire qusetionnaire ;
	
	private List<Question> questionList = new ArrayList<>();

	public QuizReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizReq(Questionnaire qusetionnaire, List<Question> questionList) {
		super();
		this.qusetionnaire = qusetionnaire;
		this.questionList = questionList;
	}

	public Questionnaire getQusetionnaire() {
		return qusetionnaire;
	}

	public void setQusetionnaire(Questionnaire qusetionnaire) {
		this.qusetionnaire = qusetionnaire;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}	

	

}
