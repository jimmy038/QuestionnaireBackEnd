package com.example.questionnaire.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.UserDao;
import com.example.questionnaire.service.ifs.UserService;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private QuestionnaireDao qnDao;
	
	@Autowired
	private QuestionDao questionDao; 
	
	@Override 
	public UserRes saveUser(User user) {
		// 檢查必要資料是否為空或年齡小於等於0
		if(!StringUtils.hasText(user.getName()) || !StringUtils.hasText(user.getEmail()) ||
				!StringUtils.hasText(user.getPhoneNumber()) || user.getAge() <= 0 ) {
			return new UserRes(RtnCode.PARAM_ERROR);
		}
	    // 檢查問卷ID是否存在
		Optional<Questionnaire> qnop = qnDao.findById(user.getQnId());
		if(qnop.isEmpty()) {
			return new UserRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);	
		}
		try {	
			userDao.save(user);
			 return new UserRes(RtnCode.SUCCESSFUL);    
		} catch (Exception e) {
			 return new UserRes(RtnCode.DATABASE_ERROR);
		}
	}


	@Override //抓使用者填寫回答答案的ID
	public UserRes getAnsId(int ansId) {
		Optional<User> op = userDao.findById(ansId);
		if(op.isEmpty()) {
			User user = new User();
			return new UserRes(user,RtnCode.ID_NOT_FOUND);
		}
		return new UserRes(op.get(),RtnCode.SUCCESSFUL);	
	}


	@Override //存使用者資料 保留 測試用
	public UserRes saveUserData(UserReq userReq) {
		if(!StringUtils.hasText(userReq.getUser().getName()) || !StringUtils.hasText(userReq.getUser().getEmail()) ||
			!StringUtils.hasText(userReq.getUser().getPhoneNumber()) || userReq.getUser().getAge() <= 0 ) {
			return new UserRes(RtnCode.PARAM_ERROR);
		}
		Optional<Questionnaire> qnOp = qnDao.findById(userReq.getUser().getQnId());
		if(qnOp.isEmpty()) {
			return new UserRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);	
		}
		try {
			userDao.save(userReq.getUser());
			return new UserRes(RtnCode.SUCCESSFUL);	
		} catch (Exception e) {
			return new UserRes(RtnCode.DATABASE_ERROR);
		}
	}
	
	
	@Override //統計資料api   getCombinedData為 取得組合數據 的意思
	public QuizRes getCombinedData(int id,int ansId) {
		if(!qnDao.existsById(id)) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
		}
		//撈出問卷id接回來
		Optional<Questionnaire> quiz = qnDao.findById(id);
		//撈出問題id接回來 問題多筆用List
		List<Question> questions = questionDao.findAllByQnId(id);
		List<QuizVo> quizVo = new ArrayList<>();
		quizVo.add(new QuizVo(quiz.get(),questions));
		//撈使用者
		Optional<User> userOp = userDao.findById(ansId);
		//使用者id若不存在
		if(userOp.isEmpty()) {
			return new QuizRes(RtnCode.ID_NOT_FOUND);
		}
		User user = userOp.get();
		return new QuizRes(RtnCode.SUCCESSFUL, quizVo, user);
	}
	
}

	

