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
import com.teamstudy.myapp.repository.NewRepository;
import com.teamstudy.myapp.repository.UserRepository;


@Service
public class NewService {
	
	private final Logger log = LoggerFactory.getLogger(NewService.class);
	
	@Inject
	private NewRepository newRepository;
	
	@Inject
	private UserRepository userRepository;
	
	public void createNew(){
		
	}
	
	
}
