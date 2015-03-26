package com.teamstudy.myapp.web.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mongodb.gridfs.GridFSDBFile;
import com.teamstudy.myapp.domain.Folder;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.FolderRepository;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.FolderService;

@RestController
@RequestMapping("/api")
public class FolderResource {

	private final Logger log = LoggerFactory.getLogger(FolderResource.class);

	@Inject
	private FolderService folderService;

	@Inject
	private FolderRepository folderRepository;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private UserRepository userRepository;

	@RequestMapping(value = "folders/{folderId}/add", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> addFileToFolder(@PathVariable String folderId,
			@RequestParam String filePath, HttpServletRequest request)
			throws Exception {
		Folder folder = folderRepository.findOne(folderId);
		if (filePath == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("Folder path can not be null");
		} else {
			if (folder == null) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("This folder does not exist");
			} else {
				Group group = groupRepository.findOne(folder.getGroupId());
				User user = userRepository.findOneByLogin(SecurityUtils
						.getCurrentLogin());
				if (!user.isTeacher()
						&& !group.getAlums().contains(user.getId())) {
					return ResponseEntity
							.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You do not have permission to upload files to this group(U)");
				} else if (user.isTeacher()
						&& !group.getTeacherId().equals(user.getId())) {
					return ResponseEntity
							.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You do not have permission to upload files to this group(T)");
				} else {
					File file = new File(filePath);
					folderService.addArchive(file, folderId);
					return ResponseEntity.ok("File has been upload");
				}
			}
		}
	}

	@RequestMapping(value = "folders/{folderId}/download/{gridId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> downloadFileFromFolder(
			@PathVariable String folderId, @PathVariable String gridId,
			HttpServletResponse response) throws Exception, IOException {
		Folder folder = folderRepository.findOne(folderId);
		if (folder == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This folder does not exist");
		} else {
			Group group = groupRepository.findOne(folder.getGroupId());
			User user = userRepository.findOneByLogin(SecurityUtils
					.getCurrentLogin());
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return ResponseEntity
						.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to upload files to this group(U)");
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return ResponseEntity
						.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to upload files to this group(T)");
			} else {
				ObjectId id = new ObjectId(gridId);
				GridFSDBFile file = folderService.downloadFile(id);
				if (file == null) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("The file does not exist");
				} else {
					response.setHeader("Content-Disposition","attachment;filename=" + file.getFilename());
					try{
						InputStream fileIn = file.getInputStream();
						ServletOutputStream out = response.getOutputStream();
						byte[] outputByte = new byte[4096];
						while (fileIn.read(outputByte, 0, 4096) != -1) {
							out.write(outputByte, 0, 4096);
						}
						fileIn.close();
						out.flush();
						out.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					return ResponseEntity.ok("Downloading");
				}
			}
		}
	}

}
