package com.example.questionnaire.vo;

import java.util.ArrayList;
import java.util.List;

import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;

//QuizReq extends(繼承) QuizVo裡面的物件
public class QuizReq extends QuizVo{ //request請求,Req為縮寫

	private List<Question> deleteQuestionList = new ArrayList<>();
	
	public QuizReq() {
		super();
	}

	public QuizReq(Questionnaire questionnaire, List<Question> questionList) {
		super(questionnaire, questionList);
	}

	public List<Question> getDeleteQuestionList() {
		return deleteQuestionList;
	}

	public void setDeleteQuestionList(List<Question> deleteQuestionList) {
		this.deleteQuestionList = deleteQuestionList;
	}

	
}
