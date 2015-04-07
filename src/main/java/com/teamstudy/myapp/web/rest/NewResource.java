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
		if(userRepository.findOneById(new ObjectId(userId)) == null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}else{
			List<Group> groups = groupService.getGroupsForUser(userId);
			List<New> news = newService.getAllByGroups(groups);
			return news;
		}
	}

	@RequestMapping(value = "/news", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(@Valid @RequestBody NewsDTO newsDTO,
			@RequestParam("groupId") String groupId,
			HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			if(!user.isTeacher()){
				return new ResponseEntity<>("Can not create a news in this group", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher() && !group.getTeacherId().equals(user.getId().toString())){
				return new ResponseEntity<>("Can not create a news in this group", HttpStatus.UNAUTHORIZED);
			}else{
				newService.create(newsDTO, groupId);
				return new ResponseEntity<>("Thread created", HttpStatus.CREATED);
			}
		}
	}
}
