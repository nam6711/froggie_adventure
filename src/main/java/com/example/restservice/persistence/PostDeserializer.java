package com.example.restservice.persistence;

import java.io.IOException; 
import java.util.Map;
import java.util.TreeMap;

import com.example.restservice.model.Comment;
import com.example.restservice.model.Post;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode; 

public class PostDeserializer extends StdDeserializer<Post> {
    
    public PostDeserializer() { 
        this(null); 
    } 

    public PostDeserializer(Class<?> vc) { 
        super(vc); 
    } 

    @Override
    public Post deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        int id = node.get("id").asInt(); 

        Map<Integer, Comment> comments = getComments(node);
        String pictureName = node.get("pictureName").asText();
        String title = node.get("title").asText();

        return new Post(id, pictureName, title, comments);
    } 

    private Map<Integer, Comment> getComments(JsonNode node) {
        ArrayNode queries = (ArrayNode) node.get("comments");
        Map<Integer, Comment> comments = new TreeMap<Integer, Comment>();

        // loop through and create the list of nodes
        for (JsonNode query : queries) { 
            Comment comment = new Comment(query.get("id").asInt(),
            query.get("author").asText(),
            query.get("profile_pic_name").asText(),
            query.get("description").asText(),
            query.get("date_posted").asLong());

            comments.put(comment.getId(), comment);
        } 

        return comments;
    }
}
