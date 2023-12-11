package com.example.questionnaire.service.ifs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.repository.UserDao;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public interface UserService {

	public UserRes saveUser(UserReq req); //¦s¸ê®Æ
    
	
	

}
