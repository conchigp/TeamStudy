package com.teamstudy.myapp.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.Message;
import com.teamstudy.myapp.domain.Reply;
import com.teamstudy.myapp.repository.MessageRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.web.rest.dto.MessageDTO;

@Service
public class MessageService {

	@Inject
	private MessageRepository messageRepository;

	@Inject
	private UserRepository userRepository;
	
	@Inject
	private ReplyService replyService;

	/* GET Methods */

	public List<Message> findAllByThread(String threadId) {
		return messageRepository.findAllByThreadId(threadId);
	}

	/* POST Methods */

	public Message create(MessageDTO messageDTO, String threadId) {
		Message message = new Message();
		message.setCreationMoment(new Date());
		message.setDescription(messageDTO.getDescription());
		message.setThreadId(threadId);
		message.setUserId(userRepository.findOneByLogin(
				SecurityUtils.getCurrentLogin()).getId().toString());
		messageRepository.save(message);
		return message;
	}

	public Message update(MessageDTO messageDTO) {
		Message message = messageRepository.findOneById(new ObjectId(messageDTO.getId()));
		message.setDescription(messageDTO.getDescription());
		messageRepository.save(message);
		return message;
	}

	public void delete(String messageId) {
		Message message = messageRepository.findOneById(new ObjectId(messageId));
		List<Reply> replies = replyService.findAllByMessage(messageId);
		for(Reply r : replies){
			replyService.delete(r.getId().toString());
		}
		messageRepository.delete(message);
	}
}
