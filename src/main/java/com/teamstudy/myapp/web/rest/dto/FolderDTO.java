package com.teamstudy.myapp.web.rest.dto;

import javax.validation.constraints.NotNull;

public class FolderDTO {

	private String id;

	@NotNull
	private String title;

	private String groupId;

	public FolderDTO(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public FolderDTO(String id, String title, String groupId) {
		this.id = id;
		this.title = title;
		this.groupId = groupId;
	}

	public FolderDTO(String title) {
		this.title = title;
	}

	public FolderDTO() {

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
		FolderDTO other = (FolderDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FolderDTO [id='" + id + '\'' + ", title='" + title + '\'' + ", groupId='" + groupId + '\'' + "]";
	}

}
