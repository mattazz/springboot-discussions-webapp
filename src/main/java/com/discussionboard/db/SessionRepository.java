package com.discussionboard.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {
    Optional<Session> findById(String id);
}