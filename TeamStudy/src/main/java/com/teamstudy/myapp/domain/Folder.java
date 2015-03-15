package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_FOLDER")
public class Folder extends AbstractAuditingEntity implements Serializable {

    @NotNull
    private String title;

    @Past
    private Date creationMoment;

    @NotNull
    private Group group;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Folder{" + "title='" + title + '\'' + ", creationMoment='" + creationMoment + '\'' + ", group='"
                + group + '\'' + "}";
    }

}
