package com.teamstudy.myapp.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.MessageChat;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.ChatRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.web.rest.dto.ChatDTO;

@Service
public class ChatService {

	@Inject
	private ChatRepository chatRepository;

	@Inject
	private UserRepository userRepository;

	/* GET Methods */

	public List<MessageChat> findAllByGroup(String groupId) {
		return chatRepository.findAllByGroupId(groupId);
	}

	/* POST Methods */

	public MessageChat create(ChatDTO chatDTO, String groupId) {
		MessageChat chat = new MessageChat();
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		chat.setContent(chatDTO.getContent());
		chat.setGroupId(groupId);
		chat.setCreationMoment(new Date());
		chat.setUserId(user.getId().toString());
		List<MessageChat> limit = chatRepository.findAllByGroupId(groupId);
		if (limit.size() == 20) {
			MessageChat delete = chat;
			for (MessageChat mc : limit) {
				if (mc.getCreationMoment()
						.compareTo(delete.getCreationMoment()) < 0) {
					delete = mc;
				}
			}
			chatRepository.delete(delete);
		}
		chatRepository.save(chat);
		return chat;
	}
	
	public void delete(String groupId){
		List<MessageChat> messages = findAllByGroup(groupId);
		for(MessageChat m : messages){
			chatRepository.delete(m);
		}
	}

}
