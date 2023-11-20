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
	/*qn為Questionnaire,qu為Question*/
	
	@Autowired
	private QuestionnaireDao qnDao; //縮寫qnDao
	
	@Autowired
	private QuestionDao quDao; //縮寫quDao

	//用在若要將兩張表同時存進資料庫時
	@Transactional //@Transactional(交易),不能寫在private上面
	@Override //↓(抽方法)先寫方法名稱再帶入實作。(抽方法)將一段的邏輯跟檢查的方法放進
	public QuizRes create(QuizReq req) { 
		QuizRes checkResult = checkParam(req); //檢查參數,呼叫下面的方法
		if(checkResult != null) { //假設checkResult的方法不等於null時回傳回去
			return checkResult;
		}
		int quId = qnDao.save(req.getQuestionnaire()).getId();//要接回來因為Questionnaire裡面的qid才會出來
		List<Question> quList = req.getQuestionList();
		if(quList.isEmpty()) { //如果List不為空
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		for(Question qu : quList) {
			qu.setQnId(quId);
		}
		quDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
		//若為null就是通過下面兩個的檢查
	}

	//↓這部分做參數判斷,檢查4個參數,參數從QuizRes來的,參數不能為空,檢查這4個標題.輸入.開始及結束日期
	private QuizRes checkParam(QuizReq req) { //這個方法主要檢查參數作用,這個方法回傳RES或是null 
		Questionnaire qn = req.getQuestionnaire();
		if(!StringUtils.hasText(qn.getTitle()) || !StringUtils.hasText(qn.getDescription())
				|| qn.getStartDate() == null || qn.getEndDate() == null
				|| qn.getStartDate().isAfter(qn.getEndDate())) { //A是否在B之後(開始時間在結束時間之後 為false)
			return new QuizRes(RtnCode.QUESTIONNAIRE_PARAM_ERROR);//如果QUESTIONNAIRE這張表錯就不會檢查到第二張
		}
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) { 
			if(qu.getQuId() <=0 || !StringUtils.hasText(qu.getTitle()) 
					|| !StringUtils.hasText(qu.getOptionType())|| !StringUtils.hasText(qu.getOption())) { //這邊if判斷question的
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
			}
		}
		return null;
	}

	
	@Override //更新(修改已存在的資料),1.檢查參數 2.更新既有的資料
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
		if(qnOp.isEmpty()) { //檢查ID存不存在,如果不存在拋錯誤訊息出去
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_NOT_FOUND);
		}
		Questionnaire qn = qnOp.get();
		//可以修改的條件,判斷兩個條件:
		//1.尚未發布:is_published == false,可以修改
		//2.已發布但尚未開始進行:is_published == true + 當前時間必須小於start_date
		if(!qn.isPublished() || (qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate()))) { //LocalDate.now()當前時間
			qnDao.save(req.getQuestionnaire()); //存在就存資料進去
			quDao.saveAll(req.getQuestionList());
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.UPDATE_ERROR);
	}
	
	
	//抽方法,檢查Questionnaireid裡面的ID
	private QuizRes checkQuestionnaireid(QuizReq req) {
		if(req.getQuestionnaire().getId() <= 0) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
		}
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) {
			if(qu.getQnId() != req.getQuestionnaire().getId()) { //檢查Question裡面的ID不等於Questionnaire裡面的ID
				return new QuizRes(RtnCode.QUESTIONNAIRE_ID_PARAM_ERROR);
			}
		}
		return null;
	}

	
	@Override //刪除問卷題目及問題
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList) {
		List<Questionnaire> qnList = qnDao.findByIdIn(qnIdList);
		List<Integer> idList = new ArrayList<>();
		for (Questionnaire qn : qnList) {			   //當前時間小於開始日期	
			if(!qn.isPublished() || qn.isPublished()&& LocalDate.now().isBefore(qn.getStartDate())) {
//				qnDao.deleteById(qn.getId());
				idList.add(qn.getId());
			}
		}
		if(idList.isEmpty()) { //假設idList不是空的才去做刪除多筆資料
			qnDao.deleteAllById(idList); //刪除問卷題目,進到資料庫一次刪多筆
			quDao.deleteAllByQnIdIn(idList); //刪除問卷問題,一次刪多筆
		}
		return new QuizRes(RtnCode.SUCCESSFUL);
	}

	@Override
	public QuizRes deleteQuestion(int qnId, List<Integer> quIdList) {
		//							   刪第一章問卷內的,123題
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

	
	@Override //(搜尋問卷) 
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		//三元運算式,問號左邊為判斷式,右邊只有true跟false的結果,冒號左邊為當true的時候
		//當title有內容時返回原本的內容給他,不然返回空字串
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

	@Override //搜尋列表
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
