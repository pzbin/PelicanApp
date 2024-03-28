package com.pelican.pelicanapi.post;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.pelican.pelicanapi.post.dto.PostContentDto;
import com.pelican.pelicanapi.post.dto.PostDto;
import com.pelican.pelicanapi.post.mapping.PostMapper;
import com.pelican.pelicanapi.post.orm.Post;
import com.pelican.pelicanapi.utils.validation.GeneralInputValidation;
import com.pelican.pelicanapi.webclient.JsphApiPaths;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {

	private static final String POST_PERSIST_ERR_MSG = "Post persistance problem!";
	public static final String POST_DELETE_ERR_MSG = "Post deletion not successful!";
	public static final String POST_DELETE_SUCCESS_MSG = "Post deletion successful!";
	
	private WebClient webClient;
	private final PostRepository postRepo;
	private final PostMapper postMapper;
	
	@Autowired
	public PostService(WebClient jsphWebClient, PostRepository postRepository, PostMapper postMapper) {
		this.webClient = jsphWebClient;
		this.postRepo = postRepository;
		this.postMapper = postMapper;
	}

	
	protected PostDto addPost(PostDto post) {
		
		PostDto remotePost = Optional.ofNullable(addPostRemote(post))
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase()));
		
		try {
			addPostLocal(remotePost);
		}
		catch (NullPointerException e) {
			log.info(e.getMessage());
			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
		}
		catch (Exception e) {
			// I would log the problem to db / logging system to know that external api Dto is not sync with local db (nullable fields without default values)
			log.error("addPostLocal - Post persistance problem");
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, POST_PERSIST_ERR_MSG);
		}
		
		return remotePost;
	}
	
	private PostDto addPostRemote(PostDto post) {
		
		ResponseEntity<PostDto> response = webClient
				.post()
				.uri(JsphApiPaths.POSTS.getPath())
				.body(BodyInserters.fromValue(post))
				.retrieve()
				.toEntity(PostDto.class)
				.onErrorReturn(ResponseEntity.badRequest().body(null))
				.block();	//sync call
		
		return response.getStatusCode().is2xxSuccessful()? response.getBody(): null;
	}

	@Transactional
	private Post addPostLocal(PostDto post) {
		
		Post postOrm = postMapper.postDtoToOrm(post);
		postOrm = postRepo.saveAndFlush(postOrm);
		return postOrm;
	}
	

	protected PostDto getPostById(Integer postId) {
		
		//find in local db
		Optional<Post> postOpt = postRepo.findById(postId);
		
		if(postOpt.isPresent()) {
			return postMapper.postOrmToDto( postOpt.get());
		}
		
		// try to fetch the remote and sync or else return NOT FOUND
		Optional<PostDto> postDtoOpt = Optional.ofNullable(getPostByIdRemote(postId));
		
		if(postDtoOpt.isEmpty()) {
			throw new HttpServerErrorException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
		}
		
		//remote found - sync to local db + return the post
		PostDto postRem = postDtoOpt.get();
		
		try {
			addPostLocal(postRem);	
		}catch (Exception e) {
			log.error(POST_PERSIST_ERR_MSG);
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, POST_PERSIST_ERR_MSG);
		}
		
		return postRem;
	}
	

	private PostDto getPostByIdRemote(@NonNull Integer postId) {
		
		ResponseEntity<PostDto> response = webClient
				.get()
				.uri(uri -> uri.path(JsphApiPaths.POST_BY_ID.getPath()).build(postId.toString()))
				.retrieve()
				.toEntity(PostDto.class)
				.onErrorReturn(ResponseEntity.badRequest().body(null))
				.block();	//sync call
		
		return response.getStatusCode().is2xxSuccessful()? response.getBody(): null;
		
	}
	

	@Transactional
	protected boolean deletePostById(Integer postId) {
		
		if(deletePostByIdRemote(postId)){
			//not throwing an exception - silently ingonred
			postRepo.deleteById(postId);
			return true;
		}
		return false;
	}
	
	private boolean deletePostByIdRemote(Integer postId) {
		
		// call are returning 200 success only for every id
		ResponseEntity<PostDto> response = webClient
				.delete()
				.uri(uri -> uri.path(JsphApiPaths.POST_BY_ID.getPath()).build(postId.toString()))
				.retrieve()
				.toEntity(PostDto.class)
				.onErrorReturn(ResponseEntity.badRequest().body(null))
				.block();	//sync call
		
		return response.getStatusCode().is2xxSuccessful();
		
	}
	
	@Transactional
	protected PostDto patchPostById(Integer postId, PostContentDto post) {
		
		PostDto postDto = Optional.ofNullable(patchPostByIdRemote(postId, post))
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));

		try {
			addPostLocal(postDto);
		} catch (Exception e) {
			log.error(POST_PERSIST_ERR_MSG);
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, POST_PERSIST_ERR_MSG);
		}

		return postDto;
	}
	
	private PostDto patchPostByIdRemote(Integer postId, PostContentDto post) {
		
		ResponseEntity<PostDto> response = webClient
				.patch()
				.uri(uri -> uri.path(JsphApiPaths.POST_BY_ID.getPath()).build(postId.toString()))
				.body(BodyInserters.fromValue(post))
				.retrieve()
				.toEntity(PostDto.class)
				.onErrorReturn(ResponseEntity.badRequest().body(null))
				.block();	//sync call
		
		// I observe that api returns 200 even if to be patched record does not exist with partial result - rather validate the patched result
		// I consider the patch successful remotely in case that the whole entity is returned
		if(response.getStatusCode().is2xxSuccessful() && GeneralInputValidation.validatePostInputs(response.getBody(), false)) {
			return response.getBody();
		}
		
		return null;
	}
	
}
