package com.learnspring.journalApp.repository;


import com.learnspring.journalApp.entity.JournalEntry;
import com.learnspring.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
}
