package com.example.questionnaire.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.repository.UserDao;
import com.example.questionnaire.service.ifs.UserService;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	
	@Override
	public UserRes saveUser(UserReq req) {
        UserRes response;
        
        try {
            // �x�s�ϥΪ̸�ƪ��޿�
            // ���] userDao.save() �^�Ǥw�x�s���ϥΪ̹���

            // userDao.save(user);
            // ���]�x�s�ާ@���\
            
            response = new UserRes(RtnCode.SUCCESSFUL);
            System.out.println("�ϥΪ̸���x�s���\�C");
        } catch (Exception e) {
            // �B�z�ҥ~���p�ó]�m���ѰT��
            response = new UserRes(RtnCode.SAVE_ERROR);
            System.out.println("�ϥΪ̸���x�s���ѡC");
        }
        
        return response;
    }
}

	

