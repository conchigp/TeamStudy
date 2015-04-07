package com.teamstudy.myapp.service;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

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
		Folder folder = folderRepository.findOneById(new ObjectId(folderDTO.getId()));
		folder.setTitle(folderDTO.getTitle());
		folderRepository.save(folder);
		return folder;
	}

	public void delete(String folderId) throws Exception {
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		List<Archive> archives = findAllByFolder(folderId);
		for(Archive a : archives){
			remove(folderId, a.getGridId());
		}
		folderRepository.delete(folder);
	}

	/**
	 * 
	 * Archive Methods
	 *
	 */
	
	/* GET Methods */
	
	public Archive findOne(String gridId, String folderId){
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		Archive archive = null;
		for (Archive a: folder.getArchives()){
			if(a.getGridId().equals(gridId)){
				archive = a;
			}
		}
		return archive;
	}
	
	public List<Archive> findAllByFolder(String folderId){
		return folderRepository.findOneById(new ObjectId(folderId)).getArchives();
	}
	
	public boolean existFile(String gridId) throws Exception{
		GridFS fs = connectDatabase();
		GridFSDBFile file = fs.find(new ObjectId(gridId));
		if(file != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * Method 1
	 * 
	 */
	/*public OutputStream download(String objectId, HttpServletResponse response) throws Exception {
		GridFS fs = connectDatabase();
		GridFSDBFile file = fs.find(new ObjectId(objectId));
		InputStream is = file.getInputStream();
		response.addHeader("Content-Type", "application/octet-stream");
		response.addHeader("Content-Disposition", "attachment; filename={}");
		int read = 0;
		byte[] bytes = new byte[4096];
		OutputStream os = response.getOutputStream();
		while ((read = is.read(bytes)) != -1) {
			os.write(bytes, 0, read);
		}
		return os;
	}*/
	
	/**
	 * 
	 * Method 2
	 * 
	 */
	
	public long download(String objectId, HttpServletResponse response) throws Exception {
		GridFS fs = connectDatabase();
		GridFSDBFile file = fs.find(new ObjectId(objectId));
		response.addHeader("Content-Type", "application/octet-stream");
		response.addHeader("Content-Disposition", "attachment; filename="+file.getFilename());
		OutputStream os = response.getOutputStream();
		return file.writeTo(os);
	}
	
	
	/* POST Methods */ 

	public void add(File file, String folderId) throws Exception {
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		List<Archive> archives = folder.getArchives();
		Archive archive = new Archive();

		archive.setCreationMoment(new Date());
		archive.setSize(file.getTotalSpace());
		archive.setTitle(file.getName().substring(0,file.getName().lastIndexOf(".")));
		archive.setUserId(userRepository.findOneByLogin(
				SecurityUtils.getCurrentLogin()).getId().toString());
		
		archive.setFormat(file.getName().substring(file.getName().lastIndexOf(".")+1));
		GridFS fs = connectDatabase();
		GridFSInputFile in = fs.createFile(file);
		in.save();

		archive.setGridId(in.getId().toString());
		archives.add(archive);
		folder.setArchives(archives);
		folderRepository.save(folder);
	}
	
	public void remove(String folderId, String gridId) throws Exception{	
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		List<Archive> archives = folder.getArchives();
		GridFS fs = connectDatabase();
		fs.remove(new ObjectId(gridId));
		for(Archive a: archives){
			if(a.getGridId().equals(gridId)){
				archives.remove(a);
				break;
			}
		}
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

}
