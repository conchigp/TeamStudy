package com.teamstudy.myapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.web.rest.dto.GroupDTO;


@Service
public class GroupService {
	
	private final Logger log = LoggerFactory.getLogger(GroupService.class);
	
	@Inject
	private GroupRepository groupRepository;
	
	@Inject
	private UserRepository userRepository;
	
	public void deleteStudentFromGroup(String studentId, String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		List<String> students = group.getAlums();
		students.remove(studentId);
		group.setAlums(students);
		groupRepository.save(group);
	}
	
	public void deleteTeacherFromGroup(String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		group.setTeacherId(null);
		groupRepository.save(group);
	}
	
	public void addStudentToGroup(String studentId, String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		List<String> students = group.getAlums();
		students.add(studentId);
		group.setAlums(students);
		groupRepository.save(group);
	}
	
	public void addTeacherToGroup(String teacherId, String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		group.setTeacherId(teacherId);
		groupRepository.save(group);
	}
	
	public List<User> getStudentsByGroup(String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		List<User> students = new ArrayList<User>();
		for(String s: group.getAlums()){
			User user = userRepository.findOneById(new ObjectId(s));
			students.add(user);
		}
		return students;
	}
	
public List<Group> getGroupsForUser(String userId){
		
		User user = userRepository.findOneById(new ObjectId(userId));
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
		public Group createGroup(GroupDTO groupDTO){ 
			
			Group group = new Group();
//			List<String> alums = new ArrayList<String>();
//		    Wiki wiki = new Wiki();
//		    wiki.setText("mierda de to");
		    
		    group.setTeacherId(groupDTO.getTeacherId());
		    group.setAlums(groupDTO.getAlums());
		    group.setWiki(groupDTO.getWiki());
		    group.setCreationMoment(new Date(System.currentTimeMillis()));
		    group.setDescription(groupDTO.getDescription());
		    group.setName(groupDTO.getName());

			groupRepository.save(group);
			log.debug("Created Information for Group: {}", group);
			return group;
			
		}/*
		// Cristian (Metodo para generar ObjectId para el populate)
		public Group createGroup(GroupDTO groupDTO){ 
		    group.setAlums(new ArrayList<String>());
		    group.setWiki(new Wiki());
		    group.setCreationMoment(new Date());
		    group.setDescription(groupDTO.getDescription());
		    group.setName(groupDTO.getName());
			groupRepository.save(group);
			log.debug("Created Information for Group: {}", group);
			return group;
		}*/
		//MIO
		public void updateGroupInformation(GroupDTO groupDTO,String id) {
			
			
			Assert.notNull(groupDTO);
			
			Group group = groupRepository.findOneById(new ObjectId(id));
			
			group.setName(groupDTO.getName());
			group.setDescription(groupDTO.getDescription());
			group.setTeacherId(groupDTO.getTeacherId());
			group.setAlums(groupDTO.getAlums());
			group.setWiki(groupDTO.getWiki());

			groupRepository.save(group);
			log.debug("Changed Information for Group: {}", group);
		}
		
		//MIO
		public void deleteGroup(String groupId){
			
			Assert.notNull(groupId);
			
			Group group = groupRepository.findOneById(new ObjectId(groupId));
			groupRepository.delete(group);
		}
		
	


}
