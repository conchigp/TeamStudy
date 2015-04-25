package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.service.GroupService;
import com.teamstudy.myapp.web.rest.dto.GroupDTO;

@RestController
@RequestMapping("/api")
public class GroupResource {

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;

	@Inject
	private UserRepository userRepository;

	@RequestMapping(value = "/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN })
	public Group getOne(@RequestParam("groupId") String groupId,
			HttpServletResponse response) {
		if (groupRepository.findOneById(new ObjectId(groupId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return groupRepository.findOneById(new ObjectId(groupId));
		}
	}

	@RequestMapping(value = "/group", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<String> delete(
			@RequestParam("groupId") String groupId, HttpServletRequest request)
			throws Exception {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			groupService.deleteGroup(groupId);
			return new ResponseEntity<>("Group deleted", HttpStatus.ACCEPTED);
		}
	}

	@RequestMapping(value = "/group/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Group> getAllGroupByUser(@RequestParam("userId") String userId,
			HttpServletResponse response) {
		if (userRepository.findOneById(new ObjectId(userId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return groupService.getGroupsForUser(userId);
		}
	}

	@RequestMapping(value = "/group", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed({AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
	public ResponseEntity<?> create(@Valid @RequestBody GroupDTO groupDTO) {
		groupService.createGroup(groupDTO);
		return new ResponseEntity<>("Group created", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/group", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER})
	public ResponseEntity<?> update(@Valid @RequestBody GroupDTO groupDTO,
			HttpServletRequest request) {
		if (groupDTO.getId() == null) {
			return create(groupDTO);
		}
		Group group = groupRepository
				.findOneById(new ObjectId(groupDTO.getId()));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			groupService.updateGroupInformation(groupDTO);
			return new ResponseEntity<>("Group updated", HttpStatus.ACCEPTED);
		}
	}

	@RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public List<Group> getAll(HttpServletResponse response) {
		return groupRepository.findAll();
	}

}
