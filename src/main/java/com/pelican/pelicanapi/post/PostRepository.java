package com.pelican.pelicanapi.post;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pelican.pelicanapi.post.orm.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	public Optional<Post> findByUserId(Integer userId);
}
