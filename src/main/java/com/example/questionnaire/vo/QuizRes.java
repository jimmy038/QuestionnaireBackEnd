package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.User;


public class QuizRes { //Res指Response回應
	
	private List<QuizVo> quizVoList; //QuizVo代表一張問卷
	
	private RtnCode rtncode;
		
	private List<QnQuVo> qnquVoList;
	
	private User user; //放入使用者資料表

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
	
	public QuizRes(List<QuizVo> quizVoList, RtnCode rtncode, User user) {
		super();
		this.quizVoList = quizVoList;
		this.rtncode = rtncode;
		this.user = user;
	}
	
	
	public QuizRes(RtnCode rtncode,List<QuizVo> quizVoList,User user) {
		super();
		this.quizVoList = quizVoList;
		this.rtncode = rtncode;
		this.user = user;
	}
	
	public QuizRes(RtnCode rtncode,List<QuizVo> quizVoList) {
		super();
		this.quizVoList = quizVoList;
		this.rtncode = rtncode;
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
	
	public QuizRes(User user, RtnCode rtncode) {
		super();
		this.user = user;
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

	public QuizRes(List<QuizVo> quizVoList, RtnCode rtncode, List<QnQuVo> qnquVoList, User user) {
		super();
		this.quizVoList = quizVoList;
		this.rtncode = rtncode;
		this.qnquVoList = qnquVoList;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
