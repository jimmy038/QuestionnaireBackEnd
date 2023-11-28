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
	/*qn為Questionnaire,qu為Question*/
	
	@Autowired
	private QuestionnaireDao qnDao; //主要要呼叫Dao,操作與資料庫的連結,縮寫qnDao
	
	@Autowired
	private QuestionDao quDao; 		//主要要呼叫Dao,操作與資料庫的連結,縮寫quDao

	//用在要將兩張表同時存進資料庫時
	@Transactional //@Transactional(交易),當2個save都能成功save的時候才會save，只能加在public 上面
	@Override 	   
	public QuizRes create(QuizReq req) { 
//		↓(抽方法)先寫方法名稱再帶入實作。(抽方法)將一段的邏輯跟檢查的方法有共用的部分抽出來做使用	
		QuizRes checkResult = checkParam(req); //檢查參數,呼叫下面checkParam的方法
		if(checkResult != null) { //假設checkResult的方法不等於null時回傳回去
			return checkResult;   //回傳checkResult回去
		}
		// 儲存後，把 QN 中最新一筆的ID拉出來，存到QU的qn_id中，
		int qnId = qnDao.save(req.getQuestionnaire()).getId();//要接回來因為Questionnaire裡面的qid才會出來
		List<Question> quList = req.getQuestionList();
		
		// 可以只新增問卷，問卷內沒有題目
//		if(quList.isEmpty()) { //如果quList不為空時回傳SUCCESSFUL成功的訊息
//			System.out.println("fail: "+quList);
//			return new QuizRes(RtnCode.SUCCESSFUL);
//		}
		for(Question qu : quList) {
			qu.setQnId(qnId); //設定問卷的ID對應到問卷問題的ID使其對應到該題目
		}
		quDao.saveAll(quList);
		System.out.println("SUCCESSFUL: "+ quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
		//若為null就是通過下面checkParam方法的兩個的檢查
		
	}

	//↓這部分實作從QuizRes來的參數檢查,檢查4個參數,參數從QuizRes來的,參數不能為空,檢查這4個標題.輸入.開始及結束日期
	private QuizRes checkParam(QuizReq req) { //這個方法主要檢查參數作用,這個方法回傳RES或是null 
//		Questionnaire qn = req.getQuestionnaire();
////		確保問卷標題(Title)和描述(Description)非空。確保問卷的開始日期(StartDate)和結束日期(EndDate)不為空且合理，並且開始日期不晚於結束日期。		
//		if(!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription())
//				|| qn.getStartDate() == null || qn.getEndDate() == null
//				|| qn.getStartDate().isAfter(qn.getEndDate())) {  //A是否在B之後(開始時間在結束時間之後 為false)
//			return new QuizRes(RtnCode.QUESTIONNAIRE_PARAM_ERROR);//如果QUESTIONNAIRE這張表錯就不會檢查到第二張
//		}
////		對於每個問題，確保問題的ID(QuId)大於0。確保問題的標題(Title)、選項類型(OptionType)和選項內容(Option)非空。		
//		List<Question> quList = req.getQuestionList();
//		for(Question qu : quList) { 
//			if(qu.getQuId() <=0 || !StringUtils.hasText(qu.getTitle()) 
//					|| !StringUtils.hasText(qu.getOptionType())|| !StringUtils.hasText(qu.getOption())) { //這邊if判斷question的
//				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
//			}
//		}
		// 通過以上兩個的檢查若回傳null代表成功，沒有錯
		return null;
	}

	
	@Transactional //如果方法中的任何一部分操作失敗，所有操作將會回滾，確保資料庫的一致性。
	@Override //更新(修改已存在的資料),第1步:檢查參數 第2步:更新既有的資料。這是一個 QuizService 類別中的 update 方法，用於更新現有的問卷資料。
	public QuizRes update(QuizReq req) {
//	參數檢查,checkParam(req): 呼叫一個方法，檢查傳入的 QuizReq 對象是否符合某些參數的要求。checkQuestionnaireId(req): 另一個方法，用於確認傳入的問卷 ID 是否有效。		
		QuizRes checkResult = checkParam(req); //把上面create檢查參數抽方法的checkResult的方法拉下來用
		if(checkResult != null) { //
			return checkResult;
		}
		// 抽出檢查ID的方法
		checkResult = checkQuestionnaireId(req);
		if(checkResult != null) {
			return checkResult;
		}
//		檢查問卷是否存在,用qnDao.findById(req.getQuestionnaire().getId())查找要更新的問卷。如果該問卷ID不存在，將返回QUESTIONNAIRE_ID_NOT_FOUND的QuizRes物件。
		Optional<Questionnaire> qnOp = qnDao.findById(req.getQuestionnaire().getId());
		if(qnOp.isEmpty()) { //檢查ID存不存在,如果不存在拋錯誤訊息出去
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
		}
//		collect deleted_question_id
		List<Integer> deletedQuIdList = new ArrayList<>();
		for(Question qu : req.getDeleteQuestionList()) {
			deletedQuIdList.add(qu.getQuId());
		}
		Questionnaire qn = qnOp.get();
//		可以修改的條件,判斷兩個條件:
//		1.尚未發布:is_published == false,可以修改
//		2.已發布但尚未開始進行:is_published == true + 當前時間必須小於start_date
//		條件檢查:檢查問卷是否可以被修改。兩個主要條件：
//		若問卷未發布 (!qn.isPublished())，或者問卷已發布(qn.isPublished())但尚未開始(LocalDate.now().isBefore(qn.getStartDate()))。		
		if(!qn.isPublished() || (qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate()))) { //LocalDate.now()當前時間
			qnDao.save(req.getQuestionnaire()); //存儲問卷資訊。
			quDao.saveAll(req.getQuestionList()); //存儲所有新添加的問題。
//			↓多做一個判斷式主要為不想讓他進到DB裡面
//			更新操作:如果問卷符合修改條件，將更新問卷資訊到資料庫中，包括問卷本身資料及相關的問題列表。	
			if(!deletedQuIdList.isEmpty()) {
//				↓如果deletedQuIdList不為空的話就去刪除他,刪除所選的問題。
				quDao.deleteAllByQnIdAndQuIdIn(qn.getId(), deletedQuIdList); 
			}
//			返回結果:根據執行結果返回不同的QuizRes物件，如果更新成功，返回SUCCESSFUL，否則返回UPDATE_ERROR。			
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.UPDATE_ERROR);
	}
		
