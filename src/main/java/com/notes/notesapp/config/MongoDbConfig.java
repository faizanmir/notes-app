package com.notes.notesapp.config;

import com.notes.notesapp.user.user_repo.UserRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = UserRepo.class)
@Configuration
public class MongoDbConfig {
}
