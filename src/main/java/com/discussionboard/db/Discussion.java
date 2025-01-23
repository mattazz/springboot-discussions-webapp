package com.discussionboard.db;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.time.LocalDateTime;

// Model for MongoDB discussion collection
@Data
@Document(collection="discussion")
public class Discussion {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;

    @CreatedDate
    private LocalDateTime date;
}
