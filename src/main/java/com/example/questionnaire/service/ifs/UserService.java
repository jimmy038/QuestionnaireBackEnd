package com.example.questionnaire.service.ifs;


import org.springframework.stereotype.Service;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public interface UserService {
	
	//�s�ϥΪ̸��
	public UserRes saveUser(User user); 
    
	//���o�ϥΪ̸��
	public UserRes getAnsId(int ansId);
	
	//���o����user��椺���ݨ�id���
	public UserRes getAllQnid(int qnId);

	/** ���� �s��� **/
	public UserRes saveUserData(UserReq userReq); 

	//���o�ݨ� ���D �ϥΪ̸�� �έpapi
	public QuizRes getCombinedData(int id,int ansId);
}
