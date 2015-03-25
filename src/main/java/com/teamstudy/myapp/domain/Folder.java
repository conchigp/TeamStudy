package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_FOLDER")
public class Folder extends AbstractAuditingEntity implements Serializable {

	@Id
	private String id;

	@NotNull
	private String title;

	@Past
	private Date creationMoment;

	@NotNull
	private String groupId;

	@NotNull
	private List<Archive> archives;

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

	public List<Archive> getArchives() {
		return archives;
	}

	public void setArchives(List<Archive> archives) {
		this.archives = archives;
	}

	@Override
	public String toString() {
		return "Folder{" + "id='" + id + '\'' + "title='" + title + '\'' + ", creationMoment='"
				+ creationMoment + '\'' + ", groupId='" + groupId + '\'' + ", archives='" + archives + '\'' + "}";
	}

}
