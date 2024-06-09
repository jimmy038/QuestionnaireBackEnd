package com.example.questionnaire.service.ifs;


import org.springframework.stereotype.Service;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public interface UserService {
	
	//存使用者資料
	public UserRes saveUser(User user); 
    
	//取得使用者資料
	public UserRes getAnsId(int ansId);
	
	//取得對應user表格內的問卷id資料
	public UserRes getAllQnid(int qnId);

	/** 測試 存資料 **/
	public UserRes saveUserData(UserReq userReq); 

	//取得問卷 問題 使用者資料 統計api
	public QuizRes getCombinedData(int id,int ansId);
}
