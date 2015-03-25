package com.teamstudy.myapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
	
	Group findOneById(String id);
	Group findOneByName(String name);

}
