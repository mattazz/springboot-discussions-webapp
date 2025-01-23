package com.discussionboard.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostsRepository extends MongoRepository<Post, String>{
    List<Post> findByDiscussionId(String discussionId);
}
