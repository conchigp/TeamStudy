package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_THREAD")
public class Thread extends AbstractAuditingEntity implements Serializable {

	@Id
	private String id;

	@NotNull
	private String title;

	@NotNull
	private String description;

	@Past
	private Date creationMoment;

	@NotNull
	private String groupId;

	@NotNull
	private String userId;

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

	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Thread{" + "id='" + id + '\'' + "title='" + title + '\'' + ", description='"
				+ description + '\'' + ", creationMoment='" + creationMoment
				+ '\'' + ", groupId='" + groupId + '\'' + ", userId='" + userId
				+ '\'' + "}";
	}

}