//	抽方法,檢查問卷的ID是否符合特定的條件。這包括檢查要新增的問題是否與問卷相關、要刪除的問題是否屬於這張問卷等。如果檢查失敗，將返回對應的錯誤碼的 QuizRes 物件。
	private QuizRes checkQuestionnaireId(QuizReq req) {
//		檢查QuizReq物件中的問卷ID是否小於等於零。如果是，表示問卷ID無效，會返回QUESTIONNAIRE_ID_PARAM_ERROR的QuizRes 物件，指示問題所在。		
		if(req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
//		↓此方法為:檢查新增的問題是否與問卷相關
//		檢查req中的問題列表QuestionList，並確保這些問題的QnId（問卷 ID）與req中的問卷ID相符。
//		如果有任何一個問題的QnId不等於問卷的ID，代表這個問題並不屬於該問卷，因此會返回QUESTIONNAIRE_ID_PARAM_ERROR的QuizRes物件。		
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) {
			if(qu.getQnId() != req.getQuestionnaire().getId()) { //檢查Question裡面的ID不等於Questionnaire裡面的ID
				return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
			}
		}
//		↓此方法為:檢查要刪除的問題是否屬於該問卷
//		檢查要刪除的問題列表DeleteQuestionList。它會確保這些問題的QnId（問卷 ID）與req中的問卷ID相符。
//		如果有任何一個要刪除的問題的 QnId 不等於問卷的 ID，代表這個問題不屬於該問卷，同樣會返回 QUESTIONNAIRE_ID_PARAM_ERROR 的 QuizRes 物件。		
		List<Question> quDelList = req.getDeleteQuestionList();
		if(!quDelList.isEmpty()) {
			for(Question qu : quDelList) {
				if(qu.getQnId() != req.getQuestionnaire().getId()) {
					return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
				}
			}
		}
//		如果所有檢查都通過，方法將返回 null，表示問卷ID符合所有條件。但如果任何一個檢查失敗，則會返回對應的錯誤碼的QuizRes 物件，指示發生了什麼問題。
		return null;
	}

	
	@Override //刪除問卷題目及問題的方法
