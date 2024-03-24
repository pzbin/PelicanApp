package com.pelican.pelicanapi.post.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")
public class Post {

	@Id
	Integer id;
	
	@Column(name = "user_id", columnDefinition = "integer", nullable = false)
	Integer userId;
	
	@Column(name = "title", columnDefinition = "varchar(255) DEFAULT ''", nullable = false)
	String title;
	
	@Column(name = "body", columnDefinition = "text", nullable = false)
	String body;
}
