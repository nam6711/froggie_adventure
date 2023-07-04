package com.example.restservice.persistence;

import java.io.IOException;

import com.example.restservice.model.Comment;
import com.example.restservice.model.Post;

public interface PostDAO { 

    int getLatestPostNum() throws IOException;

    Post getLatest() throws IOException;

    /**
     * Gets a lab with the specified name as queried by a user
     * 
     * @param name the term to check for seeing if a {@link Lab lab} exists
     * 
     * @return a {@link Lab lab} with the same name as the searched term
     * <br>
     * or returns null if nothing is found
     * 
     * @throws IOException for any issues that may arise
     */
    Post getPost(int post_id) throws IOException;

    /**
     * Get all existing labs within the labs Map
     * 
     * @return a {@link Lab lab} array with all loaded Labs from the labs Map
     * 
     * @throws IOException for any issues that may arise
     */
    Post[] getPosts() throws IOException;

    /**
     * Creates and saves a {@linkplain Lab}
     * 
     * @param lab {@linkplain Lab} object to be created and saved
     *
     * @return new {@link Lab} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean createPost(Post post) throws IOException;

    /**
     * Creates and saves a {@linkplain Lab}
     * 
     * @param lab {@linkplain Lab} object to be created and saved
     *
     * @return new {@link Lab} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    boolean createComment(int post_id, Comment comment) throws IOException;

    /**
     * Updates and saves a {@linkplain Lab}
     * 
     * @param {@link Lab} object to be updated and saved
     * 
     * @return updated {@link Lab} if successful, null if
     * {@link Lab} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Post updatePost(int post_id, Post lab) throws IOException;

    /**
     * Deletes a {@linkplain Post} with the given id
     * 
     * @param name The name of the {@link Post}
     * 
     * @return true if the {@link Post} was deleted
     * <br>
     * false if hero with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deletePost(int post_id) throws IOException;

    /**
     * Deletes a {@linkplain Comment} with the given id
     * 
     * @param name The name of the {@link Comment}
     * 
     * @return true if the {@link Comment} was deleted
     * <br>
     * false if hero with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteComment(int post_id, int comment_id) throws IOException;
}
