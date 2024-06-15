package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



import com.example.questionnaire.entity.User;

public interface UserDao extends JpaRepository<User, Integer>{

		
	
		/** �M��user����qnId�����**/
		public List<User> findAllByqnId(int qnId) ;
			
		/** �N����qnId��user��Ƥ]�R�� �[�Win(�N�䬰�]�t�b�Y�d��)�ϥ�list�h�� �R���h�������ݨ�id**/
		public void deleteAllByqnIdIn(List<Integer> qnId);
		
	
  
}
