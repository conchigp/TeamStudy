package com.teamstudy.myapp.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "T_ADMINISTRATOR")
public class Administrator extends AbstractAuditingEntity implements Serializable {

}
