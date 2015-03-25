
package com.teamstudy.myapp.web.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamstudy.myapp.service.NewService;

@RestController
@RequestMapping("/api")
public class NewResource {

	private final Logger log = LoggerFactory.getLogger(NewResource.class);

	@Inject
	private NewService newService;



}

