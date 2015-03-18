package com.teamstudy.myapp.domain;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.URL;

public class Archive {

	@NotNull
	private String title;

	@Past
	private Date creationMoment;

	@Min(0)
	private Long size;

	private String format;

	@NotNull
	@URL
	private String url;

	// Hace referencia al ID del usuario.
	@NotNull
	private String userId;

	@NotNull
	private Object gridId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Object getGridId() {
		return gridId;
	}

	public void setGridId(Object gridId) {
		this.gridId = gridId;
	}

	@Override
	public String toString() {
		return "Archive{" + "title='" + title + '\'' + ", creationMoment='"
				+ creationMoment + '\'' + ", size='" + size + '\''
				+ ", format='" + format + '\'' + ", url='" + url + '\''
				+ ", userId='" + userId + '\'' + "}";
	}

}
