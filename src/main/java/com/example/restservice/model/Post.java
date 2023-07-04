package com.example.restservice.model;
 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.restservice.persistence.PostDeserializer;
import com.example.restservice.persistence.PostSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize; 

@JsonDeserialize(using = PostDeserializer.class)
@JsonSerialize(using = PostSerializer.class)
public class Post {

    private @JsonProperty("id") int id;
    
    private @JsonProperty("comments") Map<Integer, Comment> comments;
    
    @JsonIgnore
    private int comment_id_count;

    private @JsonProperty("pictureName") String pictureName;

    private @JsonProperty("title") String title;
    
    public Post(@JsonProperty("pictureName") String pictureName, 
        @JsonProperty("title") String title, 
        @JsonProperty("comments") Map<Integer, Comment> comments) {
            this.comments = comments;
            // set comment id count to the size of comment section
            this.comment_id_count = this.comments.size();

            this.pictureName = pictureName;
            this.title = title;
    }

    @Autowired
    public Post(@JsonProperty("id") int id, 
        @JsonProperty("pictureName") String pictureName, 
        @JsonProperty("title") String title, 
        @JsonProperty("comments") Map<Integer, Comment> comments) {
        
            this(pictureName, title, comments);
            this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureName() {
        return this.pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Map<Integer, Comment> getComments() {
        return this.comments;
    }

    public void addComment(Comment comment) {
        comment.setId(comment_id_count++);
        this.comments.put(comment.getId(), comment);
    }

    // takes a comment's id and deletes it
    public void deleteComment(int comment_id) {
        if (this.comments.containsKey(comment_id))
            this.comments.remove(comment_id);
    }
}
