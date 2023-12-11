package com.example.questionnaire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QnQuVo;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;

@SpringBootTest
class QuizServiceTest {

	@Autowired
	private QuizService service;

	@Autowired
	private QuestionnaireDao qnDao;

	@Autowired
	private QuestionDao qDao;

	public void createTest() {
//		���ЫؤF�@�Ӱݨ���H questionnaire�A�ë��w�F�ݨ��������ݩʡA�p�ݨ����D�B�y�z�B�o�����A�M�ɶ��d�򵥡C
		Questionnaire questionnaire = new Questionnaire(1, "test1", "test", false, LocalDate.of(2023, 11, 17),
				LocalDate.of(2023, 11, 30));
//		�ЫؤF�@�Ӱ��D�C�� questionList�A�䤤�]�t�F�T�Ӱ��D��H q1�Bq2 �M q3�C�C�Ӱ��D�������P���ݩʡA�Ҧp���D���D�B�����B�ﶵ���C		
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1, 1, "test_question_1", "single", false, "AAA;BBB;CCC");
		Question q2 = new Question(2, 1, "test_question_2", "multi", false, "10;20;30;40");
		Question q3 = new Question(3, 1, "test_question_3", "text", false, "ABC");
		questionList.addAll(Arrays.asList(q1, q2, q3));

//		�N�Ыت��ݨ���H�M���D�C��ʸ˨� QuizReq��Hreq ���C		
		QuizReq req = new QuizReq(questionnaire, questionList);
//		�ϥ�service��create ��k����Ыؾާ@�A�ñN���G�s�x�b res ���C		
		QuizRes res = service.create(req);
//		���ըϥΤF Assert.isTrue() �_����k�A�ˬd res ������^�N�X�O�_���� 200�C�p�G���O�A�h�|���"create error!"�C		
		Assert.isTrue(res.getRtncode().getCode() == 200, "create error!");
	}

	public void updateTest() {
//		���~
//		��諸�ɾ� �G ���o��+�w�o���|���}�l ( ��e�ɶ� < �}�l�ɶ� )
//		�w�o���w�}�l�����
//		qn�Gid�Btitle���~�B�������~�B�}�l�ɶ��B�����ɶ��B�}�l>����
//		qu�Gid�Bquid == id �B qtitle�B�ﶵ�����B�ﶵ����	
// 		�w�o���w�}�l		
//		���ռ��D���ର�Ŧr��
		Questionnaire questionnaire = new Questionnaire(1, "", "test", false, LocalDate.of(2023, 11, 17),
				LocalDate.of(2023, 11, 30));
//		�ЫؤF�@�Ӱ��D�C�� questionList�A�䤤�]�t�F�T�Ӱ��D��H q1�Bq2 �M q3�C�C�Ӱ��D�������P���ݩʡA�Ҧp���D���D�B�����B�ﶵ���C		
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1, 1, "test_question_1", "single", false, "AAA;BBB;CCC");
		Question q2 = new Question(2, 1, "test_question_2", "multi", false, "10;20;30;40");
		Question q3 = new Question(3, 1, "test_question_3", "text", false, "ABC");
		questionList.addAll(Arrays.asList(q1, q2, q3));
		QuizReq req = new QuizReq(questionnaire, questionList);
		QuizRes res = service.update(req);
		// ���ռ��D���ର�Ŧr��
		Assert.isTrue(res.getRtncode().getCode() != 200, "title error!!");
		// ����Id not fount
		questionnaire = new Questionnaire(50, "test1", "test", false, LocalDate.of(2023, 11, 17),
				LocalDate.of(2023, 11, 30));
		;
		// ���է������G��result�o�ӰѼƥh��,�A�N���X�Ӫ����G
		QuizRes result = service.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtncode().getCode() != 200, "Id error!!");

	}

	public void deleteQuestionnaireTest() {
		// �R�h�i�ݨ� �u�� �|���o�� + �|���}��(��e��� < �}�l���) �i�R
		// �o�G�N���i�R
		// 1. �w�o�����}�� 2.�w�o���w�}��
		// ���@��int ��List

		// �R����Ʈw�̨S����
		// �R���h����List,�b�Τ@���ܼ�deleteList���n�R���ݨ�id,�Y�S��()�����ݨ�id,�N�^�_id not found
		List<Integer> deleteList = new ArrayList<>(Arrays.asList(26, 27));
		QuizRes result = service.deleteQuestionnaire(deleteList);
		Assert.isTrue(result.getRtncode() != RtnCode.SUCCESSFUL, "id not found");
		// �R����Ʈw�� �w�}��
		deleteList = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		result = service.deleteQuestion(1, deleteList);
		Assert.isTrue(result.getRtncode() != RtnCode.SUCCESSFUL, "id not found");
	}

	public void deleteQuestionTest() {
		// �n�R���ݨ� int deleqnid �n�R���D�� List<int> delequid

		// �R���ݨ��S�����D��
		int deleteQnId = 26;
		ArrayList<Integer> deleteQuIdList = new ArrayList<Integer>(Arrays.asList(1, 5));
		QuizRes result = service.deleteQuestion(deleteQnId, deleteQuIdList);

	}

