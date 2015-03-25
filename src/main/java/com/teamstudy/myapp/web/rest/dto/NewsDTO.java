package com.teamstudy.myapp.web.rest.dto;

import javax.validation.constraints.NotNull;

public class NewsDTO {
	
	private String id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	public NewsDTO(String id, String title, String description){
		
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	
	@Override
	public String toString() {
		return "NewsDTO{" + "id='" + id + '\'' + ", title='"
				+ title + '\'' + ", description='" + description + '}';
	}
	

}