//	這段程式碼描述了一個用於刪除問卷題目及問題的方法。用於刪除問卷題目及問題。這個方法接受一個List<Integer>qnIdList參數代表多筆，其中包含要刪除的問卷ID列表。	
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList) {
//	qnDao.findByIdIn(qnIdList) 被用來根據提供的問卷ID列表從資料庫中查詢問卷		
		List<Questionnaire> qnList = qnDao.findByIdIn(qnIdList);
		List<Integer> idList = new ArrayList<>();
//	會遍歷qnList中的每個問卷，檢查是否滿足以下條件：問卷尚未發布或者已經發布但是當前日期小於開始日期。如果滿足條件，該問卷的ID會被加入到idList 中。				
		for (Questionnaire qn : qnList) {			   //當前時間小於開始日期	
			if(!qn.isPublished() || qn.isPublished()&& LocalDate.now().isBefore(qn.getStartDate())) {
//				qnDao.deleteById(qn.getId());
				idList.add(qn.getId());
			}
		}
//		在檢查完所有問卷後會檢查idList是否為空。如果idList是空的，則表示沒有符合條件的問卷需要被刪除。否則，會執行下面的刪除操作：
//		qnDao.deleteAllById(idList)：這個語句會刪除問卷題目，對應著idList中的問卷ID。
//		quDao.deleteAllByQnIdIn(idList)：這個語句會刪除問卷問題，對應著idList中的問卷ID。		
		if(idList.isEmpty()) { //假設idList不是空的才去做刪除多筆資料
			qnDao.deleteAllById(idList); //刪除問卷題目,進到資料庫一次刪多筆
			quDao.deleteAllByQnIdIn(idList); //刪除問卷問題,一次刪多筆
		}
//		無論是否有問卷被刪除，方法會返回一個 QuizRes 物件，並使用 RtnCode.SUCCESSFUL 作為成功刪除的標識。		
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	
	@Override //方法是用於刪除問卷內特定問題的功能它接受兩個參數：qnId：要刪除問題的問卷ID。quIdList：要刪除的問題ID列表。
	public QuizRes deleteQuestion(int qnId, List<Integer> quIdList) {
		//							   刪第一章問卷內的,123題
//		從資料庫中查找特定ID的問卷。如果該問卷不存在，將立即返回 QuizRes(RtnCode.SUCCESSFUL)，表示操作成功。		
		Optional<Questionnaire> qnOP = qnDao.findById(qnId);
		if(qnOP.isEmpty()) {
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		Questionnaire qn = qnOP.get();
//		如果問卷存在，程式會檢查問卷的狀態：如果問卷未發布或者已經發布但是當前日期小於開始日期，則執行以下操作：quDao.deleteAllByQnIdAndQuIdIn(qnId, quIdList)：這行代碼會刪除特定問卷中指定問題ID的問題。		
		if(!qn.isPublished() || qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate())) {
			quDao.deleteAllByQnIdAndQuIdIn(qnId,quIdList);			
		}
//		無論是否有問題被刪除，最終都會返回一個 QuizRes 物件，並使用 RtnCode.SUCCESSFUL 作為操作成功的標識。		
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	
//	@Cacheable(cacheNames = "search",
//			//key = #title_#startDate_#endDate
//			//key = "test_2023-11-10_2023-11-30"  ↓把日期格式轉型轉為字串使用toString()
//			key = "#title.concat('_').concat(#startDate.toString()).concat('_').concat(#endDate.toString())", //兩個不同的參數做串接concat
//			unless = "#result.rtnCode.code != 200")
	@Override //(搜尋問卷) 
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		//三元運算式,問號左邊為判斷式,右邊只有true跟false的結果,冒號左邊為當true的時候
		//當title有內容時返回原本的內容給他,不然返回空字串
		title = StringUtils.hasText(title) ? title : ""; //把null變為空字串
		startDate = startDate != null? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null? endDate : LocalDate.of(2099, 12, 31);
	
//		使用qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual方法查詢符合條件的問卷列表qnList。從這些問卷中提取了問卷的ID，並放入qnIds列表中。		
		List<Questionnaire> qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
		List<Integer> qnIds = new ArrayList<>();
		for(Questionnaire qu : qnList) { 
			qnIds.add(qu.getId());
		}
//		根據問卷的 ID 查詢相關聯的問題列表 quList：使用 quDao.findAllByQnIdIn(qnIds) 查詢出相關聯的問題列表。		
		List<Question> quList = quDao.findAllByQnIdIn(qnIds);
//		建立了一個 quizVoList 列表，其中包含了每個問卷及其相關聯的問題：進行問卷與問題的匹配，將問題與對應的問卷進行配對，建立了QuizVo對象。		
		List<QuizVo> quizVoList = new ArrayList<>();
//		將問卷的資訊存入QuizVo對象中的questionnaire屬性。
//		將符合相同問卷 ID 的問題放入 QuizVo 對象中的 questionList 屬性。		
		for(Questionnaire qn : qnList) { 
			QuizVo vo = new QuizVo();/*一個Vo代表一個問卷跟問卷內的題目*/
			vo.setQuestionnaire(qn);//裝問卷名稱及描述
			List<Question> questionList = new ArrayList<>();
			for(Question qu : quList) {
				if(qu.getQnId() == qn.getId()) { //讓兩張迴圈配對使用if,抓他們的ID配對
					questionList.add(qu); 		 //當條件符合時把問題加入問卷內
				}
			}
			vo.setQuestionList(questionList); //
			quizVoList.add(vo); //把做完的vo放進List裡面
		}
//		最後，將建立的QuizVo對象放入quizVoList中，並將quizVoList作為結果返回，同時返回RtnCode.SUCCESSFUL表示搜尋成功。
		return new QuizRes(quizVoList, RtnCode.SUCCESSFUL);
	}
	
	//概念與上面三元運算式相同↑
//	if(!StringUtils.hasText(title)) { //假設title為null,空字串,空白,時
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

	
	@Override //搜尋問卷列表的方法。
//	接受幾個參數：title：問卷標題關鍵字，用於搜索符合標題的問卷。startDate和endDate：問卷開始和結束日期的篩選條件，用於限制問卷在特定日期範圍內。isAll：一個布林值，用於標識是否返回所有符合條件的問卷。
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, 
			LocalDate endDate,boolean isAll) {
//		用StringUtils.hasText方法檢查title是否為空。如果title為空，則將其設置為空字串。		
		title = StringUtils.hasText(title) ? title : "";
//		檢查startDate和endDate是否為 null。如果它們是null，則將它們設置為1971年1月1日和2099年12月31日，這樣就有一個默認的日期範圍。		
		startDate = startDate != null? startDate : LocalDate.of(1971, 1, 1);
		endDate = endDate != null? endDate : LocalDate.of(2099, 12, 31);
//		根據 isAll 的值，程式碼使用不同的查詢方法從資料庫中獲取問卷列表：
//		如果 isAll 為 false，則調用 qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue 方法，該方法根據標題、開始日期、結束日期和已發布的狀態搜索問卷列表。
//		如果 isAll 為 true，則調用 qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual 方法，該方法根據標題、開始日期和結束日期搜索問卷列表，而不考慮是否已發布。		
		List<Questionnaire> qnList = new ArrayList<>();
			if(!isAll) {
				qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(title, startDate, endDate);
			}else {
				qnList = qnDao.findByTitleContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(title, startDate, endDate);
			}
//		最後，將查詢到的問卷列表封裝到QuestionnaireRes物件中，並使用RtnCode.SUCCESSFUL表示操作成功，返回給使用者。			
		return new QuestionnaireRes(qnList,RtnCode.SUCCESSFUL);
	}

	
	@Override //searchQuestionList方法用於查找特定問卷內問題的列表。這個方法接受一個參數 qnId，代表要搜索的問卷ID。
	public QuestionRes searchQuestionList(int qnId) {
//		檢查，確保qnId大於零。如果qnId小於等於零，代表該問卷ID無效，於是直接返回一個QuestionRes物件，其包含了錯誤碼 		
		if(qnId <= 0 ) {
			return new QuestionRes(null,RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
//		如果qnId有效，則使用quDao.findAllByQnIdIn(Arrays.asList(qnId)) 從資料庫中找到特定qnId下的問題列表。		
//		將問題列表封裝到 QuestionRes 物件中，並使用 RtnCode.SUCCESSFUL 作為操作成功的標識，返回給調用者。		
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
