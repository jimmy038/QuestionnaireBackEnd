package com.example.questionnaire.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizSearchReq {

	private String title;
	
	@JsonProperty("start_Date") //用@JsonProperty對應到post_man裡面的start_Date
	private LocalDate startDate;
	
	@JsonProperty("end_Date") //用@JsonProperty對應到post_man裡面的end_Date
	private LocalDate endDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
	
}
