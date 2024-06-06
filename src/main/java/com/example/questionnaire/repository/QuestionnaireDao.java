package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Questionnaire;



@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{

	/** 取得最新一筆資料:撈出資料後倒敘排列，倒敘後依照ID找最新一筆DESC倒敘排列 **/
	public Questionnaire findTopByOrederByIdDesc();
		
	public Questionnaire findByIdIn(List<Integer> idList);
	
}
