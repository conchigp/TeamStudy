package com.teamstudy.myapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.Folder;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.Thread;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.domain.Wiki;
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
	
	@Inject
	private ChatService chatService;
	
	@Inject
	private ThreadService threadService;
	
	@Inject
	private NewService newService;
	
	@Inject
	private FolderService folderService;
	
	public void deleteStudent(String studentId, String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		List<String> students = group.getAlums();
		students.remove(studentId);
		group.setAlums(students);
		groupRepository.save(group);
	}
	
	public void deleteTeacher(String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		group.setTeacherId(null);
		groupRepository.save(group);
	}
	
	public void addStudent(String studentId, String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		List<String> students = group.getAlums();
		students.add(studentId);
		group.setAlums(students);
		groupRepository.save(group);
	}
	
	public void addTeacher(String teacherId, String groupId){
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		group.setTeacherId(teacherId);
		groupRepository.save(group);
	}
	
	public List<Group> getGroupsForUser(String userId){
		User user = userRepository.findOneById(new ObjectId(userId));
		List<Group> groups = groupRepository.findAll();
		List<Group> myGroups = new ArrayList<Group>();
		if(user.isTeacher()){
			for (Group g : groups){
				if (g.getTeacherId() != null && g.getTeacherId().equals(user.getId().toString())){
					myGroups.add(g);
				}
			}	
		} else {
			for (Group g : groups){
				if (g.getAlums() != null){
					if(g.getAlums().contains(user.getId())){
						myGroups.add(g);
					}
				}
			}	
		}
		return myGroups;
	}
	
	public Group createGroup(GroupDTO groupDTO){ 	
		Group group = new Group();   
	    group.setAlums(new ArrayList<String>());
	    group.setWiki(new Wiki());
	    group.setCreationMoment(new Date(System.currentTimeMillis()));
	    group.setDescription(groupDTO.getDescription());
	    group.setName(groupDTO.getName());
		groupRepository.save(group);
		log.debug("Created Information for Group: {}", group);
		return group;	
	}
	
	public void updateGroupInformation(GroupDTO groupDTO) {
		Group group = groupRepository.findOneById(new ObjectId(groupDTO.getId()));
		group.setName(groupDTO.getName());
		group.setDescription(groupDTO.getDescription());
		group.setWiki(groupDTO.getWiki());
		groupRepository.save(group);
		log.debug("Changed Information for Group: {}", group);
	}
	
	public void deleteGroup(String groupId) throws Exception{
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		List<Thread> threads = threadService.findAllByGroup(groupId);
		List<Folder> folders = folderService.findAllByGroup(groupId);
		for(Thread t : threads){
			threadService.delete(t.getId().toString());
		}
		for(Folder f : folders){
			folderService.delete(f.getId().toString());
		}
		newService.delete(groupId);
		chatService.delete(groupId);
		groupRepository.delete(group);
	}
	
}
