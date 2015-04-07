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
import com.teamstudy.myapp.domain.Message;
import com.teamstudy.myapp.domain.Reply;
import com.teamstudy.myapp.domain.Thread;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.MessageRepository;
import com.teamstudy.myapp.repository.ThreadRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.MessageService;
import com.teamstudy.myapp.service.ReplyService;
import com.teamstudy.myapp.web.rest.dto.MessageDTO;

@RestController
@RequestMapping("/api")
public class MessageResource {

	@Inject
	private UserRepository userRepository;

	@Inject
	private MessageRepository messageRepository;

	@Inject
	private MessageService messageService;

	@Inject
	private ThreadRepository threadRepository;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private ReplyService replyService;

	/* GET Methods */

	@RequestMapping(value = "/message/thread", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Message> getAllByThread(
			@RequestParam("threadId") String threadId,
			HttpServletResponse response) {
		if (threadRepository.findOneById(new ObjectId(threadId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return messageService.findAllByThread(threadId);
		}
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Message getOne(@RequestParam("messageId") String messageId,
			HttpServletResponse response) {
		if (messageRepository.findOneById(new ObjectId(messageId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return messageRepository.findOneById(new ObjectId(messageId));
		}
	}

	/* POST Methods */

	@RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(@Valid @RequestBody MessageDTO messageDTO,
			@RequestParam("threadId") String threadId,
			HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Thread thread = threadRepository.findOneById(new ObjectId(threadId));
		if (thread == null) {
			return new ResponseEntity<>("Thread not exist", HttpStatus.NOT_FOUND);
		} else {
			Group group = groupRepository.findOneById(new ObjectId(thread
					.getGroupId()));
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return new ResponseEntity<>("Can not create a Message", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return new ResponseEntity<>("Can not create a Message", HttpStatus.UNAUTHORIZED);
			} else {
				messageService.create(messageDTO, threadId);
				return new ResponseEntity<>("Message created", HttpStatus.CREATED);
			}
		}
	}

	@RequestMapping(value = "/message", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> update(@Valid @RequestBody MessageDTO messageDTO, @RequestParam("threadId") String threadId,
			HttpServletRequest httpServletRequest) {
		if(messageDTO.getId()==null){
			return create(messageDTO, threadId, httpServletRequest);
		}
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Message message = messageRepository.findOneById(new ObjectId(messageDTO
				.getId()));
		List<Reply> replies = replyService.findAllByMessage(messageDTO.getId());
		if (message == null) {
			return new ResponseEntity<>("Message not exist", HttpStatus.NOT_FOUND);
		} else {
			Thread thread = threadRepository.findOneById(new ObjectId(message.getThreadId()));
			Group group = groupRepository.findOneById(new ObjectId(thread.getGroupId()));
			if (!user.isTeacher() && !messageDTO.getUserId().equals(user.getId())) {
				return new ResponseEntity<>("Can not update a Message", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return new ResponseEntity<>("Can not update a Message", HttpStatus.UNAUTHORIZED);
			} else {
				if (!user.isTeacher() && !replies.isEmpty()) {
					return new ResponseEntity<>("Can not update a Message", HttpStatus.UNAUTHORIZED);
				} else {
					messageService.update(messageDTO);
					return new ResponseEntity<>("Message updated", HttpStatus.ACCEPTED);
				}
			}
		}
	}

	@RequestMapping(value = "/message", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> delete(
			@RequestParam("messageId") String messageId,
			HttpServletRequest httpServletRequest) {
		Message message = messageRepository
				.findOneById(new ObjectId(messageId));
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		if (message == null) {
			return new ResponseEntity<>("Message not exist", HttpStatus.NOT_FOUND);
		} else {
			List<Reply> replies = replyService
					.findAllByMessage(message.getId());
			Thread thread = threadRepository.findOneById(new ObjectId(message
					.getThreadId()));
			Group group = groupRepository.findOneById(new ObjectId(thread
					.getGroupId()));
			if (!user.isTeacher() && !replies.isEmpty()) {
				return new ResponseEntity<>("Can not delete a message with replies", HttpStatus.UNAUTHORIZED);
			} else {
				if (!user.isTeacher()
						&& !message.getUserId().equals(user.getId())) {
					return new ResponseEntity<>("Can not delete a message", HttpStatus.UNAUTHORIZED);
				} else if (user.isTeacher()
						&& !group.getTeacherId().equals(user.getId())) {
					return new ResponseEntity<>("Can not delete a message", HttpStatus.UNAUTHORIZED);
				} else {
					messageService.delete(messageId);
					return new ResponseEntity<>("Message deleted", HttpStatus.ACCEPTED);
				}
			}
		}
	}
}
