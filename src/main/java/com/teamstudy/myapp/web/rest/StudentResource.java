package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
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
import com.teamstudy.myapp.service.UserService;

@RestController
@RequestMapping("/api")
public class StudentResource {

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;
	
	@Inject
	private UserService userService;

	@Inject
	private UserRepository userRepository;
	
	@RequestMapping(value = "/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public List<User> getAll(){
		return userService.getStudents();
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({AuthoritiesConstants.USER,AuthoritiesConstants.ADMIN})
	public List<User> getAllByGroup(@RequestParam("groupId") String groupId,
			HttpServletResponse response) {
		if (groupRepository.findOneById(new ObjectId(groupId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			return userService.getStudentsByGroup(groupId);
		}
	}

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
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			if (student == null) {
				return new ResponseEntity<>("Student not exist", HttpStatus.NOT_FOUND);
			} else {
				if (student.isTeacher()) {
					return new ResponseEntity<>("This user is not a student", HttpStatus.UNAUTHORIZED);
				} else {
					if (!group.getAlums().contains(studentId)) {
						return new ResponseEntity<>("This student is not in the group", HttpStatus.UNAUTHORIZED);
					} else {
						groupService.deleteStudent(studentId, groupId);
						return new ResponseEntity<>("Student removed", HttpStatus.ACCEPTED);
					}
				}
			}
		}
	}

	// Add student from group
	@RequestMapping(value = "/student", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> add(
			@RequestParam("groupId") String groupId,
			@RequestParam("studentId") String studentId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		User student = userRepository.findOneById(new ObjectId(studentId));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			if (student == null) {
				return new ResponseEntity<>("Student not exist", HttpStatus.NOT_FOUND);
			} else {
				if (student.isTeacher()) {
					return new ResponseEntity<>("This user is not a student", HttpStatus.UNAUTHORIZED);
				} else {
					if (group.getAlums().contains(studentId)) {
						return new ResponseEntity<>("This student is already in the group", HttpStatus.UNAUTHORIZED);
					} else {
						groupService.addStudent(studentId, groupId);
						return new ResponseEntity<>("Student added", HttpStatus.ACCEPTED);
					}
				}
			}
		}
	}

}
