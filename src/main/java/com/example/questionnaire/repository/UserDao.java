package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



import com.example.questionnaire.entity.User;

public interface UserDao extends JpaRepository<User, Integer>{

		
	
		/** 尋找user對應qnId的資料**/
		public List<User> findAllByqnId(int qnId) ;
			
		/** 將對應qnId的user資料也刪除 加上in(意思為包含在某範圍內)使用list多筆 刪除多筆對應問卷id**/
		public void deleteAllByqnIdIn(List<Integer> qnId);
		
	
  
}
