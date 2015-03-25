package com.teamstudy.myapp.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class Reply {

	@NotNull
	private String description;

	@Past
	private Date creationMoment;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@Override
	public String toString() {
		return "Reply{" + "description='" + description + '\''
				+ ", creationMoment='" + creationMoment + '\'' + "}";
	}

}
