package com.example.questionnaire.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "questionnaire")
public class Questionnaire {
	//↓
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id") //這邊ID為AI AI會自動生成
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_published")
	private boolean published; //命名boolean的時候,不能用到is只能用後面的名字
	
	@Column(name = "start_date")
	private LocalDate startDate; //日期要用LocalDate LocalDateTime(有日期又有時間用這個)
	
	@Column(name = "end_date")
	private LocalDate endDate;

	public Questionnaire() {
		super();
	}

	public Questionnaire(String title, String description, boolean published, LocalDate startDate, LocalDate endDate) {
		super();
		this.title = title;
		this.description = description;
		this.published = published;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
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
