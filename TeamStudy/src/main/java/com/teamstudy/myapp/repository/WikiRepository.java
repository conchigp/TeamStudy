package com.teamstudy.myapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Group;

public interface WikiRepository extends MongoRepository<Group, String> {
	
	Group findOneById(String id);
	Group findOneByName(String name);

}
