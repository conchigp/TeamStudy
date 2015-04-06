package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_GROUP")
public class Group extends AbstractAuditingEntity implements Serializable {

	@Id
	private String id;

	@NotNull
	private String name;

	private String description;

	@Past
	private Date creationMoment;

	// Hace referencia al ID del usuario
	private String teacherId;

	// lista de ids de alumnos
	private List<String> alums;

	private Wiki wiki;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public List<String> getAlums() {
		return alums;
	}

	public void setAlums(List<String> alums) {
		this.alums = alums;
	}

	public Wiki getWiki() {
		return wiki;
	}

	public void setWiki(Wiki wiki) {
		this.wiki = wiki;
	}

	@Override
	public String toString() {
		return "Group{" + "id='" + id + '\'' + "name='" + name + '\'' + ", description='"
				+ description + '\'' + ", creationMoment='" + creationMoment
				+ '\'' + ", teacherId='" + teacherId + '\'' + ", alums='"
				+ alums + '\'' + ", wiki='" + wiki + '\'' + "}";
	}
}
