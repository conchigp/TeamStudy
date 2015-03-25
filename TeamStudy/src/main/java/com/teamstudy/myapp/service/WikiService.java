package com.teamstudy.myapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.domain.Wiki;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;


@Service
public class WikiService {
	
	private final Logger log = LoggerFactory.getLogger(WikiService.class);
	
	@Inject
	private GroupRepository groupRepository;
	
	@Inject
	private UserRepository userRepository;
	
	public void deleteStudentFromGroup(String studentId, String groupId){
		Group group = groupRepository.findOne(groupId);
		List<String> students = group.getAlums();
		students.remove(studentId);
		group.setAlums(students);
		groupRepository.save(group);
	}
	
	public void deleteTeacherFromGroup(String groupId){
		Group group = groupRepository.findOne(groupId);
		group.setTeacherId(null);
		groupRepository.save(group);
	}
	
	public void addStudentToGroup(String studentId, String groupId){
		Group group = groupRepository.findOne(groupId);
		List<String> students = group.getAlums();
		students.add(studentId);
		group.setAlums(students);
		groupRepository.save(group);
	}
	
	public void addTeacherToGroup(String teacherId, String groupId){
		Group group = groupRepository.findOne(groupId);
		group.setTeacherId(teacherId);
		groupRepository.save(group);
	}
	
	public List<User> getStudentsByGroup(String groupId){
		Group group = groupRepository.findOne(groupId);
		List<User> students = new ArrayList<User>();
		for(String s: group.getAlums()){
			User user = userRepository.findOne(s);
			students.add(user);
		}
		return students;
	}
	
public List<Group> getGroupsForUser(String userId){
		
		User user = userRepository.findOne(userId);
		List<Group> groups = groupRepository.findAll();
		List<Group> myGroups = new ArrayList<Group>();
		
		for (Group g : groups){
			if(g.getAlums().contains(user.getId())){
				myGroups.add(g);
			}
		}
		
		return myGroups;
	}
	
	//MIO
		public Group createGroup(String name, String description, String teacherId,
				List<String> alums, Wiki wiki) {
			Group newGroup = new Group();
			
			newGroup.setAlums(alums);
			newGroup.setCreationMoment(new Date(System.currentTimeMillis()));
			newGroup.setName(name);
			newGroup.setDescription(description);
			newGroup.setTeacherId(teacherId);
			newGroup.setWiki(wiki);

			groupRepository.save(newGroup);
			log.debug("Created Information for Group: {}", newGroup);
			return newGroup;
		}
		
		//MIO
		public void updateGroupInformation(String groupId, String name, String description, String teacherId,
				List<String> alums, Wiki wiki) {
			Group group = groupRepository.findOne(groupId);
			
			group.setName(name);
			group.setDescription(description);
			group.setTeacherId(teacherId);
			group.setAlums(alums);
			group.setWiki(wiki);

			groupRepository.save(group);
			log.debug("Changed Information for Group: {}", group);
		}
		
		//MIO
		public void deleteGroup(String groupId){
			Group group = groupRepository.findOne(groupId);
			groupRepository.delete(group);
		}
		
	


}
