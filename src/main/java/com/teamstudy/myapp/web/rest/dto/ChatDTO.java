package com.teamstudy.myapp.web.rest.dto;

import javax.validation.constraints.NotNull;

public class ChatDTO {

	@NotNull
	private String content;

	public ChatDTO(String content) {
		this.content = content;
	}

	public ChatDTO() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
