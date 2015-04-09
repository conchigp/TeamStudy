package com.teamstudy.myapp.web.rest.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.teamstudy.myapp.domain.Wiki;

public class GroupDTO {

	private String id;

	@NotNull
	private String name;

	private String description;

	private String teacherId;

	private List<String> alums;

	private Wiki wiki;

	public GroupDTO() {
	}

	public GroupDTO(String id, String name, String description,
			String teacherId, List<String> alums, Wiki wiki) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.teacherId = teacherId;
		this.alums = alums;
		this.wiki = wiki;
	}

	public GroupDTO(String name, String description, String teacherId,
			List<String> alums, Wiki wiki) {
		super();
		this.name = name;
		this.description = description;
		this.teacherId = teacherId;
		this.alums = alums;
		this.wiki = wiki;
	}

	public GroupDTO(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public GroupDTO(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public List<String> getAlums() {
		return alums;
	}

	public Wiki getWiki() {
		return wiki;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		GroupDTO other = (GroupDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GroupDTO [id=" + id + ", name=" + name + ", description="
				+ description + ", teacherId=" + teacherId + ", alums=" + alums
				+ ", wiki=" + wiki + "]";
	}

}
