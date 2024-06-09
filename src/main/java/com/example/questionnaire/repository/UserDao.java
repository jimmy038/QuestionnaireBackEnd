package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



import com.example.questionnaire.entity.User;

public interface UserDao extends JpaRepository<User, Integer>{

	
		/** 尋找user對應qnId的資料**/
		public List<User> findAllByqnId(int qnId) ;
			
		/** 將對應qnId的user資料也刪除 **/
		public void deleteAllByqnId(int qnId);
  
}
