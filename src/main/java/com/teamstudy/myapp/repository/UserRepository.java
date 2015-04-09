package com.teamstudy.myapp.repository;

import com.teamstudy.myapp.domain.User;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the User entity.
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

    User findOneByLogin(String login);

    User findOneByEmail(String email);
    
    List<User> findAllByIsTeacher(boolean isTeacher);
    
    User findOneById(ObjectId objectId);

}
