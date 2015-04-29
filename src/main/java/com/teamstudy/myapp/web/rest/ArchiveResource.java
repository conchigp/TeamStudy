package com.teamstudy.myapp.web.rest;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
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

	@RequestMapping(value = "/archive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Archive> getAllByFolder(
			@RequestParam("folderId") String folderId,
			HttpServletResponse httpServletResponse) {
		return folderService.findAllByFolder(folderId);
	}

	@RequestMapping(value = "/archive/{folderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Archive getOne(@PathVariable String folderId,
			@RequestParam("gridId") String gridId,
			HttpServletResponse httpServletResponse){
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		if(folder == null){
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}else{
			Archive archive = folderService.findOne(gridId, folderId);
			if(archive == null){
				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}else{
				return folderService.findOne(gridId, folderId);
			}
		}
	}

	@RequestMapping(value = "/archive/download/{folderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> download(@PathVariable String folderId,
			@RequestParam("gridId") String gridId, HttpServletResponse response)
			throws Exception, IOException {
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		if (folder == null) {
			return new ResponseEntity<>("Folder not exist", HttpStatus.NOT_FOUND);
		} else {
			if (!folderService.existFile(gridId)) {
				return new ResponseEntity<>("File not exist", HttpStatus.NOT_FOUND);
			}else{
				Group group = groupRepository.findOneById(new ObjectId(folder.getGroupId()));
				User user = userRepository.findOneByLogin(SecurityUtils
						.getCurrentLogin());
				if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
					return new ResponseEntity<>("Can not download a file", HttpStatus.UNAUTHORIZED);
				} else if (user.isTeacher()
						&& !group.getTeacherId().equals(user.getId())) {
					return new ResponseEntity<>("Can not download a file", HttpStatus.UNAUTHORIZED);
				} else {
					/*OutputStream os = folderService.download(gridId, response);
					os.flush();
					os.close();*/
					folderService.download(gridId, response);
					return new ResponseEntity<>("Downloading", HttpStatus.OK);
				}
			}
		}
	}

	/* POST Methods */

	@RequestMapping(value = "/archive", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> upload(@RequestParam("folderId") String folderId, @RequestBody MultipartFile file,
			HttpServletRequest request) throws Exception {
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		if (file == null) {
			return new ResponseEntity<>("Filepath can not be null", HttpStatus.BAD_REQUEST);
		} else {
			if (folder == null) {
				return new ResponseEntity<>("Folder not exist", HttpStatus.NOT_FOUND);
			} else {
				Group group = groupRepository.findOneById(new ObjectId(folder.getGroupId()));
				User user = userRepository.findOneByLogin(SecurityUtils
						.getCurrentLogin());
				if (!user.isTeacher()
						&& !group.getAlums().contains(user.getId())) {
					return new ResponseEntity<>("Can not upload a file", HttpStatus.UNAUTHORIZED);
				} else if (user.isTeacher()
						&& !group.getTeacherId().equals(user.getId())) {
					return new ResponseEntity<>("Can not upload a file", HttpStatus.UNAUTHORIZED);
				} else {
					folderService.add(file, folderId);
					return new ResponseEntity<>("File uploaded", HttpStatus.CREATED);
				}
			}
		}
	}

	@RequestMapping(value = "/archive", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> delete(@RequestParam("folderId") String folderId,
			@RequestParam("gridId") String gridId, HttpServletRequest request)
			throws Exception {
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		Archive archive = folderService.findOne(gridId, folderId);
		Group group = groupRepository.findOneById(new ObjectId(folder.getGroupId()));
		if (archive == null) {
			return new ResponseEntity<>("Archive not exist", HttpStatus.NOT_FOUND);
		} else {
			if (!user.isTeacher() && !archive.getUserId().equals(user.getId())) {
				return new ResponseEntity<>("Can not delete this file", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !user.getId().equals(group.getTeacherId())) {
				return new ResponseEntity<>("Can not delete this file", HttpStatus.UNAUTHORIZED);
			} else {
				folderService.remove(folderId, gridId);
				return new ResponseEntity<>("Archive deleted", HttpStatus.ACCEPTED);
			}
		}
	}
}
