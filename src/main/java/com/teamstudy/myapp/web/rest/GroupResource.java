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
	@RequestMapping(value = "/groups/{groupId}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<User> getAllByGroup(@PathVariable String groupId,
			HttpServletResponse response) {
		log.debug("REST request to get all Users");
		return groupService.getStudentsByGroup(groupId);
	}

	// Delete student from group
	@RequestMapping(value = "/groups/{groupId}/remove/{studentId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> removeStudentFromGroup(
			@PathVariable String groupId, @PathVariable String studentId,
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
						groupService.deleteStudentFromGroup(studentId, groupId);
						return ResponseEntity.ok("Student removed");
					}
				}
			}
		}

	}

	// Add student from group
	@RequestMapping(value = "/groups/{groupId}/add/{studentId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> addStudentToGroup(@PathVariable String groupId,
			@PathVariable String studentId, HttpServletRequest request) {
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
						groupService.addStudentToGroup(studentId, groupId);
						return ResponseEntity.ok("Student added");
					}
				}
			}
		}
	}

	// Add teacher from group
	@RequestMapping(value = "/groups/{groupId}/addT/{teacherId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> addTeacherToGroup(@PathVariable String groupId,
			@PathVariable String teacherId, HttpServletRequest request) {
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
						groupService.addTeacherToGroup(teacherId, groupId);
						return ResponseEntity.ok("Teacher added");
					}
				}
			}
		}
	}

	// Remove teacher from group
	@RequestMapping(value = "/groups/{groupId}/removeT", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> removeTeacherFromGroup(
			@PathVariable String groupId, HttpServletRequest request) {
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
				groupService.deleteTeacherFromGroup(groupId);
				return ResponseEntity.ok("Teacher removed");
			}
		}
	}

	// //create group.(MIO)
	// @RequestMapping(value = "/groups", method = RequestMethod.POST, produces
	// = MediaType.TEXT_PLAIN_VALUE)
	// @Timed
	// @RolesAllowed(AuthoritiesConstants.ADMIN)
	// public ResponseEntity<?> createGroup(@Valid @RequestBody Group group) {
	// // Group group2 = groupRepository.findOneById(group.getId());
	// if (group.getId() != null) {
	// return ResponseEntity.badRequest().header("Failure",
	// "A new group cannot already have an ID").build();
	// } else {
	// // groupService.createGroup(groupDTO.getName(),
	// groupDTO.getDescription(), groupDTO.getTeacherId(),
	// // groupDTO.getAlums(), groupDTO.getWiki());
	// group.setCreationMoment(new Date(System.currentTimeMillis()));
	// groupRepository.save(group);
	//
	// return new ResponseEntity<>(HttpStatus.CREATED);
	// }
	// }
	//
	// // update the current group information (MIO)
	// @RequestMapping(value = "/groups", method = RequestMethod.PUT, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// @Timed
	// @RolesAllowed(AuthoritiesConstants.ADMIN)
	// public ResponseEntity<?> updateGroup(@RequestBody Group group) throws
	// URISyntaxException{
	// log.debug("REST request to update Group : {}", group);
	// // Group group2 = groupRepository.findOneById(group.getId());
	// if (group.getId() == null) {
	// return createGroup(group);
	// }
	// // groupService.updateGroupInformation(group.getId(), groupDTO.getName(),
	// // groupDTO.getDescription(), groupDTO.getTeacherId(),
	// // groupDTO.getAlums(), groupDTO.getWiki());
	// groupRepository.save(group);
	// return new ResponseEntity<>(HttpStatus.OK);
	// }

	// delete group (MIO)
	@RequestMapping(value = "/delete/{groupId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<String> updateGroup(@PathVariable String groupId,
			HttpServletRequest request) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		if (group == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("nonexistent group");
		}
		groupService.deleteGroup(groupId);
		return ResponseEntity.ok("Group deleted");
	}

	// /**
	// * GET /group/wiki:groupId -> get the "wiki" by groupId.
	// */
	// @RequestMapping(value = "/groups/{groupId}/wiki", method =
	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	// @Timed
	// @RolesAllowed(AuthoritiesConstants.USER)
	// public Wiki getWiki(@PathVariable String groupId,
	// HttpServletResponse response) {
	// log.debug("REST request to get Wiki of the group with id: ", groupId);
	// Group group = groupRepository.findOneById(new ObjectId(groupId));
	// if (group == null) {
	// response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	// }
	// Wiki wiki = group.getWiki();
	// if (wiki == null) {
	// response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	// }
	// return wiki;
	// }

	// Get groups for user (MIO)
	@RequestMapping(value = "/groups/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public List<Group> getAllGroupForUSer(@PathVariable String userId,
			HttpServletResponse response) {
		log.debug("REST request to get all groups for user");
		return groupService.getGroupsForUser(userId);
	}

	// create group.(MIO)
	@RequestMapping(value = "/groups", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDTO groupDTO) {

		groupService.createGroup(groupDTO);
		return ResponseEntity.ok("Group created");

	}

	// update group information (MIO)
	@RequestMapping(value = "/groups", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> updateGroup(@Valid @RequestBody GroupDTO groupDTO,
			HttpServletRequest request) {

		if (groupDTO == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");

		}
		
		if (groupDTO.getId() == null) {
			createGroup(groupDTO);

		}

		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		User teacher = userRepository.findOneById(new ObjectId(groupDTO
				.getTeacherId()));

		if (!user.isTeacher()) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("engaña a tu madre, q ese nota no es profesor");
		}
		if (!teacher.isTeacher()) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("engaña a tu madre, q ese nota no es profesor");
		} else {

			groupService.updateGroupInformation(groupDTO);
			return ResponseEntity.ok("group update");
		}

	}

	// update group information (MIO)
	@RequestMapping(value = "/groups/{groupId}/wiki", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> updateGroupWiki(@Valid @RequestBody GroupDTO groupDTO,
			HttpServletRequest request) {

		if (groupDTO == null) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("This group does not exist");

		}
		
		if (groupDTO.getId() == null) {
			createGroup(groupDTO);

		}

		User user = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		User teacher = userRepository.findOneById(new ObjectId(groupDTO
				.getTeacherId()));

		if (!user.isTeacher()) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("engaña a tu madre, q ese nota no es profesor");
		}
		if (!teacher.isTeacher()) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.TEXT_PLAIN)
					.body("engaña a tu madre, q ese nota no es profesor");
		} else {

			groupService.updateGroupInformation(groupDTO);
			return ResponseEntity.ok("group update");
		}

	}
	
}
