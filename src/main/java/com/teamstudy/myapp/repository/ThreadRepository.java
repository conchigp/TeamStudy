package com.teamstudy.myapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Thread;

public interface ThreadRepository extends MongoRepository<Thread, String>{
	
	List<Thread> findAllByGroupId(String groupId);
	
	Thread findOneById(ObjectId objectId);
	
}