package com.teamstudy.myapp.web.rest.dto;

import javax.validation.constraints.NotNull;

public class ReplyDTO {

	private String id;

	@NotNull
	private String description;

	private String userId;

	private String messageId;

	public ReplyDTO(String description) {
		this.description = description;
	}

	public ReplyDTO(String id, String description, String userId,
			String messageId) {
		super();
		this.id = id;
		this.description = description;
		this.userId = userId;
		this.messageId = messageId;
	}

	public ReplyDTO() {
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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
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
		ReplyDTO other = (ReplyDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReplyDTO {id='" + id + '\'' + ", description='" + description
				+ '\'' + ", userId='" + userId
				+ '\'' + ", messageId='" + messageId
				+ '\'' + "}";
	}

}
