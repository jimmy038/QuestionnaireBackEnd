package com.example.questionnaire.service.ifs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;

@Service
public interface QuizService {

	public QuizRes create(QuizReq req); //創建表單
	
	public QuizRes update(QuizReq req); //更新(修改)
	
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList); //刪除Id可刪除多筆
	
	//刪除同張問卷的題目
	public QuizRes deleteQuestion(int qnId,List<Integer> quIdList); //刪除問題

	//搜尋裡面帶三個參數
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate);

//	public void saveUser(String name,String unmber,String email,int age);
//========================================================================================//
	//SQL 語法search
	public QuizRes searchFuzzy(String title, LocalDate startDate, LocalDate endDate);

	//搜尋問卷列表
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,boolean isPublished);
	
	//搜尋問卷列表模糊搜尋方法
	public QuestionnaireRes searchQuizs(String title, LocalDate startDate, LocalDate endDate);
	
	
	//搜尋問題列表
	public QuestionRes searchQuestionList(int qnId); //撈問卷的所有問題qnId
	
}
