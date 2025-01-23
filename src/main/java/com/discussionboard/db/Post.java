package com.discussionboard.db;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String discussionId;
    private String content;
    private String author;

    @CreatedDate
    private LocalDateTime date;
}
