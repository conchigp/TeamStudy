package com.teamstudy.myapp.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.teamstudy.myapp.config.DatabaseConfiguration;
import com.teamstudy.myapp.domain.Archive;
import com.teamstudy.myapp.domain.Folder;
import com.teamstudy.myapp.repository.FolderRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.SecurityUtils;


@Service
public class FolderService {
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private DatabaseConfiguration databaseConfiguration;
	
	@Inject FolderRepository folderRepository;
	
	public void addArchive(File file, String folderId) throws Exception{
		Folder folder = folderRepository.findOne(folderId);
		List<Archive> archives = folder.getArchives();	
		Archive archive = new Archive();
		
		archive.setCreationMoment(new Date());
		archive.setSize(file.getTotalSpace());
		archive.setTitle(file.getName());
		archive.setUrl(file.getPath());
		archive.setUserId(userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin()).getId());
		
		GridFS fs = connectDatabase();
		GridFSInputFile in = fs.createFile(file);
		in.save();
		
		archive.setGridId(in.getId());
		archives.add(archive);
		folder.setArchives(archives);
		folderRepository.save(folder);
	}
	
	//Other
	
	public GridFS connectDatabase() throws Exception{
		Mongo mongo = databaseConfiguration.mongo();
		DB db = mongo.getDB("teamstudy");
		GridFS fs = new GridFS(db);
		return fs;
	}
	
	public GridFSDBFile downloadFile(ObjectId objectId) throws Exception{
		GridFS fs = connectDatabase();
		GridFSDBFile file = fs.find(objectId);
		return file;
	}

}
