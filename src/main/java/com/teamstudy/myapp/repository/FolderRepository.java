package com.teamstudy.myapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.teamstudy.myapp.domain.Folder;

public interface FolderRepository extends MongoRepository<Folder, String>{
	
	List<Folder> findAllByGroupId(String groupId);

	Folder findOneById(ObjectId objectId);

}
