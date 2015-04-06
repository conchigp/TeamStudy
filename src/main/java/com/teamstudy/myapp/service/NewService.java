package com.teamstudy.myapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.New;
import com.teamstudy.myapp.repository.NewRepository;
import com.teamstudy.myapp.web.rest.dto.NewsDTO;

@Service
public class NewService {

	private final Logger log = LoggerFactory.getLogger(NewService.class);

	@Inject
	private NewRepository newRepository;

	public void create(NewsDTO noticia, String groupId) {

		New news = new New();
		news.setCreationMoment(new Date(System.currentTimeMillis()));
		news.setDescription(noticia.getDescription());
		news.setTitle(noticia.getTitle());
		news.setGroupId(groupId);

		newRepository.save(news);

		log.debug("Changed new: {}", noticia);
	}

	public List<New> getAllByGroups(List<Group> groups) {
		List<New> news = new ArrayList<New>();
		for (Group group : groups) {
			news.addAll(newRepository.findAllByGroupId(group.getId()));
		}
		return news;
	}
	
	public void delete(String groupId){
		List<New> news = newRepository.findAllByGroupId(groupId);
		for(New n : news){
			newRepository.delete(n);
		}
	}

}
