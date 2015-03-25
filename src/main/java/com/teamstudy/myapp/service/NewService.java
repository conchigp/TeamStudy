package com.teamstudy.myapp.service;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.New;
import com.teamstudy.myapp.repository.NewRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.web.rest.dto.NewsDTO;



@Service
public class NewService {
	
	private final Logger log = LoggerFactory.getLogger(NewService.class);
	
	@Inject
	private NewRepository newRepository;
	
	@Inject
	private UserRepository userRepository;
	
	public void createNew(NewsDTO noticia,String groupId){
	    
		New news = new New();
		news.setCreationMoment(new Date(System.currentTimeMillis()));
		news.setDescription(noticia.getDescription());
		news.setTitle(noticia.getTitle());
		news.setGroupId(groupId);

	    newRepository.save(news);
	    
	    log.debug("Changed new: {}", noticia);
	}
	
	
}
