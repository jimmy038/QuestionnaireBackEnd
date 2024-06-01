package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;

public class QuizVo {
	
	private Questionnaire questionnaire;
	
	private List<Question> questionList;

	public Questionnaire getQuestionnaireList() {
		return questionnaire;
	}

	public void setQuestionnaireList(Questionnaire questionnaireList) {
		this.questionnaire = questionnaireList;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public QuizVo(Questionnaire questionnaireList, List<Question> questionList) {
		super();
		this.questionnaire = questionnaireList;
		this.questionList = questionList;
	}

	public QuizVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
