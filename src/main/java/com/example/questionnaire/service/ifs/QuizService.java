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

	public QuizRes create(QuizReq req); //�Ыت��
	
	public QuizRes update(QuizReq req); //��s(�ק�)
	
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList); //�R��Id�i�R���h��
	
	//�R���P�i�ݨ����D��
	public QuizRes deleteQuestion(int qnId,List<Integer> quIdList); //�R�����D

	//�j�M�̭��a�T�ӰѼ�
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate);

//	public void saveUser(String name,String unmber,String email,int age);
//========================================================================================//
	//SQL �y�ksearch
	public QuizRes searchFuzzy(String title, LocalDate startDate, LocalDate endDate);

	//�j�M�ݨ��C��
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,boolean isPublished);
	
	//�j�M�ݨ��C��ҽk�j�M��k
	public QuestionnaireRes searchQuizs(String title, LocalDate startDate, LocalDate endDate);
	
	
	//�j�M���D�C��
	public QuestionRes searchQuestionList(int qnId); //���ݨ����Ҧ����DqnId
	
}
