package com.teamstudy.myapp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

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
import com.teamstudy.myapp.web.rest.dto.FolderDTO;

@Service
public class FolderService {

	@Inject
	private UserRepository userRepository;

	@Inject
	private DatabaseConfiguration databaseConfiguration;

	@Inject
	private FolderRepository folderRepository;

	/**
	 * 
	 * Folder Methods
	 *
	 */

	/* GET Methods */

	public List<Folder> findAllByGroup(String groupId) {
		return folderRepository.findAllByGroupId(groupId);
	}

	/* POST Methods */

	public Folder create(FolderDTO folderDTO, String groupId) {
		Folder folder = new Folder();
		folder.setTitle(folderDTO.getTitle());
		folder.setCreationMoment(new Date());
		folder.setGroupId(groupId);
		folder.setArchives(new ArrayList<Archive>());
		folderRepository.save(folder);
		return folder;
	}

	public Folder update(FolderDTO folderDTO) {
		Folder folder = folderRepository.findOne(folderDTO.getId());
		folder.setTitle(folderDTO.getTitle());
		folderRepository.save(folder);
		return folder;
	}

	public void delete(String folderId) {
		Folder folder = folderRepository.findOne(folderId);
		folderRepository.delete(folder);
	}

	/**
	 * 
	 * Archive Methods
	 *
	 */
	
	/* GET Mehotds */
	
	public Archive findOne(String gridId, String folderId){
		Folder folder = folderRepository.findOne(folderId);
		Archive archive = new Archive();
		for (Archive a: folder.getArchives()){
			if(a.getGridId().equals(gridId)){
				archive = a;
			}
		}
		return archive;
	}
	
	/* POST Methods */ 

	public void add(File file, String folderId) throws Exception {
		Folder folder = folderRepository.findOne(folderId);
		List<Archive> archives = folder.getArchives();
		Archive archive = new Archive();

		archive.setCreationMoment(new Date());
		archive.setSize(file.getTotalSpace());
		archive.setTitle(file.getName());
		archive.setUserId(userRepository.findOneByLogin(
				SecurityUtils.getCurrentLogin()).getId());

		GridFS fs = connectDatabase();
		GridFSInputFile in = fs.createFile(file);
		in.save();

		archive.setGridId(in.getId().toString());
		archives.add(archive);
		folder.setArchives(archives);
		folderRepository.save(folder);
	}

	/**
	 * 
	 * Database Methods
	 *
	 */

	public GridFS connectDatabase() throws Exception {
		Mongo mongo = databaseConfiguration.mongo();
		DB db = mongo.getDB("teamstudy");
		GridFS fs = new GridFS(db);
		return fs;
	}

	public GridFSDBFile download(String objectId) throws Exception {
		GridFS fs = connectDatabase();
		GridFSDBFile file = fs.find(new ObjectId(objectId));
		return file;
	}

}
