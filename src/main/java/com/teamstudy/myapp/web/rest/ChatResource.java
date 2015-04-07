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
import com.teamstudy.myapp.domain.MessageChat;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.ChatService;
import com.teamstudy.myapp.web.rest.dto.ChatDTO;

@RestController
@RequestMapping("/api")
public class ChatResource {

	@Inject
	private ChatService chatService;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private UserRepository userRepository;

	/* GET Methods */

	@RequestMapping(value = "/chat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<MessageChat> getAllByGroup(
			@RequestParam("groupId") String groupId,
			HttpServletResponse httpServletResponse) {
		if(groupRepository.findOneById(new ObjectId(groupId)) == null){
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}else{
			return chatService.findAllByGroup(groupId);
		}
	}

	/* POST Methods */

	@RequestMapping(value = "/chat", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(@Valid @RequestBody ChatDTO chatDTO,
			@RequestParam("groupId") String groupId,
			HttpServletRequest httpServletRequest) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			User user = userRepository.findOneByLogin(SecurityUtils
					.getCurrentLogin());
			if (!user.isTeacher()
					&& !group.getAlums().contains(user.getId().toString())) {
				return new ResponseEntity<>("Can not create a message", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId().toString())) {
				return new ResponseEntity<>("Can not create a message", HttpStatus.UNAUTHORIZED);
			} else {
				chatService.create(chatDTO, groupId);
				return new ResponseEntity<>("Message sent", HttpStatus.CREATED);
			}
		}
	}

}
