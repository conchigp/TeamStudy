package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.service.GroupService;
import com.teamstudy.myapp.web.rest.dto.GroupDTO;

@RestController
@RequestMapping("/api")
public class GroupResource {

	private final Logger log = LoggerFactory.getLogger(GroupResource.class);

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;

	// Get students from group
	@RequestMapping(value = "/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<User> getAllByGroup(@RequestParam("groupId") String groupId,
			HttpServletResponse response) {
		log.debug("REST request to get all Users");
		return groupService.getStudentsByGroup(groupId);
	}

	@RequestMapping(value = "/group", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<String> delete(
			@RequestParam("groupId") String groupId, HttpServletRequest request) throws Exception {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			groupService.deleteGroup(groupId);
			return ResponseEntity.ok("Group deleted");
		}
	}

	@RequestMapping(value = "/group/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Group> getAllGroupByUser(
			@RequestParam("userId") String userId, HttpServletResponse response) {
		log.debug("REST request to get all groups for user => " + userId);
		return groupService.getGroupsForUser(userId);
	}

	@RequestMapping(value = "/group", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> create(@Valid @RequestBody GroupDTO groupDTO) {
		groupService.createGroup(groupDTO);
		return ResponseEntity.ok("Group created");
	}

	@RequestMapping(value = "/group", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> update(@Valid @RequestBody GroupDTO groupDTO,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupDTO.getId()));
		if (group == null) {
			return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			groupService.updateGroupInformation(groupDTO);
			return ResponseEntity.ok("Group updated");
		}
	}

}
