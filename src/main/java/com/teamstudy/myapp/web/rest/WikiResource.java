package com.teamstudy.myapp.web.rest;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bson.types.ObjectId;
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
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.GroupService;
import com.teamstudy.myapp.web.rest.dto.GroupDTO;


@RestController
@RequestMapping("/api")
public class WikiResource {
	
	@Inject
	private GroupRepository groupRepository;
	
	@Inject
	private GroupService groupService;
	
	@Inject
	private UserRepository userRepository;
	
	
	@RequestMapping(value = "/wiki", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> update(
			@Valid @RequestBody GroupDTO groupDTO,
			@RequestParam("groupId") String groupId, HttpServletRequest request) {
		if (groupDTO == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			User user = userRepository.findOneByLogin(SecurityUtils
					.getCurrentLogin());
			Group group = groupRepository.findOneById(new ObjectId(groupId));
			if (!user.isTeacher()) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not modify this wiky");
			} else if (user.isTeacher()
					&& !(new ObjectId(group.getTeacherId())
							.equals(user.getId()))) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not modify this wiky");
			} else {
				groupService.updateGroupInformation(groupDTO);
				return ResponseEntity.ok("Group update");
			}
		}
	}

}
