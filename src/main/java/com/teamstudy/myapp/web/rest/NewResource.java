
package com.teamstudy.myapp.web.rest;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.teamstudy.myapp.security.AuthoritiesConstants;
import com.teamstudy.myapp.service.NewService;
import com.teamstudy.myapp.web.rest.dto.NewsDTO;

@RestController
@RequestMapping("/api")
public class NewResource {

	private final Logger log = LoggerFactory.getLogger(NewResource.class);

	@Inject
	private NewService newService;

	// create new
	@RequestMapping(value = "/news", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@RolesAllowed(AuthoritiesConstants.USER)
	public ResponseEntity<?> createNew(@Valid @RequestBody NewsDTO noticia,@PathVariable String groupId,
			HttpServletResponse response) {
		if (noticia.getId() != null) {
			return ResponseEntity.badRequest()
					.header("Failure", "A new new cannot already have an ID")
					.build();
		} else {

			newService.createNew(noticia,groupId);

			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}


}

