package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_MESSAGECHAT")
public class MessageChat extends AbstractAuditingEntity implements Serializable {

    @NotNull
    private String content;

    @Past
    private Date creationMoment;

    @NotNull
    private Group group;

    @NotNull
    private User user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "MessageChat{" + "content='" + content + '\'' + ", creationMoment='" + creationMoment + '\''
                + ", group='" + group + '\'' + ", user='" + user + '\'' + "}";
    }

}
