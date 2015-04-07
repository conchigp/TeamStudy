package com.teamstudy.myapp.web.rest.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

public class UserDTO {

	@Pattern(regexp = "^[a-z0-9]*$")
	@NotNull
	@Size(min = 1, max = 50)
	private String login;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 5, max = 100)
	private String password;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 100)
	private String email;

	@Size(min = 2, max = 5)
	private String langKey;

	private List<String> roles;

	private String isTeacher;

	@URL
	private String imageUrl;

	public UserDTO() {
	}

	public UserDTO(String id, String login, String password, String firstName,
			String lastName, String email, String langKey, List<String> roles,
			String isTeacher) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.langKey = langKey;
		this.roles = roles;
		this.isTeacher = isTeacher;
	}

	public UserDTO(String login, String password, String firstName,
			String lastName, String email, String langKey, List<String> roles,
			String isTeacher) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.langKey = langKey;
		this.roles = roles;
		this.isTeacher = isTeacher;
	}

	public UserDTO(String login, String password, String firstName,
			String lastName, String email, String langKey, List<String> roles,
			String isTeacher, String imageUrl) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.langKey = langKey;
		this.roles = roles;
		this.isTeacher = isTeacher;
		this.imageUrl = imageUrl;
	}

	public String getPassword() {
		return password;
	}

	public String getLogin() {
		return login;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getLangKey() {
		return langKey;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getIsTeacher() {
		return isTeacher;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "login='" + login + '\'' + ", password='"
				+ password + '\'' + ", firstName='" + firstName + '\''
				+ ", lastName='" + lastName + '\'' + ", email='" + email + '\''
				+ ", langKey='" + langKey + '\'' + ", roles=" + roles + '\''
				+ ", isTeacher=" + isTeacher + '\'' + ", imageUrl=" + imageUrl + '}';
	}
}
