package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_THREAD")
public class Thread extends AbstractAuditingEntity implements Serializable {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Past
    private Date creationMoment;

    @NotNull
    private Group group;

    @NotNull
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Thread{" + "title='" + title + '\'' + ", description='" + description + '\'' + ", creationMoment='"
                + creationMoment + '\'' + ", group='" + group + '\'' + ", user='" + user + '\'' + "}";
    }

}
