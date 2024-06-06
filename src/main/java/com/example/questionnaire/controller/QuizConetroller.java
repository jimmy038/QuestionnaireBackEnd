package com.example.questionnaire.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.service.ifs.UserService;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizSearchReq;
import com.example.questionnaire.vo.UserRes;

/*Controller�R�W�覡���O���service*/
@RestController /* �n���bbuild.gradle�[web,�A���simport�i�� */
@CrossOrigin // @CrossOrigin (�����ШD)
public class QuizConetroller {

	// �s�W,�R��,�ק�,���ϥ�post Get�N���@��ШD�Ӥw,��¨��귽

	@Autowired /* Conetroller��Service������,@Autowired QuizService�i�� */
	private QuizService service;
	
	@Autowired
	private UserService userService;

	/* @RequestBody,�����~���ǤJ�����,���~���ϥΤu������L���«O��JSON�榡:�榡��:Key:value */
//	PostMapping �s�W post ���� http methods
	@PostMapping(value = "api/quiz/create") // ���@��api������榡���@��url,���~�����ϥ�,post�����O����HTTP���ШD��k
	public QuizRes create(@RequestBody QuizReq req) {
		return service.create(req);
	}

//	���w�q�F�o�Ӥ�k�B�z�Ӧ� /api/quiz/search ���|�� HTTP GET �ШD�C	
//	���ϥ� service.search(title, startDate, endDate) ��k�Ӱ�����骺�j�M�ާ@�A�ê�^�@�� QuizRes ����C�B�z GET �ШD����k�A�ϥ�@RequestBody���Ѩӫ��w�ШD���D�����ӳQ�M�g��QuizSearchReq��H�W�C�o�Ӥ�k����^������QuizRes�C	
	@GetMapping(value = "api/quiz/search") // (�j�M�ݨ�)
	public QuizRes search(QuizSearchReq req) {
//		���ϥ�StringUtils.hasText ��k�ˬd req ���� title �O�_����r�A�p�G���h�ϥθӤ�r�A�_�h�N��]�m���Ŧr��C		
//		String title = StringUtils.hasText(req.getTitle()) ? req.getTitle() : ""; //��null�ܬ��Ŧr��
//		����startDate�i���ˬd�A�p�G req ���� startDate ���� null�A�h�ϥθӭȡA�_�h�N��]�m�� 1971 �~ 1 �� 1 ��C		
//		LocalDate startDate = req.getStartDate() != null? req.getStartDate() : LocalDate.of(1971, 1, 1); //��startDate��null���ഫ���᭱()�����ɶ�
//		����endDate�i���ˬd�A�p�G req ���� endDate ���� null�A�h�ϥθӭȡA�_�h�N��]�m�� 2099 �~ 12 �� 31 ��C		
//		LocalDate endDate = req.getEndDate() != null? req.getEndDate() : LocalDate.of(2099, 12, 31); //��endDate��null���ഫ���᭱()�����ɶ�
//		���ϥ� service.search(title, startDate, endDate) ��k�Ӱ�����骺�j�M�ާ@�A�ê�^�@�� QuizRes ����C
//		return service.search(title,startDate,endDate);		
		return service.search(req.getTitle(), req.getStartDate(), req.getEndDate());
	}

	// update��s
	@PostMapping(value = "api/quiz/update")
	public QuizRes update(@RequestBody QuizReq req) {
		return service.update(req);
	}

	// deleteQuestionnaire�R���ݨ�
	@PostMapping(value = "api/quiz/deleteQuestionnaire")
	public QuizRes deleteQuestionnaire(@RequestBody List<Integer> qnIdList) {
		return service.deleteQuestionnaire(qnIdList);
	}

	// deleteQuestion�R���ݨ����D
	@PostMapping(value = "api/quiz/deleteQuestion")
	public QuizRes deleteQuestion(@RequestBody int qnId, List<Integer> quIdList) {
		return service.deleteQuestionnaire(quIdList);
	}

	// �j�M�ݨ��C��
	@GetMapping(value = "api/quiz/searchQuestionnaireList")
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,
			boolean isPublished) {
		return service.searchQuestionnaireList(title, startDate, endDate, isPublished);
	}

	// �j�M�ݨ��C��ҽk�j�M
	@GetMapping(value = "api/quiz/searchQuiz")
	public QuestionnaireRes searchQuizs(
		@RequestParam(value="title",required = false) String title,
		@RequestParam(value="startDate",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam(value="endDate",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return service.searchQuizs(title, startDate, endDate);
	}

	// �j�M���D�C��
	@GetMapping(value = "api/quiz/searchQuestionList")
	public QuestionRes searchQuestionList(int qnId) {
		return service.searchQuestionList(qnId);
	}
	
	//�s�ϥΪ̸��
	@PostMapping(value = "api/quiz/saveUser") // ���@��api������榡���@��url,���~�����ϥ�,post�����O����HTTP���ШD��k
	public UserRes saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	//��ݨ�ID�Χ�ݨ������U�������D
	@GetMapping(value = "api/quiz/getQuizInfo") 
	public QuizRes getQuizInfo(@RequestParam(value = "id") int id) {
		return service.getQuizInfo(id);
	}
	
	//��ϥΪ̦^���Ҧ���T��ID
	@GetMapping(value = "api/quiz/getAnsId") 
	public UserRes getAnsId(@RequestParam(value = "id") int ansId) {
		return userService.getAnsId(ansId);
	}
	
	//��Ҧ���� �ݨ� ���D user api
	@GetMapping(value = "api/quiz/getgetCombinedData") 
	public QuizRes getCombinedData(@RequestParam(value = "id")int id,
			@RequestParam(value = "ansId")int ansId) {
		return userService.getCombinedData(id,ansId);
	}
}
