package com.example.questionnaire.service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@Service
public class QuizServiceImpl implements QuizService{
	/*qn��Questionnaire,qu��Question*/
	
	@Autowired
	private QuestionnaireDao qnDao; //�Y�gqnDao
	
	@Autowired
	private QuestionDao quDao; //�Y�gquDao

	//�Φb�Y�n�N��i��P�ɦs�i��Ʈw��
	@Transactional //@Transactional(���),����g�bprivate�W��
	@Override //��(���k)���g��k�W�٦A�a�J��@�C(���k)�N�@�q���޿���ˬd����k��i
	public QuizRes create(QuizReq req) { 
		QuizRes checkResult = checkParam(req); //�ˬd�Ѽ�,�I�s�U������k
		if(checkResult != null) { //���]checkResult����k������null�ɦ^�Ǧ^�h
			return checkResult;
		}
		int quId = qnDao.save(req.getQuestionnaire()).getId();//�n���^�Ӧ]��Questionnaire�̭���qid�~�|�X��
		List<Question> quList = req.getQuestionList();
		if(quList.isEmpty()) { //�p�GList������
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		for(Question qu : quList) {
			qu.setQnId(quId);
		}
		quDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
		//�Y��null�N�O�q�L�U����Ӫ��ˬd
	}

	//���o�������ѼƧP�_,�ˬd4�ӰѼ�,�ѼƱqQuizRes�Ӫ�,�ѼƤ��ର��,�ˬd�o4�Ӽ��D.��J.�}�l�ε������
	private QuizRes checkParam(QuizReq req) { //�o�Ӥ�k�D�n�ˬd�ѼƧ@��,�o�Ӥ�k�^��RES�άOnull 
		Questionnaire qn = req.getQuestionnaire();
		if(!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription())
				|| qn.getStartDate() == null || qn.getEndDate() == null
				|| qn.getStartDate().isAfter(qn.getEndDate())) { //A�O�_�bB����(�}�l�ɶ��b�����ɶ����� ��false)
			return new QuizRes(RtnCode.QUESTIONNAIRE_PARAM_ERROR);//�p�GQUESTIONNAIRE�o�i����N���|�ˬd��ĤG�i
		}
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) { 
			if(qu.getQuId() <=0 || !StringUtils.hasText(qu.getTitle()) 
					|| !StringUtils.hasText(qu.getOptionType())|| !StringUtils.hasText(qu.getOption())) { //�o��if�P�_question��
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
			}
		}
		return null;
	}

	
	@Override //��s(�ק�w�s�b�����),1.�ˬd�Ѽ� 2.��s�J�������
	public QuizRes update(QuizReq req) {
		QuizRes checkResult = checkParam(req); //
		if(checkResult != null) { //
			return checkResult;
		}
		checkResult = checkQuestionnaireid(req);
		if(checkResult != null) {
			return checkResult;
		}
		Optional<Questionnaire> qnOp = qnDao.findById(req.getQuestionnaire().getId());
		if(qnOp.isEmpty()) { //�ˬdID�s���s�b,�p�G���s�b�߿��~�T���X�h
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
		}
		Questionnaire qn = qnOp.get();
		//�i�H�ק諸����,�P�_��ӱ���:
		//1.�|���o��:is_published == false,�i�H�ק�
		//2.�w�o�����|���}�l�i��:is_published == true + ��e�ɶ������p��start_date
		if(!qn.isPublished() || (qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate()))) { //LocalDate.now()��e�ɶ�
			qnDao.save(req.getQuestionnaire()); //�s�b�N�s��ƶi�h
			quDao.saveAll(req.getQuestionList());
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.UPDATE_ERROR);
	}
	
	
	//���k,�ˬdQuestionnaireid�̭���ID
	private QuizRes checkQuestionnaireid(QuizReq req) {
		if(req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) {
			if(qu.getQnId() != req.getQuestionnaire().getId()) { //�ˬdQuestion�̭���ID������Questionnaire�̭���ID
				return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
			}
		}
		return null;
	}

	
	@Override //�R���ݨ��D�ؤΰ��D
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList) {
		List<Questionnaire> qnList = qnDao.findByIdIn(qnIdList);
		List<Integer> idList = new ArrayList<>();
		for (Questionnaire qn : qnList) {			   //��e�ɶ��p��}�l���	
			if(!qn.isPublished() || qn.isPublished()&& LocalDate.now().isBefore(qn.getStartDate())) {
//				qnDao.deleteById(qn.getId());
				idList.add(qn.getId());
			}
		}
		if(idList.isEmpty()) { //���]idList���O�Ū��~�h���R���h�����
			qnDao.deleteAllById(idList); //�R���ݨ��D��,�i���Ʈw�@���R�h��
			quDao.deleteAllByQnIdIn(idList); //�R���ݨ����D,�@���R�h��
		}
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes deleteQuestion(int qnId, List<Integer> quIdList) {
		//							   �R�Ĥ@���ݨ�����,123�D
		Optional<Questionnaire> qnOP = qnDao.findById(qnId);
		if(qnOP.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		Questionnaire qn = qnOP.get();
		if(!qn.isPublished() || qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate())) {
			quDao.deleteAllByQnIdAndQuIdIn(qnId,quIdList);			
		}
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	
	@Override //(�j�M�ݨ�) 
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		//�T���B�⦡,�ݸ����䬰�P�_��,�k��u��true��false�����G,�_�����䬰��true���ɭ�
		//��title�����e�ɪ�^�쥻�����e���L,���M��^�Ŧr��
		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null? endDate : LocalDate.of(2099, 12, 31);
		List<Questionnaire> qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqalAndEndDateLessThanEqual(title, startDate, endDate);
		List<Integer> qnIds = new ArrayList<>();
		for(Questionnaire qu : qnList) { 
			qnIds.add(qu.getId());
		}
		List<Question> quList = quDao.findAllByQnIdIn(qnIds);
		List<QuizVo> quizVoList = new ArrayList<>();
		for(Questionnaire qn : qnList) { 
			QuizVo vo = new QuizVo();/*�@��Vo�N��@�Ӱݨ���ݨ������D��*/
			vo.setQuestionnaire(qn);//�˰ݨ��W�٤δy�z
			List<Question> questionList = new ArrayList<>();
			for(Question qu : quList) {
				if(qu.getQnId() == qn.getId()) { //����i�j��t��ϥ�if,��L�̪�ID�t��
					questionList.add(qu); 		 //�����ŦX�ɧ���D�[�J�ݨ���
				}
			}
			vo.setQuestionList(questionList); //
			quizVoList.add(vo); //�ⰵ����vo��iList�̭�
		}
		return new QuizRes(quizVoList, RtnCode.SUCCESSFUL);
	}
	
	//�����P�W���T���B�⦡�ۦP��
//	if(!StringUtils.hasText(title)) { //���]title��null,�Ŧr��,�ť�,��
//		title = "";
//	}
//	if(startDate == null) {
//		startDate = LocalDate.of(1971, 1, 1);
//	}
//	if(endDate == null) {
//		endDate = LocalDate.of(2099, 12, 31);
//	}
//	return null;
//}

	@Override //�j�M�C��
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, 
			LocalDate endDate,boolean isAll) {
		title = StringUtils.hasText(title) ? title : "";
		startDate = startDate != null? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null? endDate : LocalDate.of(2099, 12, 31);
		List<Questionnaire> qnList = new ArrayList<>();
			if(!isAll) {
				qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqalAndEndDateLessThanEqualAndPublishTrue(title, startDate, endDate);
			}else {
				qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqalAndEndDateLessThanEqual(title, startDate, endDate);
			}
		return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
	}

	
	@Override //
	public QuestionRes searchQuestionList(int qnId) {
		if(qnId <= 0 ) {
			return new QuestionRes(null,RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
		List<Question> quList = quDao.findAllByQnIdIn(Arrays.asList(qnId));
		return new QuestionRes(quList,RtnCode.SUCCESSFUL);
	}
		
}
