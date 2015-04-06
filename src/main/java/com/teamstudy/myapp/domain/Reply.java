package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_REPLY")
public class Reply extends AbstractAuditingEntity implements Serializable {

	@Id
	private String id;

	@NotNull
	private String description;

	@Past
	private Date creationMoment;

	@NotNull
	private String messageId;

	@NotNull
	private String userId;

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

	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Reply{" + "id='" + id + '\'' + ", description='" + description
				+ '\'' + ", creationMoment='" + creationMoment + '\''
				+ ", messageId='" + messageId + '\'' + ", userId='" + userId
				+ '\'' + "}";
	}

}
