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
            // 儲存使用者資料的邏輯
            // 假設 userDao.save() 回傳已儲存的使用者實體

            // userDao.save(user);
            // 假設儲存操作成功
            
            response = new UserRes(RtnCode.SUCCESSFUL);
            System.out.println("使用者資料儲存成功。");
        } catch (Exception e) {
            // 處理例外狀況並設置失敗訊息
            response = new UserRes(RtnCode.SAVE_ERROR);
            System.out.println("使用者資料儲存失敗。");
        }
        
        return response;
    }
}

	

