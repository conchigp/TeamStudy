package com.teamstudy.myapp.domain;

import javax.validation.constraints.NotNull;


public class Wiki {

	@NotNull
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
