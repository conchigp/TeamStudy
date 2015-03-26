package com.teamstudy.myapp.web.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mongodb.gridfs.GridFSDBFile;
import com.teamstudy.myapp.domain.Archive;
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
public class ArchiveResource {

	@Inject
	private FolderService folderService;

	@Inject
	private FolderRepository folderRepository;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private UserRepository userRepository;
	
	/* GET Methods */
	
	@RequestMapping(value = "/archives", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Archive> getAllByFolder(@RequestParam("folderId") String folderId, HttpServletResponse httpServletResponse){
		return folderService.findAllByFolder(folderId);
	}
	@RequestMapping(value = "/archive/{folderId}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Archive getOne(@PathVariable String folderId, @RequestParam("gridId") String gridId, HttpServletResponse httpServletResponse){
		return folderService.findOne(gridId, folderId);
	}
	
	@RequestMapping(value = "/archive/download/{folderId}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> download(
			@PathVariable String folderId,
			@RequestParam("gridId") String gridId, HttpServletResponse response)
			throws Exception, IOException {
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
				GridFSDBFile file = folderService.download(gridId);
				if (file == null) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("The file does not exist");
				} else {
					response.setHeader("Content-Disposition",
							"attachment;filename=" + file.getFilename());
					try {
						InputStream fileIn = file.getInputStream();
						ServletOutputStream out = response.getOutputStream();
						byte[] outputByte = new byte[4096];
						while (fileIn.read(outputByte, 0, 4096) != -1) {
							out.write(outputByte, 0, 4096);
						}
						fileIn.close();
						out.flush();
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return ResponseEntity.ok("Downloading");
				}
			}
		}
	}
	
	/* POST Methods */

	@RequestMapping(value = "/archive/{folderId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> upload(@PathVariable String folderId,
			@RequestParam("filePath") String filePath,
			HttpServletRequest request) throws Exception {
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
					folderService.add(file, folderId);
					return ResponseEntity.ok("File has been upload");
				}
			}
		}
	}
	
	@RequestMapping(value = "/archive/{folderId}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> delete(@PathVariable String folderId,
			@RequestParam("gridId") String gridId,
			HttpServletRequest request) throws Exception {
		Folder folder = folderRepository.findOne(folderId);
		User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
		Archive archive = folderService.findOne(gridId, folderId);
		Group group = groupRepository.findOne(folder.getGroupId());
		if(archive == null){
			return ResponseEntity
					.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This archive does not exist");
		}else{
			if(!user.isTeacher() && !archive.getUserId().equals(user.getId())){
				return ResponseEntity
						.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to delete this file");
			} else if(user.isTeacher() && !user.getId().equals(group.getTeacherId())){
				return ResponseEntity
						.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to delete this file");
			} else{
				folderService.remove(folderId, gridId);
				return ResponseEntity.ok("Archive deleted");
			}
		}
	}
}
