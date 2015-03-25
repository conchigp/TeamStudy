package com.teamstudy.myapp.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.service.GroupService;
import com.teamstudy.myapp.web.rest.dto.GroupDTO;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Inject
	private UserRepository userRepository;

	@Inject
	private GroupService groupService;

	@Inject
	private GroupRepository groupRepository;

	/**
	 * GET /users -> get all users.
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<User> getAll() {
		log.debug("REST request to get all Users");
		return userRepository.findAll();
	}

	/**
	 * GET /users/:login -> get the "login" user.
	 */
	@RequestMapping(value = "/users/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public User getUser(@PathVariable String login, HttpServletResponse response) {
		log.debug("REST request to get User : {}", login);
		User user = userRepository.findOneByLogin(login);
		if (user == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return user;
	}

	// Get groups for user (MIO)
	@RequestMapping(value = "/users/{userId}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Group> getAllGroupForUSer(@PathVariable String userId,
			HttpServletResponse response) {
		log.debug("REST request to get all groups for user");
		return groupService.getGroupsForUser(userId);
	}

	// create group.(MIO)
	@RequestMapping(value = "/users/groups", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> createGroup(@Valid @RequestBody Group group) {
		if (group.getId() != null) {
			return ResponseEntity.badRequest()
					.header("Failure", "A new group cannot already have an ID")
					.build();
		} else {

			groupService.createGroup(group);

			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}

	// update group information (MIO)
	@RequestMapping(value = "/groups/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> updateGroup(@Valid @RequestBody GroupDTO groupDTO,@PathVariable String id, HttpServletRequest request) {
		log.debug("REST request to update Group : {}", groupDTO);
		if (groupDTO.getId() == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
			
		}
		groupService.updateGroupInformation(groupDTO,id);
		return ResponseEntity.ok("group update");
	}

	/**
	 * GET /books/:id -> get the "id" book.
	 */
	@RequestMapping(value = "/books/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Group> get(@PathVariable String id) {
		log.debug("REST request to get Book : {}", id);

		Group group = groupRepository.findOne(id);

		if (group != null) {
			return new ResponseEntity<>(group, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}
