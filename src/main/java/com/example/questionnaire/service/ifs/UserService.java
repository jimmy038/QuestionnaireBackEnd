package com.example.questionnaire.service.ifs;


import org.springframework.stereotype.Service;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public interface UserService {

	public UserRes saveUser(User user); //存資料
    
	public UserRes getAnsId(int ansId);

	public UserRes saveUserData(UserReq userReq); //存資料

	//取得問卷 問題 使用者資料 統計api
	public QuizRes getCombinedData(int id,int ansId);
}
