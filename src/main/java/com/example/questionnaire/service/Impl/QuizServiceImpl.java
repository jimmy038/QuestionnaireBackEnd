package com.example.questionnaire.service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QnQuVo;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@Service
public class QuizServiceImpl implements QuizService{
	/*qn��Questionnaire,qu��Question*/
	
	@Autowired
	private QuestionnaireDao qnDao; //�D�n�n�I�sDao,�ާ@�P��Ʈw���s��,�Y�gqnDao
	
	@Autowired
	private QuestionDao quDao; 		//�D�n�n�I�sDao,�ާ@�P��Ʈw���s��,�Y�gquDao

	//�Φb�n�N��i��P�ɦs�i��Ʈw��
	@Transactional //@Transactional(���),��2��save���ন�\save���ɭԤ~�|save�A�u��[�bpublic �W��
	@Override 	   
	public QuizRes create(QuizReq req) { 
//		��(���k)���g��k�W�٦A�a�J��@�C(���k)�N�@�q���޿���ˬd����k���@�Ϊ�������X�Ӱ��ϥ�	
		QuizRes checkResult = checkParam(req); //�ˬd�Ѽ�,�I�s�U��checkParam����k
		if(checkResult != null) { //���]checkResult����k������null�ɦ^�Ǧ^�h
			return checkResult;   //�^��checkResult�^�h
		}
		// �x�s��A�� QN ���̷s�@����ID�ԥX�ӡA�s��QU��qn_id���A
		int qnId = qnDao.save(req.getQuestionnaire()).getId();//�n���^�Ӧ]��Questionnaire�̭���qid�~�|�X��
		List<Question> quList = req.getQuestionList();
		
		// �i�H�u�s�W�ݨ��A�ݨ����S���D��
//		if(quList.isEmpty()) { //�p�GquList�����Ůɦ^��SUCCESSFUL���\���T��
//			System.out.println("fail: "+quList);
//			return new QuizRes(RtnCode.SUCCESSFUL);
//		}
		for(Question qu : quList) {
			qu.setQnId(qnId); //�]�w�ݨ���ID������ݨ����D��ID�Ϩ��������D��
		}
		quDao.saveAll(quList);
		System.out.println("SUCCESSFUL: "+ quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
		//�Y��null�N�O�q�L�U��checkParam��k����Ӫ��ˬd
		
	}

	//���o������@�qQuizRes�Ӫ��Ѽ��ˬd,�ˬd4�ӰѼ�,�ѼƱqQuizRes�Ӫ�,�ѼƤ��ର��,�ˬd�o4�Ӽ��D.��J.�}�l�ε������
	private QuizRes checkParam(QuizReq req) { //�o�Ӥ�k�D�n�ˬd�ѼƧ@��,�o�Ӥ�k�^��RES�άOnull 
//		Questionnaire qn = req.getQuestionnaire();
////		�T�O�ݨ����D(Title)�M�y�z(Description)�D�šC�T�O�ݨ����}�l���(StartDate)�M�������(EndDate)�����ťB�X�z�A�åB�}�l������ߩ󵲧�����C		
//		if(!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription())
//				|| qn.getStartDate() == null || qn.getEndDate() == null
//				|| qn.getStartDate().isAfter(qn.getEndDate())) {  //A�O�_�bB����(�}�l�ɶ��b�����ɶ����� ��false)
//			return new QuizRes(RtnCode.QUESTIONNAIRE_PARAM_ERROR);//�p�GQUESTIONNAIRE�o�i����N���|�ˬd��ĤG�i
//		}
////		���C�Ӱ��D�A�T�O���D��ID(QuId)�j��0�C�T�O���D�����D(Title)�B�ﶵ����(OptionType)�M�ﶵ���e(Option)�D�šC		
//		List<Question> quList = req.getQuestionList();
//		for(Question qu : quList) { 
//			if(qu.getQuId() <=0 || !StringUtils.hasText(qu.getTitle()) 
//					|| !StringUtils.hasText(qu.getOptionType())|| !StringUtils.hasText(qu.getOption())) { //�o��if�P�_question��
//				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
//			}
//		}
		// �q�L�H�W��Ӫ��ˬd�Y�^��null�N���\�A�S����
		return null;
	}

	
	@Transactional //�p�G��k��������@�����ާ@���ѡA�Ҧ��ާ@�N�|�^�u�A�T�O��Ʈw���@�P�ʡC
	@Override //��s(�ק�w�s�b�����),��1�B:�ˬd�Ѽ� ��2�B:��s�J������ơC�o�O�@�� QuizService ���O���� update ��k�A�Ω��s�{�����ݨ���ơC
	public QuizRes update(QuizReq req) {
//	�Ѽ��ˬd,checkParam(req): �I�s�@�Ӥ�k�A�ˬd�ǤJ�� QuizReq ��H�O�_�ŦX�Y�ǰѼƪ��n�D�CcheckQuestionnaireId(req): �t�@�Ӥ�k�A�Ω�T�{�ǤJ���ݨ� ID �O�_���ġC		
		QuizRes checkResult = checkParam(req); //��W��create�ˬd�ѼƩ��k��checkResult����k�ԤU�ӥ�
		if(checkResult != null) { //
			return checkResult;
		}
		// ��X�ˬdID����k
		checkResult = checkQuestionnaireId(req);
		if(checkResult != null) {
			return checkResult;
		}
//		�ˬd�ݨ��O�_�s�b,��qnDao.findById(req.getQuestionnaire().getId())�d��n��s���ݨ��C�p�G�Ӱݨ�ID���s�b�A�N��^QUESTIONNAIRE_ID_NOT_FOUND��QuizRes����C
		Optional<Questionnaire> qnOp = qnDao.findById(req.getQuestionnaire().getId());
		if(qnOp.isEmpty()) { //�ˬdID�s���s�b,�p�G���s�b�߿��~�T���X�h
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
		}
//		collect deleted_question_id
		List<Integer> deletedQuIdList = new ArrayList<>();
		for(Question qu : req.getDeleteQuestionList()) {
			deletedQuIdList.add(qu.getQuId());
		}
		Questionnaire qn = qnOp.get();
//		�i�H�ק諸����,�P�_��ӱ���:
//		1.�|���o��:is_published == false,�i�H�ק�
//		2.�w�o�����|���}�l�i��:is_published == true + ��e�ɶ������p��start_date
//		�����ˬd:�ˬd�ݨ��O�_�i�H�Q�ק�C��ӥD�n����G
//		�Y�ݨ����o�� (!qn.isPublished())�A�Ϊ̰ݨ��w�o��(qn.isPublished())���|���}�l(LocalDate.now().isBefore(qn.getStartDate()))�C		
		if(!qn.isPublished() || (qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate()))) { //LocalDate.now()��e�ɶ�
			qnDao.save(req.getQuestionnaire()); //�s�x�ݨ���T�C
			quDao.saveAll(req.getQuestionList()); //�s�x�Ҧ��s�K�[�����D�C
//			���h���@�ӧP�_���D�n�����Q���L�i��DB�̭�
//			��s�ާ@:�p�G�ݨ��ŦX�ק����A�N��s�ݨ���T���Ʈw���A�]�A�ݨ�������Ƥά��������D�C��C	
			if(!deletedQuIdList.isEmpty()) {
//				���p�GdeletedQuIdList�����Ū��ܴN�h�R���L,�R���ҿ諸���D�C
				quDao.deleteAllByQnIdAndQuIdIn(qn.getId(), deletedQuIdList); 
			}
//			��^���G:�ھڰ��浲�G��^���P��QuizRes����A�p�G��s���\�A��^SUCCESSFUL�A�_�h��^UPDATE_ERROR�C			
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.UPDATE_ERROR);
	}
		
//	���k,�ˬd�ݨ���ID�O�_�ŦX�S�w������C�o�]�A�ˬd�n�s�W�����D�O�_�P�ݨ������B�n�R�������D�O�_�ݩ�o�i�ݨ����C�p�G�ˬd���ѡA�N��^���������~�X�� QuizRes ����C
	private QuizRes checkQuestionnaireId(QuizReq req) {
//		�ˬdQuizReq���󤤪��ݨ�ID�O�_�p�󵥩�s�C�p�G�O�A��ܰݨ�ID�L�ġA�|��^QUESTIONNAIRE_ID_PARAM_ERROR��QuizRes ����A���ܰ��D�Ҧb�C		
		if(req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
//		������k��:�ˬd�s�W�����D�O�_�P�ݨ�����
//		�ˬdreq�������D�C��QuestionList�A�ýT�O�o�ǰ��D��QnId�]�ݨ� ID�^�Preq�����ݨ�ID�۲šC
//		�p�G������@�Ӱ��D��QnId������ݨ���ID�A�N��o�Ӱ��D�ä��ݩ�Ӱݨ��A�]���|��^QUESTIONNAIRE_ID_PARAM_ERROR��QuizRes����C		
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) {
			if(qu.getQnId() != req.getQuestionnaire().getId()) { //�ˬdQuestion�̭���ID������Questionnaire�̭���ID
				return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
			}
		}
//		������k��:�ˬd�n�R�������D�O�_�ݩ�Ӱݨ�
//		�ˬd�n�R�������D�C��DeleteQuestionList�C���|�T�O�o�ǰ��D��QnId�]�ݨ� ID�^�Preq�����ݨ�ID�۲šC
//		�p�G������@�ӭn�R�������D�� QnId ������ݨ��� ID�A�N��o�Ӱ��D���ݩ�Ӱݨ��A�P�˷|��^ QUESTIONNAIRE_ID_PARAM_ERROR �� QuizRes ����C		
		List<Question> quDelList = req.getDeleteQuestionList();
		if(!quDelList.isEmpty()) {
			for(Question qu : quDelList) {
				if(qu.getQnId() != req.getQuestionnaire().getId()) {
					return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
				}
			}
		}
//		�p�G�Ҧ��ˬd���q�L�A��k�N��^ null�A��ܰݨ�ID�ŦX�Ҧ�����C���p�G����@���ˬd���ѡA�h�|��^���������~�X��QuizRes ����A���ܵo�ͤF������D�C
		return null;
	}

	
	@Override //�R���ݨ��D�ؤΰ��D����k
//	�o�q�{���X�y�z�F�@�ӥΩ�R���ݨ��D�ؤΰ��D����k�C�Ω�R���ݨ��D�ؤΰ��D�C�o�Ӥ�k�����@��List<Integer>qnIdList�ѼƥN��h���A�䤤�]�t�n�R�����ݨ�ID�C��C	
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList) {
//	qnDao.findByIdIn(qnIdList) �Q�ΨӮھڴ��Ѫ��ݨ�ID�C��q��Ʈw���d�߰ݨ�		
		List<Questionnaire> qnList = qnDao.findByIdIn(qnIdList);
		List<Integer> idList = new ArrayList<>();
//	�|�M��qnList�����C�Ӱݨ��A�ˬd�O�_�����H�U����G�ݨ��|���o���Ϊ̤w�g�o�����O��e����p��}�l����C�p�G��������A�Ӱݨ���ID�|�Q�[�J��idList ���C				
		for (Questionnaire qn : qnList) {			   //��e�ɶ��p��}�l���	
			if(!qn.isPublished() || qn.isPublished()&& LocalDate.now().isBefore(qn.getStartDate())) {
//				qnDao.deleteById(qn.getId());
				idList.add(qn.getId());
			}
		}
//		�b�ˬd���Ҧ��ݨ���|�ˬdidList�O�_���šC�p�GidList�O�Ū��A�h��ܨS���ŦX���󪺰ݨ��ݭn�Q�R���C�_�h�A�|����U�����R���ާ@�G
//		qnDao.deleteAllById(idList)�G�o�ӻy�y�|�R���ݨ��D�ءA������idList�����ݨ�ID�C
//		quDao.deleteAllByQnIdIn(idList)�G�o�ӻy�y�|�R���ݨ����D�A������idList�����ݨ�ID�C		
		if(idList.isEmpty()) { //���]idList���O�Ū��~�h���R���h�����
			qnDao.deleteAllById(idList); //�R���ݨ��D��,�i���Ʈw�@���R�h��
			quDao.deleteAllByQnIdIn(idList); //�R���ݨ����D,�@���R�h��
		}
//		�L�׬O�_���ݨ��Q�R���A��k�|��^�@�� QuizRes ����A�èϥ� RtnCode.SUCCESSFUL �@�����\�R�������ѡC		
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	
	@Override //��k�O�Ω�R���ݨ����S�w���D���\�०������ӰѼơGqnId�G�n�R�����D���ݨ�ID�CquIdList�G�n�R�������DID�C��C
	public QuizRes deleteQuestion(int qnId, List<Integer> quIdList) {
		//							   �R�Ĥ@���ݨ�����,123�D
//		�q��Ʈw���d��S�wID���ݨ��C�p�G�Ӱݨ����s�b�A�N�ߧY��^ QuizRes(RtnCode.SUCCESSFUL)�A��ܾާ@���\�C		
		Optional<Questionnaire> qnOP = qnDao.findById(qnId);
		if(qnOP.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		Questionnaire qn = qnOP.get();
//		�p�G�ݨ��s�b�A�{���|�ˬd�ݨ������A�G�p�G�ݨ����o���Ϊ̤w�g�o�����O��e����p��}�l����A�h����H�U�ާ@�GquDao.deleteAllByQnIdAndQuIdIn(qnId, quIdList)�G�o��N�X�|�R���S�w�ݨ������w���DID�����D�C		
		if(!qn.isPublished() || qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate())) {
			quDao.deleteAllByQnIdAndQuIdIn(qnId,quIdList);			
		}
//		�L�׬O�_�����D�Q�R���A�̲׳��|��^�@�� QuizRes ����A�èϥ� RtnCode.SUCCESSFUL �@���ާ@���\�����ѡC		
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	
//	@Cacheable(cacheNames = "search",
//			//key = #title_#startDate_#endDate
//			//key = "test_2023-11-10_2023-11-30"  �������榡�૬�ର�r��ϥ�toString()
//			key = "#title.concat('_').concat(#startDate.toString()).concat('_').concat(#endDate.toString())", //��Ӥ��P���Ѽư��걵concat
//			unless = "#result.rtnCode.code != 200")
	@Override //(�j�M�ݨ�) 
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		//�T���B�⦡,�ݸ����䬰�P�_��,�k��u��true��false�����G,�_�����䬰��true���ɭ�
		//��title�����e�ɪ�^�쥻�����e���L,���M��^�Ŧr��
		title = StringUtils.hasText(title) ? title : ""; //��null�ܬ��Ŧr��
		startDate = startDate != null? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null? endDate : LocalDate.of(2099, 12, 31);
	
//		�ϥ�qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual��k�d�߲ŦX���󪺰ݨ��C��qnList�C�q�o�ǰݨ��������F�ݨ���ID�A�é�JqnIds�C���C		
		List<Questionnaire> qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
		List<Integer> qnIds = new ArrayList<>();
		for(Questionnaire qu : qnList) { 
			qnIds.add(qu.getId());
		}
//		�ھڰݨ��� ID �d�߬����p�����D�C�� quList�G�ϥ� quDao.findAllByQnIdIn(qnIds) �d�ߥX�����p�����D�C��C		
		List<Question> quList = quDao.findAllByQnIdIn(qnIds);
//		�إߤF�@�� quizVoList �C��A�䤤�]�t�F�C�Ӱݨ��Ψ�����p�����D�G�i��ݨ��P���D���ǰt�A�N���D�P�������ݨ��i��t��A�إߤFQuizVo��H�C		
		List<QuizVo> quizVoList = new ArrayList<>();
//		�N�ݨ�����T�s�JQuizVo��H����questionnaire�ݩʡC
//		�N�ŦX�ۦP�ݨ� ID �����D��J QuizVo ��H���� questionList �ݩʡC		
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
//		�̫�A�N�إߪ�QuizVo��H��JquizVoList���A�ñNquizVoList�@�����G��^�A�P�ɪ�^RtnCode.SUCCESSFUL��ܷj�M���\�C
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

	
	@Override //�j�M�ݨ��C����k�C
//	�����X�ӰѼơGtitle�G�ݨ����D����r�A�Ω�j���ŦX���D���ݨ��CstartDate�MendDate�G�ݨ��}�l�M����������z�����A�Ω󭭨�ݨ��b�S�w����d�򤺡CisAll�G�@�ӥ��L�ȡA�Ω���ѬO�_��^�Ҧ��ŦX���󪺰ݨ��C
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, 
			LocalDate endDate,boolean isAll) {
//		��StringUtils.hasText��k�ˬdtitle�O�_���šC�p�Gtitle���šA�h�N��]�m���Ŧr��C		
		title = StringUtils.hasText(title) ? title : "";
//		�ˬdstartDate�MendDate�O�_�� null�C�p�G���̬Onull�A�h�N���̳]�m��1971�~1��1��M2099�~12��31��A�o�˴N���@���q�{������d��C		
		startDate = startDate != null? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null? endDate : LocalDate.of(2099, 12, 31);
//		�ھ� isAll ���ȡA�{���X�ϥΤ��P���d�ߤ�k�q��Ʈw������ݨ��C��G
//		�p�G isAll �� false�A�h�ե� qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue ��k�A�Ӥ�k�ھڼ��D�B�}�l����B��������M�w�o�������A�j���ݨ��C��C
//		�p�G isAll �� true�A�h�ե� qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual ��k�A�Ӥ�k�ھڼ��D�B�}�l����M��������j���ݨ��C��A�Ӥ��Ҽ{�O�_�w�o���C		
		List<Questionnaire> qnList = new ArrayList<>();
			if(!isAll) {
				qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(title, startDate, endDate);
			}else {
				qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
			}
//		�̫�A�N�d�ߨ쪺�ݨ��C��ʸ˨�QuestionnaireRes���󤤡A�èϥ�RtnCode.SUCCESSFUL��ܾާ@���\�A��^���ϥΪ̡C			
		return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
	}

	
	@Override //searchQuestionList��k�Ω�d��S�w�ݨ������D���C��C�o�Ӥ�k�����@�ӰѼ� qnId�A�N��n�j�����ݨ�ID�C
	public QuestionRes searchQuestionList(int qnId) {
//		�ˬd�A�T�OqnId�j��s�C�p�GqnId�p�󵥩�s�A�N��Ӱݨ�ID�L�ġA��O������^�@��QuestionRes����A��]�t�F���~�X 		
		if(qnId <= 0 ) {
			return new QuestionRes(null,RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
//		�p�GqnId���ġA�h�ϥ�quDao.findAllByQnIdIn(Arrays.asList(qnId)) �q��Ʈw�����S�wqnId�U�����D�C��C		
//		�N���D�C��ʸ˨� QuestionRes ���󤤡A�èϥ� RtnCode.SUCCESSFUL �@���ާ@���\�����ѡA��^���եΪ̡C		
		List<Question> quList = quDao.findAllByQnIdIn(Arrays.asList(qnId));
		return new QuestionRes(quList,RtnCode.SUCCESSFUL);
	}

//==================================SQL search======================================================//	
	@Override
	public QuizRes searchFuzzy(String title, LocalDate startDate, LocalDate endDate) {
		List<QnQuVo> res = qnDao.selectFuzzy("title",startDate,endDate);
		return new QuizRes(null,RtnCode.SUCCESSFUL,res);
	}
		
}
