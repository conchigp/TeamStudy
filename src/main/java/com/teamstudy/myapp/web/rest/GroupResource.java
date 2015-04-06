package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.GroupService;
import com.teamstudy.myapp.web.rest.dto.GroupDTO;

@RestController
@RequestMapping("/api")
public class GroupResource {

	private final Logger log = LoggerFactory.getLogger(GroupResource.class);

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;

	@Inject
	private UserRepository userRepository;

	// Get students from group
	@RequestMapping(value = "/group", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<User> getAllByGroup(@RequestParam("groupId") String groupId,
			HttpServletResponse response) {
		log.debug("REST request to get all Users");
		return groupService.getStudentsByGroup(groupId);
	}

	// Delete student from group
	@RequestMapping(value = "/group/{groupId}/remove", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> deleteStudent(@PathVariable String groupId,
			@RequestParam("studentId") String studentId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		User student = userRepository.findOneById(new ObjectId(studentId));
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
						groupService.deleteStudent(studentId, groupId);
						return ResponseEntity.ok("Student removed");
					}
				}
			}
		}

	}

	// Add student from group
	@RequestMapping(value = "/group/{groupId}/add", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> addStudent(@PathVariable String groupId,
			@RequestParam("studentId") String studentId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		User student = userRepository.findOneById(new ObjectId(studentId));
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
						groupService.addStudent(studentId, groupId);
						return ResponseEntity.ok("Student added");
					}
				}
			}
		}
	}

	// Add teacher from group
	@RequestMapping(value = "/group/{groupId}/add", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> addTeacher(@PathVariable String groupId,
			@RequestParam("teacherId") String teacherId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		User teacher = userRepository.findOneById(new ObjectId(teacherId));
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
						groupService.addTeacher(teacherId, groupId);
						return ResponseEntity.ok("Teacher added");
					}
				}
			}
		}
	}

	// Remove teacher from group
	@RequestMapping(value = "/group/{groupId}/remove", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> deleteTeacher(@PathVariable String groupId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
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
				groupService.deleteTeacher(groupId);
				return ResponseEntity.ok("Teacher removed");
			}
		}
	}

	@RequestMapping(value = "/group", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<String> deleteGroup(
			@RequestParam("groupId") String groupId, HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			groupService.deleteGroup(groupId);
			return ResponseEntity.ok("Group deleted");
		}
	}

	@RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Group> getAllGroupForUSer(
			@RequestParam("userId") String userId, HttpServletResponse response) {
		log.debug("REST request to get all groups for user => " + userId);
		return groupService.getGroupsForUser(userId);
	}

	@RequestMapping(value = "/group", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
		groupService.createGroup(groupDTO);
		return ResponseEntity.ok("Group created");
	}

	@RequestMapping(value = "/groups", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> updateGroup(@Valid @RequestBody GroupDTO groupDTO,
			HttpServletRequest request) {
		
		ResponseEntity<?> res = null;
		
		if (groupDTO.getId() == null) {
			createGroup(groupDTO);

		}
		if (groupDTO == null) {
			res = ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");

		} else {

			groupService.updateGroupInformation(groupDTO);
			res = ResponseEntity.ok("group update");

		}
		return res;

	}

	@RequestMapping(value = "/group/wiki", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> updateGroupWiki(
			@Valid @RequestBody GroupDTO groupDTO,
			@RequestParam("groupId") String groupId, HttpServletRequest request) {
		if (groupDTO == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");
		} else {
			User user = userRepository.findOneByLogin(SecurityUtils
					.getCurrentLogin());
			Group group = groupRepository.findOneById(new ObjectId(groupId));
			if (!user.isTeacher()) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not modify this wiky");
			} else if (user.isTeacher()
					&& !(new ObjectId(group.getTeacherId())
							.equals(user.getId()))) {
				return ResponseEntity.badRequest()
						.contentType(MediaType.TEXT_PLAIN)
						.body("You can not modify this wiky");
			} else {
				groupService.updateGroupInformation(groupDTO);
				return ResponseEntity.ok("Group update");
			}
		}
	}

}
