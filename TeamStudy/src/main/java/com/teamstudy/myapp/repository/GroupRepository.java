package com.teamstudy.myapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Group;


public interface GroupRepository extends MongoRepository<Group, String>{

}
