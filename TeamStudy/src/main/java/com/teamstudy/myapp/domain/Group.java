package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_GROUP")
public class Group extends AbstractAuditingEntity implements Serializable {

	@NotNull
	private String name;
	
	private String description;
	
	@Past
	private Date creationMoment;
	
	@NotNull
	private User2 teacher;
	
	//lista de ids de alumnos
	private List<String> alums;

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

	public User2 getTeacher() {
		return teacher;
	}

	public void setTeacher(User2 teacher) {
		this.teacher = teacher;
	}

	public List<String> getAlums() {
		return alums;
	}

	public void setAlums(List<String> alums) {
		this.alums = alums;
	}

    @Override
    public String toString() {
        return "Group{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", creationMoment='" + creationMoment
                + '\'' + ", teacher='" + teacher + '\'' + ", alums='" + alums + '\'' + "}";
    }

}
