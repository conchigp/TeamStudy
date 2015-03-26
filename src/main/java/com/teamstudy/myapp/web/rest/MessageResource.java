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

	@RequestMapping(value = "/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Message> getAllByThread(
			@RequestParam("threadId") String threadId,
			HttpServletResponse response) {
		return messageService.findAllByThread(threadId);
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Message getOne(@RequestParam("messageId") String messageId,
			HttpServletResponse response) {
		return messageRepository.findOne(messageId);
	}

	/* POST Methods */

	@RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> createMessage(
			@Valid @RequestBody MessageDTO messageDTO,
			@RequestParam("threadId") String threadId,
			HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Thread thread = threadRepository.findOne(threadId);
		if (thread == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This thread does not exist");
		} else {
			Group group = groupRepository.findOne(thread.getGroupId());
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not create a Message in this group");
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not create a Message in this group");
			} else {
				messageService.create(messageDTO, threadId);
				return ResponseEntity.ok("Message created");
			}
		}
	}

	@RequestMapping(value = "/message", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> updateMessage(
			@Valid @RequestBody MessageDTO messageDTO,
			HttpServletRequest httpServletRequest) {

		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Message message = messageRepository.findOne(messageDTO.getId());
		List<Reply> replies = replyService.findAllByMessage(messageDTO.getId());
		if (message == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This thread does not exist");
		} else {
			Thread thread = threadRepository.findOne(message.getThreadId());
			Group group = groupRepository.findOne(thread.getGroupId());
			if (!user.isTeacher()
					&& !messageDTO.getUserId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not update a Message in this group");
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not update a Message in this group");
			} else {
				if (!user.isTeacher() && !replies.isEmpty()) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not update a Message with replies");
				} else {
					messageService.update(messageDTO);
					return ResponseEntity.ok("Message updated");
				}
			}
		}
	}

	@RequestMapping(value = "/message", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> deleteMessage(
			@RequestParam("messageId") String messageId,
			HttpServletRequest httpServletRequest) {
		Message message = messageRepository.findOne(messageId);
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		if (message == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This message does not exist");
		} else {
			List<Reply> replies = replyService
					.findAllByMessage(message.getId());
			Thread thread = threadRepository.findOne(message.getThreadId());
			Group group = groupRepository.findOne(thread.getGroupId());
			if (!user.isTeacher() && !replies.isEmpty()) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not delete a thread with messages");
			} else {
				if (!user.isTeacher()
						&& !message.getUserId().equals(user.getId())) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not update a Message in this group");
				} else if (user.isTeacher()
						&& !group.getTeacherId().equals(user.getId())) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not update a Message in this group");
				} else {
					messageService.delete(messageId);
					return ResponseEntity.ok("Message deleted");
				}
			}
		}
	}
}
