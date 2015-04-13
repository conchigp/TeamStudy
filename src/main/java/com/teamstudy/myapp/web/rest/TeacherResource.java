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
public class TeacherResource {

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;

	@Inject
	private UserService userService;
	
	@Inject
	private UserRepository userRepository;
	
	@RequestMapping(value = "/teachers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public List<User> getAll(){
		return userService.getTeachers();
	}
	
	@RequestMapping(value = "/teacher", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed({AuthoritiesConstants.USER,AuthoritiesConstants.ADMIN})
	public User getTeacherByGroup(@RequestParam("groupId") String groupId,
			HttpServletResponse response) {
		if (groupRepository.findOneById(new ObjectId(groupId)) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} else {
			Group group = groupRepository.findOneById(new ObjectId(groupId));
			
			
			if(group.getTeacherId() ==null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}else{
				User teacher = userService.getTeacherByGroup(groupId);
				return teacher;
			}
			
		}
	}

	// Add teacher from group
	@RequestMapping(value = "/teacher", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> add(@RequestParam("groupId") String groupId,
			@RequestParam("teacherId") String teacherId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		User teacher = userRepository.findOneById(new ObjectId(teacherId));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			if (teacher == null) {
				return new ResponseEntity<>("Teacher not exist", HttpStatus.NOT_FOUND);
			} else {
				if (!teacher.isTeacher()) {
					return new ResponseEntity<>("This user is not a teacher", HttpStatus.UNAUTHORIZED);
				} else {
					if (group.getTeacherId() != null) {
						return new ResponseEntity<>("This group has already a teacher", HttpStatus.UNAUTHORIZED);
					} else {
						groupService.addTeacher(teacherId, groupId);
						return new ResponseEntity<>("Teacher added", HttpStatus.ACCEPTED);
					}
				}
			}
		}
	}

	// Remove teacher from group
	@RequestMapping(value = "/teacher", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> delete(@RequestParam("groupId") String groupId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return new ResponseEntity<>("Group not exist", HttpStatus.NOT_FOUND);
		} else {
			if (group.getTeacherId() == null) {
				return new ResponseEntity<>("This group has not a teacher", HttpStatus.UNAUTHORIZED);
			} else {
				groupService.deleteTeacher(groupId);
				return new ResponseEntity<>("Teacher removed", HttpStatus.ACCEPTED);
			}
		}
	}
}
