package com.teamstudy.myapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Message;

public interface MessageRepository extends MongoRepository<Message, String>{
	
	List<Message> findAllByThreadId(String threadId);
	
	Message findOneById(ObjectId objectId);

}
