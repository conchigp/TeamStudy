package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_REPLY")
public class Reply extends AbstractAuditingEntity implements Serializable {

    @NotNull
    private String description;

    @Past
    private Date creationMoment;

    @NotNull
    private Message message;

    @NotNull
    private User user;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationMoment() {
        return creationMoment;
    }

    public void setCreationMoment(Date creationMoment) {
        this.creationMoment = creationMoment;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reply{" + "description='" + description + '\'' + ", creationMoment='" + creationMoment + '\''
                + ", message='" + message + '\'' + ", user='" + user + '\'' + "}";
    }

}
