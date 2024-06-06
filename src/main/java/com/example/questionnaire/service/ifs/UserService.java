package com.example.questionnaire.service.ifs;

<<<<<<< HEAD

import org.springframework.stereotype.Service;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;
=======
import org.springframework.stereotype.Service;

import com.example.questionnaire.vo.QuizReq;
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0

@Service
public interface UserService {

<<<<<<< HEAD
	public UserRes saveUser(User user); //存資料
    
	public UserRes getAnsId(int ansId);

	public UserRes saveUserData(UserReq userReq); //存資料

	//取得問卷 問題 使用者資料 統計api
	public QuizRes getCombinedData(int id,int ansId);
=======
	
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
}
