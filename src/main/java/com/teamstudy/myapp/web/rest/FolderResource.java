package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Folder;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.FolderRepository;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.FolderService;
import com.teamstudy.myapp.web.rest.dto.FolderDTO;

@RestController
@RequestMapping("/api")
public class FolderResource {

	@Inject
	private UserRepository userRepository;

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private FolderRepository folderRepository;

	@Inject
	private FolderService folderService;

	@RequestMapping(value = "/folder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public Folder getOne(@RequestParam("folderId") String folderId,
			HttpServletResponse response) {
		if (folderRepository.findOneById(new ObjectId(folderId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return folderRepository.findOneById(new ObjectId(folderId));
		}
	}

	@RequestMapping(value = "/folder/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Folder> getAllByGroup(@RequestParam("groupId") String groupId,
			HttpServletResponse response) {
		if (groupRepository.findOneById(new ObjectId(groupId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return folderService.findAllByGroup(groupId);
		}
	}

	@RequestMapping(value = "/folder", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(@Valid @RequestBody FolderDTO folderDTO,
			@RequestParam("groupId") String groupId) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			User user = userRepository.findOneByLogin(SecurityUtils
					.getCurrentLogin());
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return new ResponseEntity<>("Can not create a folder",
						HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return new ResponseEntity<>("Can not create a folder",
						HttpStatus.UNAUTHORIZED);
			} else {
				folderService.create(folderDTO, groupId);
				return new ResponseEntity<>("Folder created",
						HttpStatus.CREATED);
			}
		}
	}

	@RequestMapping(value = "/folder", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> update(@Valid @RequestBody FolderDTO folderDTO, @RequestParam("groupId") String groupId) {
		if(folderDTO.getId() == null){
			return create(folderDTO, groupId);
		}
		Group group = groupRepository.findOneById(new ObjectId(folderDTO
				.getGroupId()));
		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
			return new ResponseEntity<>("Can not update a folder",
					HttpStatus.UNAUTHORIZED);
		} else if (user.isTeacher()
				&& !group.getTeacherId().equals(user.getId())) {
			return new ResponseEntity<>("Can not update a folder",
					HttpStatus.UNAUTHORIZED);
		} else {
			folderService.update(folderDTO);
			return new ResponseEntity<>("Folder updated",
					HttpStatus.ACCEPTED);
		}
	}

	@RequestMapping(value = "/folder", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> delete(@RequestParam("folderId") String folderId)
			throws Exception {
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		if (folder == null) {
			return new ResponseEntity<>("Folder not exist", HttpStatus.NOT_FOUND);
		} else {
			Group group = groupRepository.findOneById(new ObjectId(folder
					.getGroupId()));
			User user = userRepository.findOneByLogin(SecurityUtils
					.getCurrentLogin());
			if (!user.isTeacher() && !group.getAlums().contains(user.getId())) {
				return new ResponseEntity<>("Can not delete a folder", HttpStatus.UNAUTHORIZED);
			} else if (user.isTeacher()
					&& !group.getTeacherId().equals(user.getId())) {
				return new ResponseEntity<>("Can not delete a folder", HttpStatus.UNAUTHORIZED);
			} else {
				if (!folder.getArchives().isEmpty()) {
					return new ResponseEntity<>("Can not delete a folder with files", HttpStatus.UNAUTHORIZED);
				} else {
					folderService.delete(folderId);
					return new ResponseEntity<>("Folder deleted", HttpStatus.ACCEPTED);
				}
			}
		}
	}

}