//==================================================================================//	
//	SQL�y�k�m��
	// insert�y�k
	public void insertData() {
		int res = qnDao.insert("qn_01", "qa_03 test", false, LocalDate.of(2023, 11, 24), LocalDate.of(2024, 01, 01));
		System.out.println(res); // �L�X1 �����O�s�W�@����Ʀ��\
	}

	// insert�y�k
	public void insertTest1() {
		int res = qnDao.insert("�ί�", "���@", false, LocalDate.of(2023, 11, 30), LocalDate.of(2024, 01, 01));
		System.out.println(res); // �L�X1 �����O�s�W�@����Ʀ��\
	}

	// SQL update�y�k
	public void updateTest1() {
		int res = qnDao.update(5, "qn_007", "qn_007_Test");
		System.out.println(res);
	}

	// SQL update�y�k
	public void updateDateTest() {
		int res = qnDao.updateDate(4, "qn_099", "qn_097_Test", LocalDate.of(2023, 11, 24));
		System.out.println(res);
	}

	// select
	public void selectTest() {
//		List<Questionnaire> res = qnDao.findByStartDate(LocalDate.of(2023, 11, 20)) ;
//		List<Questionnaire> res = qnDao.findByStartDate1(LocalDate.of(2023, 11, 20)) ;
//		List<Questionnaire> res = qnDao.findByStartDate2(LocalDate.of(2023, 11, 20)) ;
//		List<Questionnaire> res = qnDao.findByStartDate3(LocalDate.of(2023, 11, 20),true) ;
//		List<Questionnaire> res = qnDao.findByStartDate4(LocalDate.of(2023, 11, 20),true,2) ;
		List<Questionnaire> res = qnDao.findByStartDate5(LocalDate.of(2023, 11, 20), true, 2);
		System.out.println(res.size());
	}

	public void limitTest() {
		List<Questionnaire> res = qnDao.findWithLimitAndStartIndex(0, 3);
		for (Questionnaire item : res) {
			System.out.println(item.getId());
		}
//		res.forEach(item -> {				//
//			System.out.println(item.getId());
//		});
	}

	public void likeTest() {
		List<Questionnaire> res = qnDao.searchTitleLike("test");
		System.out.println(res.size());
	}

	public void regexpTest() {
		List<Questionnaire> res = qnDao.searchDescriptionContaining("qa", "qn");
		for (Questionnaire item : res) {
			System.out.println(item.getId());
		}
	}

//===================================join===============================================//	

	public void joinTest() {
		List<QnQuVo> res = qnDao.selectJoinQnQu();
		for (QnQuVo item : res) {
			System.out.printf("id: %d, title: %S, qu_id: %d \n", item.getId(), item.getTitle(), item.getQuId());
		}
	}

	public void selectFuzzyTest() {
		QuizRes res = service.searchFuzzy("test", LocalDate.of(1971, 1, 1), LocalDate.of(2099, 1, 1));
		System.out.println(res.getQnquVoList().size());
	}

	@Test
	public void searchQuizsTest() {
		// ���S��
		List<Questionnaire> qnList = new ArrayList<>();
		qnList = service.searchQuizs(null, null, null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("���S��: " + q.getId());
		}

		// �u�����D
		qnList = service.searchQuizs("1", null, null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("�u�����D: " + q.getId());
		}
		// �u���}�l
		qnList = service.searchQuizs(null, LocalDate.of(2023, 11, 25), null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("�}�l: " + q.getId());
		}
		// �u������
		qnList = service.searchQuizs(null, null, LocalDate.of(2023, 12, 30)).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("����: " + q.getId());
		}
		// ���D+�}�l
		qnList = service.searchQuizs("1", LocalDate.of(2023, 11, 25), null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("���D+�}�l: " + q.getId());
		}
		// ���D+����
		qnList = service.searchQuizs("1", null, LocalDate.of(2023, 12, 30)).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("���D+����: " + q.getId());
		}
		// �}�l+����
		qnList = service.searchQuizs(null, LocalDate.of(2023, 11, 25), LocalDate.of(2023, 12, 30))
				.getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("�}�l+����: " + q.getId());
		}
		// ����
		qnList = service.searchQuizs("1", LocalDate.of(2023, 11, 25), LocalDate.of(2023, 12, 30))
				.getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("����: " + q.getId());
		}
	}

}
