package com.pelican.pelicanapi.post;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pelican.pelicanapi.post.orm.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	public List<Post> findByUserId(Integer userId);
}
