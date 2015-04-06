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
public class TeacherResource {

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupService groupService;

	@Inject
	private UserRepository userRepository;

	// Add teacher from group
	@RequestMapping(value = "/teacher", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> add(@RequestParam("groupId") String groupId,
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
	@RequestMapping(value = "/teacher", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> delete(@RequestParam("groupId") String groupId,
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
}