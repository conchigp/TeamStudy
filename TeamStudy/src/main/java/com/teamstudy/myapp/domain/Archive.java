package com.teamstudy.myapp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_ARCHIVE")
public class Archive extends AbstractAuditingEntity implements Serializable {

    @NotNull
    private String title;

    @Past
    private Date creationMoment;

    @Min(0)
    private Double size;

    private String format;

    @NotNull
    @URL
    private String url;

    @NotNull
    private User2 user;

    @NotNull
    private Folder folder;

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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User2 getUser() {
        return user;
    }

    public void setUser(User2 user) {
        this.user = user;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public String toString() {
        return "Archive{" + "title='" + title + '\'' + ", creationMoment='" + creationMoment + '\'' + ", size='" + size
                + '\'' + ", format='" + format + '\'' + ", url='" + url + '\'' + ", user='" + user + '\''
                + ", folder='" + folder + '\'' + "}";
    }

}
