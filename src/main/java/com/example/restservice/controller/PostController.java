package com.example.restservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.model.Comment;
import com.example.restservice.model.Post;
import com.example.restservice.persistence.PostDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger; 

@RestController
@RequestMapping("post")
public class PostController {
	private static final Logger LOG = Logger.getLogger(PostController.class.getName());
	
	private PostDAO postDAO;
 
	public PostController(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	@GetMapping("/{post_id}")
	public ResponseEntity<Post> getPost(@PathVariable int post_id) {
		LOG.info("GET /post " + post_id);
        try {
            Post post = postDAO.getPost(post_id);
            if (post != null)
                return new ResponseEntity<Post>(post,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@GetMapping("/")
	public ResponseEntity<Post[]> getPosts() throws IOException {
		LOG.info("GET /posts");
		try {
			Post[] posts = postDAO.getPosts();
			if (posts != null)
				return new ResponseEntity<Post[]>(posts,HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(IOException e) {
			LOG.log(Level.SEVERE,e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addPost")
    public ResponseEntity<Boolean> createPost(@RequestBody Post post) {
        LOG.info("POST /post " + post.getTitle());

        // Replace below with your implementation
        try {
            Boolean success = postDAO.createPost(post);
            if (success)
                return new ResponseEntity<Boolean>(HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PostMapping("/addComment/{post_id}")
    public ResponseEntity<Boolean> createComment(@PathVariable int post_id, @RequestBody Comment comment) {
        LOG.info("POST /comment TO post " + post_id);

        // Replace below with your implementation
        try {
            Boolean success = postDAO.createComment(post_id, comment);
            if (success)
                return new ResponseEntity<Boolean>(HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@PutMapping("/updatePost/{post_id}")
    public ResponseEntity<Post> updatePost(@PathVariable int post_id, @RequestBody Post post) {
        LOG.info("PUT /post " + post.getTitle());

        try {
            Post newPost = postDAO.updatePost(post_id, post);
            if (newPost != null) 
                return new ResponseEntity<Post>(newPost,HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@DeleteMapping("/deletePost/{post_id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable int post_id) {
        LOG.info("DELETE /post " + post_id);

        try {
            Boolean deleted = postDAO.deletePost(post_id);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@DeleteMapping("/deleteComment/{post_id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable int post_id, @PathVariable int comment_id) {
        LOG.info("DELETE /comment " + comment_id + " ON POST " + post_id);

        try {
            Boolean deleted = postDAO.deleteComment(post_id, comment_id);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}