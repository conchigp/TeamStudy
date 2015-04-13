package com.teamstudy.myapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.teamstudy.myapp.domain.Authority;
import com.teamstudy.myapp.domain.Group;
import com.teamstudy.myapp.domain.User;
import com.teamstudy.myapp.repository.GroupRepository;
import com.teamstudy.myapp.repository.UserRepository;
import com.teamstudy.myapp.security.SecurityUtils;
import com.teamstudy.myapp.service.util.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private UserRepository userRepository;

	@Inject
	private GroupRepository groupRepository;

	public User activateRegistration(String key) {
		log.debug("Activating user for activation key {}", key);
		User user = userRepository.findOneByActivationKey(key);
		// activate given user for the registration key.
		if (user != null) {
			user.setActivated(true);
			user.setActivationKey(null);
			userRepository.save(user);
			log.debug("Activated user: {}", user);
		}
		return user;
	}

	public User createUserInformation(String login, String password,
			String firstName, String lastName, String email, String langKey,
			String isTeacher, String imageUrl) {
		User newUser = new User();
		Authority authority = new Authority();
		authority.setName("ROLE_USER");
		Set<Authority> authorities = new HashSet<>();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setTeacher(new Boolean(isTeacher));
		newUser.setLogin(login);
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setLangKey("es");
		newUser.setActivated(true);
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		authorities.add(authority);
		newUser.setAuthorities(authorities);
		newUser.setImageUrl(imageUrl);
		userRepository.save(newUser);
		log.debug("Created Information for Student: {}", newUser);
		return newUser;
	}

	public List<User> getStudentsByGroup(String groupId) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		List<User> students = new ArrayList<User>();
		for (String s : group.getAlums()) {
			User user = userRepository.findOneById(new ObjectId(s));
			students.add(user);
		}
		return students;
	}

	public void updateUserInformation(String firstName, String lastName,
			String email) {
		User currentUser = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setEmail(email);
		userRepository.save(currentUser);
		log.debug("Changed Information for User: {}", currentUser);
	}

	public void changePassword(String password) {
		User currentUser = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		String encryptedPassword = passwordEncoder.encode(password);
		currentUser.setPassword(encryptedPassword);
		userRepository.save(currentUser);
		log.debug("Changed password for User: {}", currentUser);
	}

	public User getUserWithAuthorities() {
		User currentUser = userRepository.findOneByLogin(SecurityUtils
				.getCurrentLogin());
		currentUser.getAuthorities().size(); // eagerly load the association
		return currentUser;
	}

	public List<User> getStudents() {
		return userRepository.findAllByIsTeacher(false);
	}

	public List<User> getTeachers() {
		return userRepository.findAllByIsTeacher(true);
	}

	public User getTeacherByGroup(String groupId) {
		Group group = groupRepository.findOneById(new ObjectId(groupId));
		String teacherId = group.getTeacherId();

		Assert.notNull(teacherId);

		User teacher = userRepository.findOneById(new ObjectId(teacherId));

		return teacher;
	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p/>
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 * </p>
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		DateTime now = new DateTime();
		List<User> users = userRepository
				.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
		for (User user : users) {
			log.debug("Deleting not activated user {}", user.getLogin());
			userRepository.delete(user);
		}
	}

}
