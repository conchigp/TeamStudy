package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.service.GroupService;
import com.teamstudy.myapp.web.rest.dto.GroupDTO;

@RestController
@RequestMapping("/api/groups")
public class GroupResource {

	private final Logger log = LoggerFactory.getLogger(GroupResource.class);

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;

	@Inject
	private UserRepository userRepository;

	// Get students from group
	@RequestMapping(value = "/{groupId}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<User> getAllByGroup(@PathVariable String groupId,
			HttpServletResponse response) {
		log.debug("REST request to get all Users");
		return groupService.getStudentsByGroup(groupId);
	}

	// Delete student from group
	@RequestMapping(value = "/{groupId}/remove/{studentId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> removeStudentFromGroup(
			@PathVariable String groupId, @PathVariable String studentId,
			HttpServletRequest request) {
		Group group = groupRepository.findOne(groupId);
		User student = userRepository.findOne(studentId);
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			if (student == null) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("This student does not exist");
			} else {
				if (student.isTeacher()) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("This user is not a student");
				} else {
					if (!group.getAlums().contains(studentId)) {
						return ResponseEntity.badRequest()
								.contentType(MediaType.TEXT_PLAIN)
								.body("This student is not in the group");
					} else {
						groupService.deleteStudentFromGroup(studentId, groupId);
						return ResponseEntity.ok("Student removed");
					}
				}
			}
		}

	}

	// Add student from group
	@RequestMapping(value = "/{groupId}/add/{studentId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> addStudentToGroup(@PathVariable String groupId,
			@PathVariable String studentId, HttpServletRequest request) {
		Group group = groupRepository.findOne(groupId);
		User student = userRepository.findOne(studentId);
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			if (student == null) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("This student does not exist");
			} else {
				if (student.isTeacher()) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("This user is not a student");
				} else {
					if (group.getAlums().contains(studentId)) {
						return ResponseEntity.badRequest()
								.contentType(MediaType.TEXT_PLAIN)
								.body("This student is already in the group");
					} else {
						groupService.addStudentToGroup(studentId, groupId);
						return ResponseEntity.ok("Student added");
					}
				}
			}
		}
	}

	// Add teacher from group
	@RequestMapping(value = "/{groupId}/addT/{teacherId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> addTeacherToGroup(@PathVariable String groupId,
			@PathVariable String teacherId, HttpServletRequest request) {
		Group group = groupRepository.findOne(groupId);
		User teacher = userRepository.findOne(teacherId);
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			if (teacher == null) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("This teacher does not exist");
			} else {
				if (!teacher.isTeacher()) {
					return ResponseEntity.badRequest()
							.contentType(MediaType.TEXT_PLAIN)
							.body("This user is not a teacher");
				} else {
					if (group.getTeacherId() != null) {
						return ResponseEntity.badRequest()
								.contentType(MediaType.TEXT_PLAIN)
								.body("This group has already a teacher");
					} else {
						groupService.addTeacherToGroup(teacherId, groupId);
						return ResponseEntity.ok("Teacher added");
					}
				}
			}
		}
	}

	// Remove teacher from group
	@RequestMapping(value = "/{groupId}/removeT", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> removeTeacherFromGroup(@PathVariable String groupId, HttpServletRequest request) {
		Group group = groupRepository.findOne(groupId);
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			if (group.getTeacherId() == null) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("This group has not a teacher");
			} else {
				groupService.deleteTeacherFromGroup(groupId);
				return ResponseEntity.ok("Teacher removed");
			}
		}
	}
	
	 //create group.(MIO)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDTO groupDTO, HttpServletRequest request) {
        Group group = groupRepository.findOneByName(groupDTO.getName());
        if (group != null) {
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("existing group");
        } else {
            group = groupService.createGroup(groupDTO.getName(), groupDTO.getDescription(), groupDTO.getTeacherId(), 
            	    groupDTO.getAlums(), groupDTO.getWiki());

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    
    //update the current group information (MIO)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<String> updateGroup(@RequestBody GroupDTO groupDTO) {
        Group group = groupRepository.findOneByName(groupDTO.getName());
        if (group == null) {
        	return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("nonexistent group");
        }
        groupService.updateGroupInformation(group.getId(), groupDTO.getName(), groupDTO.getDescription(), 
        		groupDTO.getTeacherId(), groupDTO.getAlums(), groupDTO.getWiki());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    //delete group (MIO)
    @RequestMapping(value = "/delete//{groupId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public ResponseEntity<String> updateGroup(@PathVariable String groupId, HttpServletRequest request) {
        Group group = groupRepository.findOneById(groupId);
        if (group == null) {
        	return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("nonexistent group");
        }
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
