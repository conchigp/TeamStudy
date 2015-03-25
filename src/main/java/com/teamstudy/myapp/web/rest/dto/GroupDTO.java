package com.teamstudy.myapp.web.rest.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.teamstudy.myapp.domain.Wiki;

public class GroupDTO {

	
	private String id;

	@NotNull
	private String name;

	private String description;

	@NotNull
	private String teacherId;

	private List<String> alums;

	private Wiki wiki;

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
	public String toString() {
		return "GroupDTO [id=" + id + ", name=" + name + ", description="
				+ description + ", teacherId=" + teacherId + ", alums=" + alums
				+ ", wiki=" + wiki + "]";
	}

}
