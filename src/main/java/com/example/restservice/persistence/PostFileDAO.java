package com.example.restservice.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.restservice.model.Post;
import com.example.restservice.model.Comment; 

@Component
public class PostFileDAO implements PostDAO {
    Map<Integer, Post> posts; // mapping for all labs after loaded from JSON
    
    private ObjectMapper objectMapper; 

    private int post_id_count = 0;
    
    private Post latestPost;

    private String filename;

    public PostFileDAO(@Value("${posts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        
        // sets up the mapper for post persistence
        this.objectMapper = objectMapper;
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Post.class, new PostDeserializer());
        module.addSerializer(Post.class, new PostSerializer());
        this.objectMapper.registerModule(module);

        load();
    }

    private Post[] getPostArray(Integer comparisonTerm) {
        ArrayList<Post> postArrayList = new ArrayList<Post>();

        // iterate through all labs loaded in memory and check if
        // any have the same name as the searched term
        // if the comparisonTerm is null, the program will return all Labs
        for (Post post : posts.values()) {
            // if the sent in comparison term is null
            // return every lab in the labs Map
            // if the current lab itterated onto has the same name as the comparison term
            // add it to the array list of labs
            // else pass over it
            if (comparisonTerm == null || comparisonTerm.equals(post.getId())) {
                postArrayList.add(post);
            }
        }

        // finally transform the compiled list of labs into a array of Labs
        // then return it back to whatever requested a array of labs
        Post[] labArray = new Post[postArrayList.size()];
        postArrayList.toArray(labArray);
        return labArray;
    }

    private Post[] getPostArray() {
        return getPostArray(null);
    }

    private boolean load() throws IOException {
        posts = new TreeMap<>();

        // loads all labs from JSON and maps into an array of Labs
        Post[] postArray = objectMapper.readValue(new File(filename), Post[].class);

        // iterate through the array, placing the current lab into the labs Map
        for (Post post : postArray) { 
            posts.put(post.getId(), post);
            // use to set the post id count where we left off
            if (post.getId() >= this.post_id_count) {
                post_id_count = post.getId() + 1;
                this.latestPost = post;
            }
        }

        // finish
        return true;
    }

    public boolean savePosts() throws IOException {
        // loads all labs into an array for saving to JSON
        Post[] postArray = getPostArray();

        objectMapper.writeValue(new File(filename), postArray);
        return true;
    }

    @Override
    public int getLatestPostNum() throws IOException {
        synchronized (posts) {
            return this.latestPost.getId();
        }
    }

    @Override
    public Post getLatest() throws IOException {
        synchronized (posts) {
            return this.latestPost;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Post getPost(int post_id) throws IOException {
        synchronized (posts) {
            if (posts.containsKey(post_id)) {
                return posts.get(post_id);
            }
            else {
                return null;
            }
        }
    }

    @Override
    public Post[] getPosts() throws IOException {
        synchronized (posts) {
            return getPostArray(); 
        }
    }

    @Override
    public boolean createPost(Post post) throws IOException {
        synchronized (posts) { 
            post.setId(post_id_count++);
            posts.put(post.getId(), post);

            this.latestPost = post;

            return savePosts();
        }
    }

    @Override
    public boolean createComment(int post_id, Comment comment) throws IOException {
        synchronized (posts) {
            if (!posts.containsKey(post_id))
                return false;
            
            Post post = posts.get(post_id);
            post.addComment(comment);
            return savePosts();
        }
    }

    @Override
    public Post updatePost(int post_id, Post post) throws IOException {
        synchronized (posts) {
            Post postToUpdate = this.posts.get(post_id);
 
            // update properties
            postToUpdate.setTitle(post.getTitle());
            postToUpdate.setPictureName(post.getPictureName());

            savePosts(); // may throw an IOException
            return post;
        }
    }

    @Override
    public boolean deletePost(int post_id) throws IOException {
        synchronized(posts) {
            if (posts.containsKey(post_id)) {
                // set latest post if THIS is the latest post
                int count = post_id;
                while (this.latestPost == posts.get(post_id)) {
                    // assign until posts == null
                    Post latest;
                    do {
                        --count;
                        latest = posts.get(count);
                    } while (latest == null && count >= 0);

                    this.latestPost = latest;
                }
                
                posts.remove(post_id);
                return savePosts();
            }
            else
                return false;
        }
    }

    @Override
    public boolean deleteComment(int post_id, int comment_id) throws IOException {
        synchronized(posts) {
            if (posts.containsKey(post_id)) {
                Post post = posts.get(post_id);

                post.deleteComment(comment_id);
                return savePosts();
            }
            else
                return false;
        }
    }
}
