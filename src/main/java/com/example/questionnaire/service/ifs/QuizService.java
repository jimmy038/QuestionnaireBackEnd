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
	//�j�M�̭��a�T�ӰѼ�
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate);

	public QuizRes getQuizInfo(int id);
	
	
//========================================================================================//
	//SQL �y�ksearch
	public QuizRes searchFuzzy(String title, LocalDate startDate, LocalDate endDate);

	//�j�M�ݨ��C��
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,boolean isPublished);
	
	//�j�M�ݨ��C��ҽk�j�M��k
	public QuestionnaireRes searchQuizs(String title, LocalDate startDate, LocalDate endDate);
	
	//�j�M���D�C��
	public QuestionRes searchQuestionList(int qnId); //���ݨ����Ҧ����DqnId


=======
	
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
}
