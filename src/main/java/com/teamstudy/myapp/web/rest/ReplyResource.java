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
import com.teamstudy.myapp.repository.ReplyRepository;
import com.teamstudy.myapp.repository.ThreadRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.ReplyService;
import com.teamstudy.myapp.web.rest.dto.ReplyDTO;

@RestController
@RequestMapping("/api")
public class ReplyResource {

	@Inject
	private UserRepository userRepository;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private ThreadRepository threadRepository;

	@Inject
	private MessageRepository messageRepository;

	@Inject
	private ReplyRepository replyRepository;

	@Inject
	private ReplyService replyService;

	/* GET Methods */

	@RequestMapping(value = "/replies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Reply> getAllByThread(@RequestParam("messageId") String messageId,
			HttpServletResponse response) {
		return replyService.findAllByMessage(messageId);
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Reply getOne(@RequestParam("replyId") String replyId,
			HttpServletResponse response) {
		return replyRepository.findOne(replyId);
	}
	
	/* POST Methods */
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> createReply(
			@Valid @RequestBody ReplyDTO replyDTO,
			@RequestParam("messageId") String messageId, HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
		Message message = messageRepository.findOne(messageId);
		if (message == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This message does not exist");
		} else {
			Thread thread = threadRepository.findOne(message.getThreadId());
			Group group = groupRepository.findOne(thread.getGroupId());
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not create a Reply in this group");
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not create a Reply in this group");
			} else {
				replyService.create(replyDTO, messageId);
				return ResponseEntity.ok("Message created");
			}
		}
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> updateReply(
			@Valid @RequestBody ReplyDTO replyDTO,
			HttpServletRequest httpServletRequest) {

		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Message message = messageRepository.findOne(replyDTO.getMessageId());
		Reply reply = replyRepository.findOne(replyDTO.getId());
		if (reply == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This thread does not exist");
		} else {
			Thread thread = threadRepository.findOne(message.getThreadId());
			Group group = groupRepository.findOne(thread.getGroupId());
			if (!user.isTeacher()
					&& !replyDTO.getUserId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not update this reply");
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not update this reply");
			} else {
				replyService.update(replyDTO);
				return ResponseEntity.ok("Reply updated");
			}
		}
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> deleteReply(
			@RequestParam("replyId") String replyId,
			HttpServletRequest httpServletRequest) {

		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Reply reply = replyRepository.findOne(replyId);
		if (reply == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This thread does not exist");
		} else {
			Message message = messageRepository.findOne(reply.getMessageId());
			Thread thread = threadRepository.findOne(message.getThreadId());
			Group group = groupRepository.findOne(thread.getGroupId());
			if (!user.isTeacher()
					&& !reply.getUserId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not delete this reply");
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not delete this reply");
			} else {
				replyService.delete(replyId);
				return ResponseEntity.ok("Reply delete");
			}
		}
	}

}
