package com.example.questionnaire.constants; //constants�`�Ʊ`�q���N��

public enum RtnCode { 
	
//	SUCCESSFUL�����\,()�����dHTTP���A�X,�D�n��200,400,401,403,404, 200�����\,�T�w��,�v����������401&403,404�T�w�N���䤣��,�ѤU�k����400
	SUCCESSFUL(200,"SUCCESSFUL!!"),//  		
	PARAM_ERROR(400,"PARAM ERROR!!"),//  
	QUESTIONNAIRE_PARAM_ERROR(400,"QUESTIONNAIRE PARAM ERROR!!"),//
	QUESTION_PARAM_ERROR(400,"QUESTION PARAM ERROR!!"),//
	ID_NOT_FOUND(404,"ID NOT FOUND!!"),//
	ID_PARAM_ERROR(400,"ID PARAM ERROR!!"),//
	UPDATE_ERROR(400,"UPDATE ERROR!!"),//
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
