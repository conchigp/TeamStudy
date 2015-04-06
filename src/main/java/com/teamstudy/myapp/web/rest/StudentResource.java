package com.teamstudy.myapp.web.rest;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.teamstudy.myapp.service.GroupService;

@RestController
@RequestMapping("/api")
public class StudentResource {

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;

	@Inject
	private UserRepository userRepository;

	@RequestMapping(value = "/student", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> delete(
			@RequestParam("groupId") String groupId,
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
	@RequestMapping(value = "/student", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> add(
			@RequestParam("groupId") String groupId,
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

}
