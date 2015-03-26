package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.Message;
import com.teamstudy.myapp.domain.Thread;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.ThreadRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.MessageService;
import com.teamstudy.myapp.service.ThreadService;
import com.teamstudy.myapp.web.rest.dto.ThreadDTO;

@RestController
@RequestMapping("/api")
public class ThreadResource {

	@Inject
	private ThreadService threadService;

	@Inject
	private ThreadRepository threadRepository;

	@Inject
	private UserRepository userRepository;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private MessageService messageService;

	/* GET Methods */

	@RequestMapping(value = "/thread/{groupId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Thread> getAllByGroup(@PathVariable String groupId,
			HttpServletResponse response) {
		return threadService.findAllByGroup(groupId);
	}

	@RequestMapping(value = "/thread/{threadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Thread getOne(@PathVariable String threadId,
			HttpServletResponse response) {
		return threadRepository.findOne(threadId);
	}

	/* POST Methods */

	// Crear Hilo
	@RequestMapping(value = "/thread/{groupId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> createThread(
			@Valid @RequestBody ThreadDTO threadDTO,
			@PathVariable String groupId, HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Group group = groupRepository.findOne(groupId);
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This thread does not exist");
		} else {
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not create a Thread in this group");
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not create a Thread in this group");
			} else {
				threadService.create(threadDTO, groupId);
				return ResponseEntity.ok("Thread created");
			}
		}
	}

	// Modificar Hilo
	@RequestMapping(value = "/thread", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> updateThread(
			@Valid @RequestBody ThreadDTO threadDTO,
			HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Thread thread = threadRepository.findOne(threadDTO.getId());
		List<Message> messages = messageService.findAllByThread(threadDTO.getId());
		Group group = groupRepository.findOne(threadDTO.getGroupId());
		if (thread == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This thread does not exist");
		} else {
			if(!user.isTeacher() && !messages.isEmpty()){
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not update a thread with messages");
			} else {
				if (!user.isTeacher() && !user.getId().equals(thread.getUserId())) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not modify this thread");
				
				}else if(user.isTeacher() && !user.getId().equals(group.getTeacherId())){
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not modify this thread");
				}else{
					threadService.update(threadDTO);
					return ResponseEntity.ok("Thread updated");
				}
			}
		}
	}

	@RequestMapping(value = "/thread/{threadId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> deleteThread(@PathVariable String threadId,
			HttpServletRequest httpServletRequest) {
		Thread thread = threadRepository.findOne(threadId);
		User user = userRepository.findOne(SecurityUtils.getCurrentLogin());
		List<Message> messages = messageService.findAllByThread(threadId);
		if (thread == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This thread does not exist");
		} else {
			if(!user.isTeacher() && !messages.isEmpty()){
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not delete a thread with messages");
			} else {
				Group group = groupRepository.findOne(thread.getGroupId());
				if (!user.isTeacher() && !user.getId().equals(thread.getUserId())) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not delete this thread");
				
				}else if(user.isTeacher() && !user.getId().equals(group.getTeacherId())){
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not delete this thread");
				}else{
					threadService.delete(threadId);
					return ResponseEntity.ok("Thread deleted");
				}
			}
		}
	}
}
