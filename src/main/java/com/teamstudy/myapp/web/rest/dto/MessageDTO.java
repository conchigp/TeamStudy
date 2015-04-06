package com.teamstudy.myapp.web.rest.dto;

import javax.validation.constraints.NotNull;

public class MessageDTO {

	private String id;

	@NotNull
	private String description;

	private String userId;

	private String threadId;

	public MessageDTO(String description) {
		this.description = description;
	}

	public MessageDTO(String id, String description, String userId,
			String threadId) {
		super();
		this.id = id;
		this.description = description;
		this.userId = userId;
		this.threadId = threadId;
	}

	public MessageDTO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
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
		MessageDTO other = (MessageDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessageDTO {id='" + id + '\'' + ", description='" + description
				+ '\'' + ", userId='" + userId + '\''
				+ ", threadId='" + threadId + '\'' + "}";
	}

}
