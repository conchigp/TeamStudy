package com.teamstudy.myapp.web.rest.dto;

import javax.validation.constraints.NotNull;

public class NewsDTO {
		
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	public NewsDTO(String title, String description){
		this.title = title;
		this.description = description;
	}
	
	public NewsDTO(){
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
	

}
