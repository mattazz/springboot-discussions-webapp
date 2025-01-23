package com.discussionboard.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DbApplication {
	public static void main(String[] args) {
		SpringApplication.run(DbApplication.class, args);
	}

}
