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

/*Controller命名方式都是跟著service*/
@RestController /* 要先在build.gradle加web,再重新import進來 */
@CrossOrigin // @CrossOrigin (跨網域請求)
public class QuizConetroller {

	// 新增,刪除,修改,都使用post Get就為一般請求而已,單純取資源

	@Autowired /* Conetroller跟Service做介接,@Autowired QuizService進來 */
	private QuizService service;
	
	@Autowired
	private UserService userService;

	/* @RequestBody,接受外部傳入的資料,讓外面使用工具時讓他依舊保持JSON格式:格式為:Key:value */
//	PostMapping 新增 post 提供 http methods
	@PostMapping(value = "api/quiz/create") // ←一個api的完整格式為一個url,給外面的使用,post指的是提供HTTP的請求方法
	public QuizRes create(@RequestBody QuizReq req) {
		return service.create(req);
	}

//	↓定義了這個方法處理來自 /api/quiz/search 路徑的 HTTP GET 請求。	
//	↓使用 service.search(title, startDate, endDate) 方法來執行具體的搜尋操作，並返回一個 QuizRes 物件。處理 GET 請求的方法，使用@RequestBody註解來指定請求的主體應該被映射到QuizSearchReq對象上。這個方法的返回類型為QuizRes。	
	@GetMapping(value = "api/quiz/search") // (搜尋問卷)
	public QuizRes search(QuizSearchReq req) {
//		↓使用StringUtils.hasText 方法檢查 req 中的 title 是否有文字，如果有則使用該文字，否則將其設置為空字串。		
//		String title = StringUtils.hasText(req.getTitle()) ? req.getTitle() : ""; //把null變為空字串
//		↓對startDate進行檢查，如果 req 中的 startDate 不為 null，則使用該值，否則將其設置為 1971 年 1 月 1 日。		
//		LocalDate startDate = req.getStartDate() != null? req.getStartDate() : LocalDate.of(1971, 1, 1); //當startDate為null時轉換為後面()內的時間
//		↓對endDate進行檢查，如果 req 中的 endDate 不為 null，則使用該值，否則將其設置為 2099 年 12 月 31 日。		
//		LocalDate endDate = req.getEndDate() != null? req.getEndDate() : LocalDate.of(2099, 12, 31); //當endDate為null時轉換為後面()內的時間
//		↓使用 service.search(title, startDate, endDate) 方法來執行具體的搜尋操作，並返回一個 QuizRes 物件。
//		return service.search(title,startDate,endDate);		
		return service.search(req.getTitle(), req.getStartDate(), req.getEndDate());
	}

	// update更新
	@PostMapping(value = "api/quiz/update")
	public QuizRes update(@RequestBody QuizReq req) {
		return service.update(req);
	}

	// deleteQuestionnaire刪除問卷
	@PostMapping(value = "api/quiz/deleteQuestionnaire")
	public QuizRes deleteQuestionnaire(@RequestBody List<Integer> qnIdList) {
		return service.deleteQuestionnaire(qnIdList);
	}

	// deleteQuestion刪除問卷問題
	@PostMapping(value = "api/quiz/deleteQuestion")
	public QuizRes deleteQuestion(@RequestBody int qnId, List<Integer> quIdList) {
		return service.deleteQuestionnaire(quIdList);
	}

	// 搜尋問卷列表
	@GetMapping(value = "api/quiz/searchQuestionnaireList")
	public QuestionnaireRes searchQuestionnaireList(String title, LocalDate startDate, LocalDate endDate,
			boolean isPublished) {
		return service.searchQuestionnaireList(title, startDate, endDate, isPublished);
	}

	// 搜尋問卷列表模糊搜尋
	@GetMapping(value = "api/quiz/searchQuiz")
	public QuestionnaireRes searchQuizs(
		@RequestParam(value="title",required = false) String title,
		@RequestParam(value="startDate",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam(value="endDate",required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return service.searchQuizs(title, startDate, endDate);
	}

	// 搜尋問題列表
	@GetMapping(value = "api/quiz/searchQuestionList")
	public QuestionRes searchQuestionList(int qnId) {
		return service.searchQuestionList(qnId);
	}
	
	//存使用者資料
	@PostMapping(value = "api/quiz/saveUser") // ←一個api的完整格式為一個url,給外面的使用,post指的是提供HTTP的請求方法
	public UserRes saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	//找問卷ID及找問卷內底下對應問題
	@GetMapping(value = "api/quiz/getQuizInfo") 
	public QuizRes getQuizInfo(@RequestParam(value = "id") int id) {
		return service.getQuizInfo(id);
	}
	
	//找使用者回答所有資訊的ID
	@GetMapping(value = "api/quiz/getAnsId") 
	public UserRes getAnsId(@RequestParam(value = "id") int ansId) {
		return userService.getAnsId(ansId);
	}
	
	//抓所有資料 問卷 問題 user api
	@GetMapping(value = "api/quiz/getgetCombinedData") 
	public QuizRes getCombinedData(@RequestParam(value = "id")int id,
			@RequestParam(value = "ansId")int ansId) {
		return userService.getCombinedData(id,ansId);
	}
}
