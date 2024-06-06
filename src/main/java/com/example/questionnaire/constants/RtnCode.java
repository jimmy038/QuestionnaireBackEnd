package com.example.questionnaire.constants; //constantsï¿½`ï¿½Æ±`ï¿½qï¿½ï¿½ï¿½Nï¿½ï¿½

public enum RtnCode { 
	
<<<<<<< HEAD
//	SUCCESSFUL¬°¦¨¥\,()¤ºªº¬dHTTPª¬ºA½X,¥D­n¥Î200,400,401,403,404, 200¬°¦¨¥\,©T©wªº,Åv­­¦³¬ÛÃöªº401&403,404©T©w´N¬°§ä¤£¨ì,³Ñ¤UÂkÃþ¨ì400
	SUCCESSFUL(200,"SUCCESSFUL!!"),//  			¦b³r¸¹«á­±¥[¤Wµù¸Ñ²Å¸¹¦³Â_¦æªº¥Î·N
	QUESTION_PARAM_ERROR(400,"Question_Param_error!!"),//  Param_error°Ñ¼Æ¿ù»~ªº·N«ä ³q±`¦r¦ê¤º®e¬°«e­±°T®§ªº¤p¼g
	QUESTIONNAIRE_PARAM_ERROR(400,"Questionnaire_Param_error!!"),//
	QUESTIONNAIRE_ID_PARAM_ERROR(400,"Questionnaire_id_Param_error!!"),//
	QUESTIONNAIRE_ID_NOT_FOUND(404,"Questionnaire_id_Not_Found!!"),
	UPDATE_ERROR(400,"Upadate_Error"),//
	SAVE_ERROR(400,"Save_Error!!"),//
	ID_NOT_FOUND(400,"ID_NOT_FOUND!!"),  //
	PARAM_ERROR(400,"PARAM_ERROR"), //
	DATABASE_ERROR(400,"DATABASE_ERROR"),//
=======
//	SUCCESSFULï¿½ï¿½ï¿½ï¿½ï¿½\,()ï¿½ï¿½ï¿½ï¿½ï¿½dHTTPï¿½ï¿½ï¿½Aï¿½X,ï¿½Dï¿½nï¿½ï¿½200,400,401,403,404, 200ï¿½ï¿½ï¿½ï¿½ï¿½\,ï¿½Tï¿½wï¿½ï¿½,ï¿½vï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½401&403,404ï¿½Tï¿½wï¿½Nï¿½ï¿½ï¿½ä¤£ï¿½ï¿½,ï¿½Ñ¤Uï¿½kï¿½ï¿½ï¿½ï¿½400
	SUCCESSFUL(200,"SUCCESSFUL!!"),//  		
	PARAM_ERROR(400,"PARAM ERROR!!"),//  
	QUESTIONNAIRE_PARAM_ERROR(400,"QUESTIONNAIRE PARAM ERROR!!"),//
	QUESTION_PARAM_ERROR(400,"QUESTION PARAM ERROR!!"),//
	ID_NOT_FOUND(404,"ID NOT FOUND!!"),//
	ID_PARAM_ERROR(400,"ID PARAM ERROR!!"),//
	UPDATE_ERROR(400,"UPDATE ERROR!!"),//
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
	;
	
	private int code; //ï¿½oï¿½äªºcodeï¿½ï¿½ï¿½ï¿½ï¿½Oï¿½Nï¿½X,ï¿½^ï¿½Ç¤@ï¿½Ó¥Nï¿½X
	
	private String message;

	private RtnCode(int code, String message) { //ï¿½ï¿½ï¿½Í±aï¿½ï¿½ï¿½Ñ¼Æªï¿½ï¿½Øºcï¿½ï¿½k
		this.code = code;
		this.message = message;
	}

	public int getCode() {	//ï¿½oï¿½ï¿½uï¿½|ï¿½Î¨ï¿½getï¿½]ï¿½ï¿½ï¿½uï¿½Ý­nï¿½ï¿½ï¿½ï¿½Get
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
