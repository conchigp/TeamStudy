package com.teamstudy.myapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.New;

public interface NewRepository extends MongoRepository<New, String>{

	New findOneById(String id);
}

