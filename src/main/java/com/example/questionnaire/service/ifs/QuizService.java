package com.example.questionnaire.service.ifs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.UserReq;
import com.example.questionnaire.vo.UserRes;

@Service
public interface QuizService {

	public QuizRes create(QuizReq req);
	
	public QuizRes update(QuizReq req);
	
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList);
	
	public QuizRes deleteQuestion(int qnId,List<Integer> quIdList);
	
	public QuizRes search(String title, LocalDate startDate,LocalDate endDate);

<<<<<<< HEAD
	//搜尋裡面帶三個參數
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate);

	public QuizRes getQuizInfo(int id);
	
	
//========================================================================================//
	//SQL 語法search
	public QuizRes searchFuzzy(String title, LocalDate startDate, LocalDate endDate);

	//搜尋問卷列表
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,boolean isPublished);
	
	//搜尋問卷列表模糊搜尋方法
	public QuestionnaireRes searchQuizs(String title, LocalDate startDate, LocalDate endDate);
	
	//搜尋問題列表
	public QuestionRes searchQuestionList(int qnId); //撈問卷的所有問題qnId


=======
	
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
}
