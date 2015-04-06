package com.teamstudy.myapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.MessageChat;

public interface ChatRepository extends MongoRepository<MessageChat, String>{
	
	List<MessageChat> findAllByGroupId(String groupId);

	MessageChat findOneById(ObjectId objectId);

}
