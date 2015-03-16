package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_MESSAGE")
public class Message extends AbstractAuditingEntity implements Serializable {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Past
    private Date creationMoment;

    @NotNull
    private Thread thread;

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

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" + "title='" + title + '\'' + ", description='" + description + '\'' + ", creationMoment='"
                + creationMoment + '\'' + ", thread='" + thread + '\'' + ", user='" + user + '\'' + "}";
    }

}
