package com.teamstudy.myapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Folder;

public interface FolderRepository extends MongoRepository<Folder, String>{

}
