package com.teamstudy.myapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.New;

public interface NewRepository extends MongoRepository<New, String>{

	New findOneById(ObjectId objectId);
	
	List<New> findAllByGroupId(String groupId);
}

