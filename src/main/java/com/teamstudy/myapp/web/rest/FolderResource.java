package com.teamstudy.myapp.web.rest;

import java.io.File;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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
import com.teamstudy.myapp.repository.FolderRepository;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.FolderService;

@RestController
@RequestMapping("/api/folders")
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

	@RequestMapping(value = "/{folderId}/add", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
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
				String userId = userRepository.findOneByLogin(
						SecurityUtils.getCurrentLogin()).getId();
				if (!group.getAlums().contains(userId)) {
					return ResponseEntity
							.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You do not have permission to upload files to this group");
				} else {
					File file = new File(filePath);
					folderService.addArchive(file, folderId);
					return ResponseEntity.ok("File has been upload");
				}
			}
		}
	}

	@RequestMapping(value = "/{folderId}/download/{gridId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> downloadFileFromFolder(
			@PathVariable String folderId, @PathVariable String gridId, String downloadPath,
			HttpServletRequest request) throws Exception {
		Folder folder = folderRepository.findOne(folderId);
		if (folder == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This folder does not exist");
		} else {
			Group group = groupRepository.findOne(folder.getGroupId());
			String userId = userRepository.findOneByLogin(
					SecurityUtils.getCurrentLogin()).getId();
			if (!group.getAlums().contains(userId)) {
				return ResponseEntity
						.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to download files from this group");
			} else {
				ObjectId id = new ObjectId(gridId);
				GridFSDBFile file = folderService.downloadFile(id);
				if (file == null) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("The file does not exist");
				} else {
					if(downloadPath == null){
						return ResponseEntity.badRequest()
								.contentType(MediaType.TEXT_PLAIN)
								.body("Download path is required");
					}else{
						return ResponseEntity.ok("Downloading");
					}
				}
			}
		}
	}

}
