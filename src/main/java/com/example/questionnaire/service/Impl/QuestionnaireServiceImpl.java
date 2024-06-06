package com.example.questionnaire.service.Impl;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;

@Service
public class QuestionnaireServiceImpl implements QuizService{

	@Autowired
	private QuestionnaireDao  qnDao;
	
	@Autowired
	private QuestionDao  qusetionDao;

	@Transactional //兩張表做存資料要加的
	@Override
	public QuizRes create(QuizReq req) {
		QuizRes checkResult = checkParam(req);
		if (checkResult != null) {
			return checkResult;
		}
		 qnDao.save(req.getQusetionnaire());
		 List<Question> quList = req.getQuestionList();
		 //如果這個List不為空，有東西時直接回傳
		 if(quList.isEmpty()){
			 return new QuizRes(RtnCode.SUCCESSFUL);
		 }
		 int qnId = qnDao.findTopByOrederByIdDesc().getId();
		 for(Question qu : quList) {
			 qu.setQnId(qnId);
		 }
		 qusetionDao.saveAll(quList);
		return new QuizRes(RtnCode.SUCCESSFUL);
	}
	
	
	//額外抽出單獨檢查參數(防呆)的一個獨立方法
	private QuizRes checkParam(QuizReq req) {
		Questionnaire qn = req.getQusetionnaire();
		if(!StringUtils.hasText(qn.getTitle()) 
			|| !StringUtils.hasText(qn.getDescription())
			|| qn.getStartDate() == null 
			|| qn.getEndDate() == null
			|| qn.getStartDate().isAfter(qn.getEndDate())) {
			return new QuizRes(RtnCode.QUESTIONNAIRE_PARAM_ERROR);			
		}
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) {
			if(qu.getId() <= 0 || !StringUtils.hasText(qu.getqTitle()) 
				|| !StringUtils.hasText(qu.getOptionType())
				|| !StringUtils.hasText(qu.getOption())) {
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR);
			}
		}
		return null;
	}


	@Transactional	//兩張表做存資料要加的
	@Override  		//更新
	public QuizRes update(QuizReq req) {
		QuizRes checkResult = checkParam(req);
		if (checkResult != null) {
			return checkResult;
		}
		checkResult = checkQusetionnaireId(req); 
		if (checkResult != null) {
			return checkResult;
		}
		Optional<Questionnaire> qnOp = qnDao.findById(req.getQusetionnaire().getId());
		//撈完資料後需先判斷是否為空。因為是upadte必須先判斷ID是否存在，若不存在回傳id為空
		if(qnOp.isEmpty()) {
			return new QuizRes(RtnCode.ID_NOT_FOUND);
		}
		//取值
		Questionnaire qn = qnOp.get();
		/*可以修改的條件為
		1.尚未發布is_published == false ， 可以修改 
		2.已發布但尚未開始進行且現在時間小於開始時間 is_published == true + 當前時間必須小於start_date，可以修改 */
		if(!qn.isPublished() || (qn.isPublished() && LocalDate.now().isBefore(qn.getStartDate()))) {
			qnDao.save(req.getQusetionnaire());
			qusetionDao.saveAll(req.getQuestionList());
			return new QuizRes(RtnCode.SUCCESSFUL);
		}
		return new QuizRes(RtnCode.UPDATE_ERROR);
	}

	
	//防呆檢查問卷id是否有等於問卷問題更新時的id
	private QuizRes checkQusetionnaireId(QuizReq req) {
		if (req.getQusetionnaire().getId() <= 0 ) {
			return new QuizRes(RtnCode.ID_PARAM_ERROR);
		}
		List<Question> quList = req.getQuestionList();
		for(Question qu : quList) {
			if(qu.getQnId() != req.getQusetionnaire().getId()) {
				return new QuizRes(RtnCode.ID_PARAM_ERROR);
			}
		}
		return null;
	}


	@Override
	public QuizRes deleteQuestionnaire(List<Integer> qnIdList) {
		 List<Questionnaire> qnList = qnDao.findByIdIn(qnIdList);
		 
		return null;
	}


	@Override
	public QuizRes deleteQuestion(int qnId,List<Integer> quIdList) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public QuizRes search(String title, LocalDate startDate, LocalDate endDate) {
		
		return null;
	}
	
	
	
	
}
