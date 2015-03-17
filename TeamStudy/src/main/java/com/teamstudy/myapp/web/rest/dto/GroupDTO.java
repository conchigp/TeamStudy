package com.teamstudy.myapp.web.rest.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.teamstudy.myapp.domain.Wiki;

public class GroupDTO {

	@NotNull
	private String name;

	private String description;

	@NotNull
	private String teacherId;

	private List<String> alums;

	private Wiki wiki;

	public GroupDTO(String name, String description, String teacherId,
			List<String> alums, Wiki wiki) {
		this.name = name;
		this.description = description;
		this.teacherId = teacherId;
		this.alums = alums;
		this.wiki = wiki;
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

	@Override
	public String toString() {
		return "GroupDTO{" + "name='" + name + '\'' + ", description='"
				+ description + '\'' + ", teacherId='" + teacherId + '\''
				+ ", alums='" + alums + '\'' + ", wiki='" + wiki + '}';
	}
}
