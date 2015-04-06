package com.teamstudy.myapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
	
	Group findOneById(ObjectId objectId);
	Group findOneByName(String name);

}
