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
//		先創建了一個問卷對象 questionnaire，並指定了問卷的相關屬性，如問卷標題、描述、發布狀態和時間範圍等。
		Questionnaire questionnaire = new Questionnaire(1, "test1", "test", false, LocalDate.of(2023, 11, 17),
				LocalDate.of(2023, 11, 30));
//		創建了一個問題列表 questionList，其中包含了三個問題對象 q1、q2 和 q3。每個問題都有不同的屬性，例如問題標題、類型、選項等。		
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1, 1, "test_question_1", "single", false, "AAA;BBB;CCC");
		Question q2 = new Question(2, 1, "test_question_2", "multi", false, "10;20;30;40");
		Question q3 = new Question(3, 1, "test_question_3", "text", false, "ABC");
		questionList.addAll(Arrays.asList(q1, q2, q3));

//		將創建的問卷對象和問題列表封裝到 QuizReq對象req 中。		
		QuizReq req = new QuizReq(questionnaire, questionList);
//		使用service的create 方法執行創建操作，並將結果存儲在 res 中。		
		QuizRes res = service.create(req);
//		測試使用了 Assert.isTrue() 斷言方法，檢查 res 中的返回代碼是否等於 200。如果不是，則會顯示"create error!"。		
		Assert.isTrue(res.getRtncode().getCode() == 200, "create error!");
	}

	public void updateTest() {
//		錯誤
//		能改的時機 ： 未發布+已發布尚未開始 ( 當前時間 < 開始時間 )
//		已發布已開始不能改
//		qn：id、title錯誤、說明錯誤、開始時間、結束時間、開始>結束
//		qu：id、quid == id 、 qtitle、選項類型、選項說明	
// 		已發布已開始		
//		測試標題不能為空字串
		Questionnaire questionnaire = new Questionnaire(1, "", "test", false, LocalDate.of(2023, 11, 17),
				LocalDate.of(2023, 11, 30));
//		創建了一個問題列表 questionList，其中包含了三個問題對象 q1、q2 和 q3。每個問題都有不同的屬性，例如問題標題、類型、選項等。		
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1, 1, "test_question_1", "single", false, "AAA;BBB;CCC");
		Question q2 = new Question(2, 1, "test_question_2", "multi", false, "10;20;30;40");
		Question q3 = new Question(3, 1, "test_question_3", "text", false, "ABC");
		questionList.addAll(Arrays.asList(q1, q2, q3));
		QuizReq req = new QuizReq(questionnaire, questionList);
		QuizRes res = service.update(req);
		// 測試標題不能為空字串
		Assert.isTrue(res.getRtncode().getCode() != 200, "title error!!");
		// 測試Id not fount
		questionnaire = new Questionnaire(50, "test1", "test", false, LocalDate.of(2023, 11, 17),
				LocalDate.of(2023, 11, 30));
		;
		// 測試完的結果用result這個參數去接,再將接出來的結果
		QuizRes result = service.update(new QuizReq(questionnaire, questionList));
		Assert.isTrue(result.getRtncode().getCode() != 200, "Id error!!");

	}

	public void deleteQuestionnaireTest() {
		// 刪多張問卷 只有 尚未發布 + 尚未開放(當前日期 < 開始日期) 可刪
		// 發佈就不可刪
		// 1. 已發布未開放 2.已發布已開放
		// 給一個int 的List

		// 刪除資料庫裡沒有的
		// 刪除多筆用List,在用一個變數deleteList接要刪除問卷id,若沒有()內的問卷id,就回復id not found
		List<Integer> deleteList = new ArrayList<>(Arrays.asList(26, 27));
		QuizRes result = service.deleteQuestionnaire(deleteList);
		Assert.isTrue(result.getRtncode() != RtnCode.SUCCESSFUL, "id not found");
		// 刪除資料庫裡 已開放的
		deleteList = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		result = service.deleteQuestion(1, deleteList);
		Assert.isTrue(result.getRtncode() != RtnCode.SUCCESSFUL, "id not found");
	}

	public void deleteQuestionTest() {
		// 要刪的問卷 int deleqnid 要刪的題目 List<int> delequid

		// 刪除問卷沒有的題目
		int deleteQnId = 26;
		ArrayList<Integer> deleteQuIdList = new ArrayList<Integer>(Arrays.asList(1, 5));
		QuizRes result = service.deleteQuestion(deleteQnId, deleteQuIdList);

	}

//==================================================================================//	
//	SQL語法練習
	// insert語法
	public void insertData() {
		int res = qnDao.insert("qn_01", "qa_03 test", false, LocalDate.of(2023, 11, 24), LocalDate.of(2024, 01, 01));
		System.out.println(res); // 印出1 指的是新增一筆資料成功
	}

	// insert語法
	public void insertTest1() {
		int res = qnDao.insert("統神", "神罰", false, LocalDate.of(2023, 11, 30), LocalDate.of(2024, 01, 01));
		System.out.println(res); // 印出1 指的是新增一筆資料成功
	}

	// SQL update語法
	public void updateTest1() {
		int res = qnDao.update(5, "qn_007", "qn_007_Test");
		System.out.println(res);
	}

	// SQL update語法
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
		// 都沒有
		List<Questionnaire> qnList = new ArrayList<>();
		qnList = service.searchQuizs(null, null, null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("都沒有: " + q.getId());
		}

		// 只有標題
		qnList = service.searchQuizs("1", null, null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("只有標題: " + q.getId());
		}
		// 只有開始
		qnList = service.searchQuizs(null, LocalDate.of(2023, 11, 25), null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("開始: " + q.getId());
		}
		// 只有結束
		qnList = service.searchQuizs(null, null, LocalDate.of(2023, 12, 30)).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("結束: " + q.getId());
		}
		// 標題+開始
		qnList = service.searchQuizs("1", LocalDate.of(2023, 11, 25), null).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("標題+開始: " + q.getId());
		}
		// 標題+結束
		qnList = service.searchQuizs("1", null, LocalDate.of(2023, 12, 30)).getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("標題+結束: " + q.getId());
		}
		// 開始+結束
		qnList = service.searchQuizs(null, LocalDate.of(2023, 11, 25), LocalDate.of(2023, 12, 30))
				.getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("開始+結束: " + q.getId());
		}
		// 都有
		qnList = service.searchQuizs("1", LocalDate.of(2023, 11, 25), LocalDate.of(2023, 12, 30))
				.getQuestionnaireList();
		for (Questionnaire q : qnList) {
			System.out.println("都有: " + q.getId());
		}
	}

}
