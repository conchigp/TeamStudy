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

	@RequestMapping(value = "/reply/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Reply> getAllByMessage(@RequestParam("messageId") String messageId,
			HttpServletResponse response) {
		if (messageRepository.findOneById(new ObjectId(messageId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return replyService.findAllByMessage(messageId);
		}
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Reply getOne(@RequestParam("replyId") String replyId,
			HttpServletResponse response) {
		if (replyRepository.findOneById(new ObjectId(replyId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return replyRepository.findOneById(new ObjectId(replyId));
		}
	}
	
	/* POST Methods */
	
	@RequestMapping(value = "/reply", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(
			@Valid @RequestBody ReplyDTO replyDTO,
			@RequestParam("messageId") String messageId, HttpServletRequest httpServletRequest) {
		User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
		Message message = messageRepository.findOneById(new ObjectId(messageId));
		if (message == null) {
			return new ResponseEntity<>("Message not exist", HttpStatus.NOT_FOUND);
		} else {
			Thread thread = threadRepository.findOneById(new ObjectId(message.getThreadId()));
			Group group = groupRepository.findOneById(new ObjectId(thread.getGroupId()));
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return new ResponseEntity<>("Can not create a Reply", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return new ResponseEntity<>("Can not create a Reply", HttpStatus.UNAUTHORIZED);
			} else {
				replyService.create(replyDTO, messageId);
				return new ResponseEntity<>("Message created", HttpStatus.CREATED);
			}
		}
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> update(
			@Valid @RequestBody ReplyDTO replyDTO, @RequestParam("messageId") String messageId,
			HttpServletRequest httpServletRequest) {
		if(replyDTO.getId() == null){
			return create(replyDTO, messageId, httpServletRequest);
		}
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Message message = messageRepository.findOneById(new ObjectId(replyDTO.getMessageId()));
		Reply reply = replyRepository.findOneById(new ObjectId(replyDTO.getId()));
		if (reply == null) {
			return new ResponseEntity<>("Reply not exist", HttpStatus.NOT_FOUND);
		} else {
			Thread thread = threadRepository.findOneById(new ObjectId(message.getThreadId()));
			Group group = groupRepository.findOneById(new ObjectId(thread.getGroupId()));
			if (!user.isTeacher()
					&& !replyDTO.getUserId().equals(user.getId())) {
				return new ResponseEntity<>("You can not update this reply", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return new ResponseEntity<>("You can not update this reply", HttpStatus.UNAUTHORIZED);
			} else {
				replyService.update(replyDTO);
				return new ResponseEntity<>("Reply updated", HttpStatus.ACCEPTED);
			}
		}
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> delete(
			@RequestParam("replyId") String replyId,
			HttpServletRequest httpServletRequest) {

		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Reply reply = replyRepository.findOneById(new ObjectId(replyId));
		if (reply == null) {
			return new ResponseEntity<>("Reply not exist", HttpStatus.NOT_FOUND);
		} else {
			Message message = messageRepository.findOneById(new ObjectId(reply.getMessageId()));
			Thread thread = threadRepository.findOneById(new ObjectId(message.getThreadId()));
			Group group = groupRepository.findOneById(new ObjectId(thread.getGroupId()));
			if (!user.isTeacher()
					&& !reply.getUserId().equals(user.getId())) {
				return new ResponseEntity<>("You can not delete this reply", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return new ResponseEntity<>("You can not delete this reply", HttpStatus.UNAUTHORIZED);
			} else {
				replyService.delete(replyId);
				return new ResponseEntity<>("Reply delete", HttpStatus.ACCEPTED);
			}
		}
	}

}
