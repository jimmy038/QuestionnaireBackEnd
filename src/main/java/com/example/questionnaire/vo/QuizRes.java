package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.constants.RtnCode;


public class QuizRes { //Res指Respones回應
	
	private List<QuizVo> quizVoList; //QuizVo代表一張問卷
	
	private RtnCode rtncode;
		
	private List<QnQuVo> qnquVoList;

	public QuizRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizRes(List<QuizVo> quizVoList, RtnCode rtncode, List<QnQuVo> qnquVoList) {
		super();
		this.quizVoList = quizVoList;
		this.rtncode = rtncode;
		this.qnquVoList = qnquVoList;
	}
	
	

	public QuizRes(RtnCode rtncode) {
		super();
		this.rtncode = rtncode;
	}
	
	

	public QuizRes(List<QuizVo> quizVoList, RtnCode rtncode) {
		super();
		this.quizVoList = quizVoList;
		this.rtncode = rtncode;
	}

	public List<QuizVo> getQuizVoList() {
		return quizVoList;
	}

	public void setQuizVoList(List<QuizVo> quizVoList) {
		this.quizVoList = quizVoList;
	}

	public RtnCode getRtncode() {
		return rtncode;
	}

	public void setRtncode(RtnCode rtncode) {
		this.rtncode = rtncode;
	}

	public List<QnQuVo> getQnquVoList() {
		return qnquVoList;
	}

	public void setQnquVoList(List<QnQuVo> qnquVoList) {
		this.qnquVoList = qnquVoList;
	}


}
