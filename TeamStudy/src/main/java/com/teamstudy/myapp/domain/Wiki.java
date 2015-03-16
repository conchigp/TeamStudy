package com.teamstudy.myapp.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_WIKI")
public class Wiki extends AbstractAuditingEntity implements Serializable {

    @NotNull
    private String text;

    @NotNull
    private Group group;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Wiki{" + "text='" + text + '\'' + ", group='" + group + '\'' + "}";
    }

    
}
