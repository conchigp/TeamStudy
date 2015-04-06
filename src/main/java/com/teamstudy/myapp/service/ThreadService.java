package com.teamstudy.myapp.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.Message;
import com.teamstudy.myapp.domain.Thread;
import com.teamstudy.myapp.repository.ThreadRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.web.rest.dto.ThreadDTO;

@Service
public class ThreadService {
	
	private final Logger log = LoggerFactory.getLogger(ThreadService.class);

	@Inject
	private ThreadRepository threadRepository;
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private MessageService messageService;
	
	/* GET Methods */
	
	public List<Thread> findAllByGroup(String groupId){
		return threadRepository.findAllByGroupId(groupId);
	}
	
	/* POST Methods */
	
	public Thread create(ThreadDTO threadDTO, String groupId){
		Thread thread = new Thread();
		thread.setUserId(userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()).getId().toString());
		thread.setCreationMoment(new Date());
		thread.setDescription(threadDTO.getDescription());
		thread.setGroupId(groupId);
		thread.setTitle(threadDTO.getTitle());
		threadRepository.save(thread);
		log.debug("Created Information for Thread: {}", thread);
		return thread;
	}
	
	public Thread update(ThreadDTO threadDTO){
		Thread thread = threadRepository.findOneById(new ObjectId(threadDTO.getId()));
		thread.setDescription(threadDTO.getDescription());
		thread.setTitle(threadDTO.getTitle());
		threadRepository.save(thread);
		return thread;
	}
	
	public void delete(String threadId){
		Thread thread = threadRepository.findOneById(new ObjectId(threadId));
		List<Message> messages = messageService.findAllByThread(threadId);
		for(Message m : messages){
			messageService.delete(m.getId().toString());
		}
		threadRepository.delete(thread);
	}
}
