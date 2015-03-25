package com.teamstudy.myapp.service;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teamstudy.myapp.domain.New;
import com.teamstudy.myapp.repository.NewRepository;
import com.teamstudy.myapp.repository.UserRepository;



@Service
public class NewService {
	
	private final Logger log = LoggerFactory.getLogger(NewService.class);
	
	@Inject
	private NewRepository newRepository;
	
	@Inject
	private UserRepository userRepository;
	
	public void createNew(New noticia,String groupId){
	    
		noticia.setCreationMoment(new Date(System.currentTimeMillis()));
	    noticia.setDescription("");
	    noticia.setTitle("");
	    noticia.setGroupId(groupId);

	    newRepository.save(noticia);
	    
	    log.debug("Changed new: {}", noticia);
	}
	
	
}
