package com.teamstudy.myapp.web.rest.dto;

import javax.validation.constraints.NotNull;

public class ThreadDTO {

	private String id;

	@NotNull
	private String title;

	@NotNull
	private String description;

	private String userId;

	private String groupId;

	public ThreadDTO() {
	}

	public ThreadDTO(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public ThreadDTO(String id, String title, String description,
			String userId, String groupId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.userId = userId;
		this.groupId = groupId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThreadDTO other = (ThreadDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ThreadDTO{id='" + id + '\'' + ", title='" + title + '\''
				+ ", description='" + description + '\''
				+ ", userId='" + userId + '\''
				+ ", groupId='" + groupId + '\'' + "}";
	}

}