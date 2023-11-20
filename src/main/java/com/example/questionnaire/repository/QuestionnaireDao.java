package com.example.questionnaire.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Questionnaire;

@Repository
public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer>{

	/**�����̷s�@�����:����������ƫ�˱�,�̷s��������Ʒ|�ܦ��Ĥ@�����**/  //�ϥα׽u�P�P�i���ӵ{���X���ܸӦ�l����ܨ����
//	public Questionnaire findTopByOrderByIdDesc();
	
	//In
	public List<Questionnaire> findByIdIn(List<Integer> idList);

	//Containing�ҽk�j�M
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqalAndEndDateLessThanEqual(String title,LocalDate startDate,LocalDate endDate);
	
	public List<Questionnaire> findByTitleContainingAndStartDateGreaterThanEqalAndEndDateLessThanEqualAndPublishTrue(String title,LocalDate startDate,LocalDate endDate);

	
}
