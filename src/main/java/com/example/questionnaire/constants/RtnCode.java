package com.example.questionnaire.constants; //constants�`�Ʊ`�q���N��

public enum RtnCode { 
	
<<<<<<< HEAD
//	SUCCESSFUL�����\,()�����dHTTP���A�X,�D�n��200,400,401,403,404, 200�����\,�T�w��,�v����������401&403,404�T�w�N���䤣��,�ѤU�k����400
	SUCCESSFUL(200,"SUCCESSFUL!!"),//  			�b�r���᭱�[�W���ѲŸ����_�檺�ηN
	QUESTION_PARAM_ERROR(400,"Question_Param_error!!"),//  Param_error�Ѽƿ��~���N�� �q�`�r�ꤺ�e���e���T�����p�g
	QUESTIONNAIRE_PARAM_ERROR(400,"Questionnaire_Param_error!!"),//
	QUESTIONNAIRE_ID_PARAM_ERROR(400,"Questionnaire_id_Param_error!!"),//
	QUESTIONNAIRE_ID_NOT_FOUND(404,"Questionnaire_id_Not_Found!!"),
	UPDATE_ERROR(400,"Upadate_Error"),//
	SAVE_ERROR(400,"Save_Error!!"),//
	ID_NOT_FOUND(400,"ID_NOT_FOUND!!"),  //
	PARAM_ERROR(400,"PARAM_ERROR"), //
	DATABASE_ERROR(400,"DATABASE_ERROR"),//
=======
//	SUCCESSFUL�����\,()�����dHTTP���A�X,�D�n��200,400,401,403,404, 200�����\,�T�w��,�v����������401&403,404�T�w�N���䤣��,�ѤU�k����400
	SUCCESSFUL(200,"SUCCESSFUL!!"),//  		
	PARAM_ERROR(400,"PARAM ERROR!!"),//  
	QUESTIONNAIRE_PARAM_ERROR(400,"QUESTIONNAIRE PARAM ERROR!!"),//
	QUESTION_PARAM_ERROR(400,"QUESTION PARAM ERROR!!"),//
	ID_NOT_FOUND(404,"ID NOT FOUND!!"),//
	ID_PARAM_ERROR(400,"ID PARAM ERROR!!"),//
	UPDATE_ERROR(400,"UPDATE ERROR!!"),//
>>>>>>> 3574534accc9673b71bf9cd8bd9e82d904ab7dc0
	;
	
	private int code; //�o�䪺code�����O�N�X,�^�Ǥ@�ӥN�X
	
	private String message;

	private RtnCode(int code, String message) { //���ͱa���Ѽƪ��غc��k
		this.code = code;
		this.message = message;
	}

	public int getCode() {	//�o��u�|�Ψ�get�]���u�ݭn����Get
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
