package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.New;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.GroupService;
import com.teamstudy.myapp.service.NewService;
import com.teamstudy.myapp.web.rest.dto.NewsDTO;

@RestController
@RequestMapping("/api")
public class NewResource {

	@Inject
	private NewService newService;

	@Inject
	private GroupService groupService;

	@Inject
	private UserRepository userRepository;

	@Inject
	private GroupRepository groupRepository;

	/**
	 * Return all the news from the groups where the user are registred.
	 */
	@RequestMapping(value = "/news", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<New> getAllByUser(@RequestParam("userId") String userId,
			HttpServletResponse response) {
		List<Group> groups = groupService.getGroupsForUser(userId);
		List<New> news = newService.getAllByGroups(groups);

		return news;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> createNew(@Valid @RequestBody NewsDTO newsDTO,
			@RequestParam("groupId") String groupId,
			HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Group group = groupRepository.findOne(groupId);
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This Group does not exist");
		} else if (group.getAlums().contains(user.getId())) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("The Alumn not belong at the group");
		} else {
			newService.create(newsDTO, groupId);
			return ResponseEntity.ok("Thread created");
		}
	}
}
