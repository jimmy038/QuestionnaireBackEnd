package com.example.questionnaire.vo;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.User;

public class UserRes { //Res  Response回應

	
	private User user; //放入使用者資料表
	
	private RtnCode rtncode;

	public UserRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public UserRes(RtnCode rtncode) {
		super();
		this.rtncode = rtncode;
	}
	
	public UserRes(User user, RtnCode rtncode) {
		super();
		this.user = user;
		this.rtncode = rtncode;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RtnCode getRtncode() {
		return rtncode;
	}

	public void setRtncode(RtnCode rtncode) {
		this.rtncode = rtncode;
	}

	
}
