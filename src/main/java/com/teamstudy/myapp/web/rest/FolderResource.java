package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;

import org.bson.types.ObjectId;
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
	public Folder getOne(@RequestParam("folderId") String folderId){
		return folderRepository.findOneById(new ObjectId(folderId));
	}
	
	@RequestMapping(value = "/folders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Folder> getAllByGroup(@RequestParam("groupId") String groupId){
		return folderService.findAllByGroup(groupId);
	}
	
	@RequestMapping(value = "/folder", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(@Valid @RequestBody FolderDTO folderDTO, @RequestParam("groupId") String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if(group == null){
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		}else{
			User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
			if(!user.isTeacher() && !group.getAlums().contains(user.getId())){
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to create folder");
			}else if(user.isTeacher() && !group.getTeacherId().equals(user.getId())){
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to create folder");
			}else{
				folderService.create(folderDTO, groupId);
				return ResponseEntity.ok("Folder created");
			}
		}
	}
	
	@RequestMapping(value = "/folder", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(@Valid @RequestBody FolderDTO folderDTO){
		Group group = groupRepository.findOneById(new ObjectId(folderDTO.getGroupId()));
		User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
		if(!user.isTeacher() && !group.getAlums().contains(user.getId())){
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("You do not have permission to create folder");
		}else if(user.isTeacher() && !group.getTeacherId().equals(user.getId())){
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("You do not have permission to create folder");
		}else{
			folderService.update(folderDTO);
			return ResponseEntity.ok("Folder created");
		}
	}
	
	@RequestMapping(value = "/folder", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> create(@RequestParam("folderId") String folderId){
		Folder folder = folderRepository.findOneById(new ObjectId(folderId));
		if(folder == null){
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("Folder does not exist");
		}else{
			Group group = groupRepository.findOneById(new ObjectId(folder.getGroupId()));
			User user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
			if(!user.isTeacher() && !group.getAlums().contains(user.getId())){
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to create folder");
			}else if(user.isTeacher() && !group.getTeacherId().equals(user.getId())){
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You do not have permission to create folder");
			}else{
				if(!folder.getArchives().isEmpty()){
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("You can not to remove a folder with archives");
				}else{
					folderService.delete(folderId);
					return ResponseEntity.ok("Folder created");
				}
			}
		}
	}
	
	

}
