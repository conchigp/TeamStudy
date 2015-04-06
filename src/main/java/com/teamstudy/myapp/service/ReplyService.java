package com.teamstudy.myapp.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.Reply;
import com.teamstudy.myapp.repository.ReplyRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.web.rest.dto.ReplyDTO;

@Service
public class ReplyService {
	
	@Inject
	private ReplyRepository replyRepository;
	
	@Inject
	private UserRepository userRepository;
	
	
	public List<Reply> findAllByMessage(String messageId){
		return replyRepository.findAllByMessageId(messageId);
	}

	/* POST Methods */

	public Reply create(ReplyDTO replyDTO, String messageId) {
		Reply reply = new Reply();
		reply.setCreationMoment(new Date());
		reply.setDescription(replyDTO.getDescription());
		reply.setMessageId(messageId);
		reply.setUserId(userRepository.findOneByLogin(
				SecurityUtils.getCurrentLogin()).getId().toString());
		replyRepository.save(reply);
		return reply;
	}

	public Reply update(ReplyDTO replyDTO) {
		Reply reply = replyRepository.findOneById(new ObjectId(replyDTO.getId()));
		reply.setDescription(replyDTO.getDescription());
		replyRepository.save(reply);
		return reply;	
	}

	public void delete(String replyId) {
		Reply reply = replyRepository.findOneById(new ObjectId(replyId));
		replyRepository.delete(reply);
	}

}
