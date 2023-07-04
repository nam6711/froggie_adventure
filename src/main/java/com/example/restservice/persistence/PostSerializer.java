package com.example.restservice.persistence; 

import java.io.IOException;
import java.util.Map;

import com.example.restservice.model.Comment;
import com.example.restservice.model.Post;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PostSerializer extends StdSerializer<Post> {
    
    public PostSerializer() {
        this(null);
    }
  
    public PostSerializer(Class<Post> t) {
        super(t);
    }

    @Override
    public void serialize(
        Post value, JsonGenerator jgen, SerializerProvider provider) 
    throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("pictureName", value.getPictureName());
        jgen.writeStringField("title", value.getTitle());
        
        jgen.writeArrayFieldStart("comments");
        writeComments(jgen, value);
        jgen.writeEndArray();

        jgen.writeEndObject();
    }

    private void writeComments(JsonGenerator jgen, Post value) throws IOException {
        for (Map.Entry<Integer, Comment> entry : value.getComments().entrySet()) {
            Comment comment = entry.getValue();

            jgen.writeStartObject();
    
            jgen.writeNumberField("id", comment.getId());
            jgen.writeStringField("author", comment.getAuthor());
            jgen.writeStringField("profile_pic_name", comment.getProfilePicName());
            jgen.writeStringField("description", comment.getDescription());
            jgen.writeNumberField("date_posted", comment.getDatePosted());
    
            jgen.writeEndObject();
        }
    }
}
