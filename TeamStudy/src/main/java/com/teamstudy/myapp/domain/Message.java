package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_MESSAGE")
public class Message extends AbstractAuditingEntity implements Serializable {

	@Id
	private String id;

	@NotNull
	private String title;

	@NotNull
	private String description;

	@Past
	private Date creationMoment;

	@NotNull
	private Thread thread;

	@NotNull
	private String userId;

	@NotNull
	private List<Reply> replies;

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

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	@Override
	public String toString() {
		return "Message{" + "id='" + id + '\'' + "title='" + title + '\''
				+ ", description='" + description + '\'' + ", creationMoment='"
				+ creationMoment + '\'' + ", thread='" + thread + '\''
				+ ", userId='" + userId + '\'' + ", replies='" + replies + '\'' + "}";
	}

}
