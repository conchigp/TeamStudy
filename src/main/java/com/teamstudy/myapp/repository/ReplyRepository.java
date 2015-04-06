package com.teamstudy.myapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Reply;

public interface ReplyRepository extends MongoRepository<Reply, String>{
	
	List<Reply> findAllByMessageId(String messageId);
	
	Reply findOneById(ObjectId objectId);

}
