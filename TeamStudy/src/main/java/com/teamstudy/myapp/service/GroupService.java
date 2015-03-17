package com.teamstudy.myapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;


@Service
public class GroupService {
	
	private final Logger log = LoggerFactory.getLogger(GroupService.class);
	
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

}
