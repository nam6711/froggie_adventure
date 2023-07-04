package com.example.restservice.model;

import java.sql.Timestamp; 

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
    
    private @JsonProperty("id") int id;

    private final @JsonProperty("author") String author;

    private final @JsonProperty("profile_pic_name") String profile_pic_name;
    
    private final @JsonProperty("description") String description;

    private final @JsonProperty("date_posted") Timestamp date_posted; 

    public Comment(@JsonProperty("id") int id,
        final @JsonProperty("author") String author, 
        final @JsonProperty("profile_pic_name") String profile_pic_name, 
        final @JsonProperty("description") String description, 
        @JsonProperty("date_posted") Timestamp date_posted) {
            this.author = author;
            this.profile_pic_name = profile_pic_name;
            this.description = description;

            // date posted
            if (date_posted == null)
                this.date_posted = new Timestamp(System.currentTimeMillis());
            else
                this.date_posted = date_posted;    
    }
    
    public Comment(final @JsonProperty("author") String author, 
        final @JsonProperty("profile_pic_name") String profile_pic_name, 
        final @JsonProperty("description") String description, 
        @JsonProperty("date_posted") Timestamp date_posted) {
            this.author = author;
            this.profile_pic_name = profile_pic_name;
            this.description = description;

            // date posted
            if (date_posted == null)
                this.date_posted = new Timestamp(System.currentTimeMillis());
            else
                this.date_posted = date_posted;    
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
