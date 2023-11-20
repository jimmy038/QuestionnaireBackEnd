package com.example.questionnaire.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Questionnaire;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{

	/**取的最新一筆資料:撈取全部資料後倒敘,最新的那筆資料會變成第一筆資料**/  //使用斜線星星可讓該程式碼移至該位子時顯示其註解
//	public Questionnaire findTopByOrderByIdDesc();
	
	//In
	public List<Questionnaire> findByIdIn(List<Integer> idList);

	//Containing模糊搜尋
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqalAndEndDateLessThanEqual(String title,LocalDate startDate,LocalDate endDate);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqalAndEndDateLessThanEqualAndPublishTrue(String title,LocalDate startDate,LocalDate endDate);

	
}
