package com.example.questionnaire.service.ifs;

import java.time.LocalDate;
import java.util.List;

import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;


public interface QuizService {

	public QuizRes create(QuizReq req); //創建表單
	
	public QuizRes update(QuizReq req); //更新(修改)
	
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList); //刪除Id可刪除多筆
	
	//刪除同張問卷的題目
	public QuizRes deleteQuestion(int qnId,List<Integer> quIdList); //刪除問題

	//搜尋裡面帶三個參數
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate);
	
	//
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,boolean isPublished);
	
	public QuestionRes searchQuestionList(int qnId); //撈問卷的所有問題qnId
	
}
