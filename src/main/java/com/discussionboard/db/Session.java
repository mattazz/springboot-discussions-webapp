package com.discussionboard.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Model for MongoDB session collection that is used to store session information
@Document
public class Session {

    @Id
    private String id;
    private String username;

    public Session(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}