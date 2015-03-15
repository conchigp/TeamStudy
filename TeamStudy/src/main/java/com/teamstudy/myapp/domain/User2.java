package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A user.
 */
@Document(collection = "T_USER2")
public class User2 extends AbstractAuditingEntity implements Serializable {

    private boolean isTeacher = false;

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    @Override
    public String toString() {
        return "User2{" + "isTeacher='" + isTeacher + "}";
    }
}
