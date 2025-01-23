package com.discussionboard.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

// Model for MongoDB user collection used to store user information
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
}